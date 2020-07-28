package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GroupBL;
import com.old2dimension.OCEANIA.bl.GroupCodeBL;
import com.old2dimension.OCEANIA.bl.LabelBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.GroupCodeRepository;
import com.old2dimension.OCEANIA.dao.GroupMemberRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.GroupIdAndCodeIdForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GroupCodeBLImpl implements GroupCodeBL {

    @Autowired
    CodeRepository codeRepository;
    @Autowired
    LabelBL labelBL;
    @Autowired
    GroupCodeRepository groupCodeRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public ResponseVO addCode(GroupIdAndCodeIdForm groupIdAndCodeIdForm) {
        Code code = codeRepository.findCodeById(groupIdAndCodeIdForm.getCodeId());
        Code newCode = new Code(1,code.getName(),code.getNumOfVertices(),code.getNumOfEdges(),code.getNumOfDomains(),
                code.getIs_default());
        newCode = codeRepository.save(newCode);
        if(newCode.getId()==0){return ResponseVO.buildFailure("Adding code failed.");}
        GroupCode groupCode = new GroupCode(groupIdAndCodeIdForm.getGroupId(),newCode.getId());
        groupCode = groupCodeRepository.save(groupCode);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1,newCode.getId());
        List<VertexLabel> vertexLabels = labelBL.getAllVertexLabel(userAndCodeForm);
        List<EdgeLabel> edgeLabels = labelBL.getAllEdgeLabel(userAndCodeForm);
        List<DomainLabel> domainLabels = labelBL.getAllDomainLabel(userAndCodeForm);
        List<VertexLabel> copyVertexLabels =new ArrayList<>();
        List<EdgeLabel> copyEdgeLabels =new ArrayList<>();
        List<DomainLabel> copyDomainLabels =new ArrayList<>();
        for(VertexLabel cur : vertexLabels){
            VertexLabel copy = new VertexLabel(cur.getUserId(),newCode.getId(),cur.getVertexId(),cur.getTitle(),cur.getContent());
            copyVertexLabels.add(copy);
        }

        for(EdgeLabel cur : edgeLabels){
            EdgeLabel copy = new EdgeLabel(cur.getUserId(),newCode.getId(),cur.getEdgeId(),cur.getTitle(),cur.getContent());
            copyEdgeLabels.add(copy);
        }

        for(DomainLabel cur : domainLabels){
            DomainLabel copy = new DomainLabel(cur.getUserId(),newCode.getId(),cur.getFirstEdgeId(),cur.getNumOfVertex(),cur.getTitle(),cur.getContent());
            copyDomainLabels.add(copy);
        }

        List<VertexLabel> savedV = labelBL.saveAllVertexLabel(copyVertexLabels);

        if(savedV==null){
            return ResponseVO.buildFailure("Adding code failed(add vertex labels)");
        }
        List<EdgeLabel> saveE = labelBL.saveAllEdgeLabel(copyEdgeLabels);
        if(saveE==null){
            labelBL.deleteAllVertexLabel(savedV);
            return ResponseVO.buildFailure("Adding code failed(add edge labels)");
        }
        List<DomainLabel> saveD = labelBL.saveAllDomainLabel(copyDomainLabels);
        if(saveD==null){
            labelBL.deleteAllEdgeLabel(saveE);
            labelBL.deleteAllVertexLabel(savedV);
            return ResponseVO.buildFailure("Adding code failed(add domain labels)");
        }

        //------------------------文件复制todo----------------------

        return ResponseVO.buildSuccess("Adding group code succeed.");
    }

    @Override
    public ResponseVO deleteCode(GroupIdAndCodeIdForm groupIdAndCodeIdForm) {
        GroupCode groupCode = groupCodeRepository.findGroupCodeByCodeIdAndGroupId(groupIdAndCodeIdForm.getCodeId(),groupIdAndCodeIdForm.getGroupId());
        if(groupCode==null){
            return ResponseVO.buildFailure("Don't have the access of deleting");
        }
        codeRepository.deleteById(groupIdAndCodeIdForm.getCodeId());

        return ResponseVO.buildSuccess("Deleting code succeed.");
    }

    @Override
    public ResponseVO getGroupCodeList(int groupId) {
        List<GroupCode> groupCodes = groupCodeRepository.findAllByGroupId(groupId);
        if(groupCodes==null){return ResponseVO.buildFailure("Getting group code list failed.");}
        List<Code> codes = new ArrayList<>();
        for(GroupCode groupCode:groupCodes){
            Code code = codeRepository.findCodeById(groupCode.getCodeId());
            if(code==null){
                return ResponseVO.buildFailure("Getting group code info failed.");
            }
            codes.add(code);
        }

        return ResponseVO.buildSuccess(codes);
    }

    @Override
    public ResponseVO getCodeStatistics(GroupIdAndCodeIdForm groupIdAndCodeIdForm) {
        HashMap<String,Integer> userVertexLabel = new HashMap<>();
        HashMap<String,Integer> userEdgeLabel = new HashMap<>();
        HashMap<String,Integer> userDomainLabel = new HashMap<>();
        HashMap<String,HashMap> sumMap = new HashMap<>();
        List<GroupMember> members = groupMemberRepository.findGroupMembersByGroupId(groupIdAndCodeIdForm.getGroupId());
        int codeId = groupIdAndCodeIdForm.getCodeId();
        for(GroupMember groupMember:members){
            int userId = groupMember.getUserId();
            User user = userRepository.findUserById(userId);
            if(user==null){
                return ResponseVO.buildFailure("Getting user info failed.");
            }
            String userKey = userId+" "+user.getName();
            userVertexLabel.put(userKey, labelBL.countVertexLabel(userId,codeId));
            userEdgeLabel.put(userKey,labelBL.countEdgeLabel(userId,codeId));
            userDomainLabel.put(userKey,labelBL.countDomainLabel(userId,codeId));

        }
        sumMap.put("vertexLabel",userVertexLabel);
        sumMap.put("edgeLabel",userEdgeLabel);
        sumMap.put("domainLabel",userDomainLabel);
        return ResponseVO.buildSuccess(sumMap);
    }
}
