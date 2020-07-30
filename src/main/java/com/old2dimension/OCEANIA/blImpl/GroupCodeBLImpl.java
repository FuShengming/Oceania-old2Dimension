package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GroupBL;
import com.old2dimension.OCEANIA.bl.GroupCodeBL;
import com.old2dimension.OCEANIA.bl.LabelBL;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.CodeAndDateForm;
import com.old2dimension.OCEANIA.vo.GroupIdAndCodeIdForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
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
    @Autowired
    WorkPlaceRepository workPlaceRepository;

    public void setWorkPlaceRepository(WorkPlaceRepository workPlaceRepository) {
        this.workPlaceRepository = workPlaceRepository;
    }

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setGroupCodeRepository(GroupCodeRepository groupCodeRepository) {
        this.groupCodeRepository = groupCodeRepository;
    }

    public void setGroupMemberRepository(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setLabelBL(LabelBL labelBL) {
        this.labelBL = labelBL;
    }

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
            EdgeLabel copy = new EdgeLabel(cur.getUserId(),cur.getEdgeId(),newCode.getId(),cur.getTitle(),cur.getContent());
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
        String source = "";
        if(code.getIs_default()==1){
            source = "src/main/resources/analyzeCode/src/0";
        }
        else{
            source = "src/main/resources/analyzeCode/src/"+groupIdAndCodeIdForm.getCodeId();
        }

        String des = "src/main/resources/analyzeCode/src/"+newCode.getId();
        boolean copySuccess = codeCopy(source,des);
        if(!copySuccess){
            deleteFile(new File(des));
            return ResponseVO.buildFailure("Adding code failed(copy files)");
        }

        return ResponseVO.buildSuccess("Adding group code succeed.");
    }

    @Override
    public ResponseVO deleteCode(GroupIdAndCodeIdForm groupIdAndCodeIdForm) {
        GroupCode groupCode = groupCodeRepository.findGroupCodeByCodeIdAndGroupId(groupIdAndCodeIdForm.getCodeId(),groupIdAndCodeIdForm.getGroupId());
        if(groupCode==null){
            return ResponseVO.buildFailure("Don't have the access of deleting");
        }
        codeRepository.deleteById(groupIdAndCodeIdForm.getCodeId());

        deleteFile(new File("src/main/resources/analyzeCode/src/"+groupIdAndCodeIdForm.getCodeId()));
        return ResponseVO.buildSuccess("Deleting code succeed.");
    }

    @Override
    public ResponseVO getGroupCodeList(int groupId) {
        List<GroupCode> groupCodes = groupCodeRepository.findAllByGroupId(groupId);
        if(groupCodes==null){return ResponseVO.buildFailure("Getting group code list failed.");}
        List<CodeAndDateForm> codes = new ArrayList<>();
        for(GroupCode groupCode:groupCodes){
            Code code = codeRepository.findCodeById(groupCode.getCodeId());
            if(code==null){
                return ResponseVO.buildFailure("Getting group code info failed.");
            }
            WorkSpace ws = workPlaceRepository.findLatestWorkSpace(1,code.getId());
            CodeAndDateForm c = null;
            if(ws!=null){
               c =  new CodeAndDateForm(code.getId(),code.getName(),ws.getDate());
            }
            else{
                c =  new CodeAndDateForm(code.getId(),code.getName(),null);
            }

            codes.add(c);
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


    public boolean codeCopy(String src, String des)  {
        File file1 = new File(src);//源
        File file2 = new File(des);//新目录
        return listCopyAll(file1, file2);//递归拷贝文件
    }

    //递归拷贝文件                      file1 源目录，file2 新目录
    private static boolean listCopyAll(File dir1, File dir2)  {
        if(!dir2.exists()){
            boolean success = dir2.mkdir();//创建新目录
            if(!success){
                System.out.println("mkdir failed");
                return false;}
        }

        File[] files = dir1.listFiles();//获取源目录文件对象列表
        if(files==null){
            System.out.println("get files failed");
            return false;
        }
        boolean res = true;

        for (File file : files) {//file源目录的最近一级子目录及文件
            if (file.isDirectory()) {//如果是目录就继续遍历
                //根据file传入new dir2子目录地址进行创建     listCopyAll中的两个参数同一水平
                res = res&&listCopyAll(file,new File(dir2.getAbsolutePath() + File.separator+ file.getName()));//在自身目录上衔接目录
            } else {
                //如果是文件就创建
                File newFile = new File(dir2.getAbsolutePath() + File.separator+ file.getName());
                try {
                    boolean success = newFile.createNewFile();
                    if(!success){
                        System.out.println("create file failed");
                        return false;
                    }
                    //拷贝文件
                    copyFile(file, newFile);
                }
                catch (IOException e){
                    System.out.println("create file and copy failed");
                    return false;
                }

            }
        }
        return res;
    }

    //copy文件
    private static void copyFile(File file, File newFile) throws IOException {
        //读取源文件信息
        BufferedInputStream bufi = new BufferedInputStream(new FileInputStream(file));
        //写入新文件信息
        BufferedOutputStream bufo = new BufferedOutputStream(new FileOutputStream(newFile));
        int len = 0;
        byte[] buf =  new byte[1024];
        while ((len = bufi.read(buf)) != -1) {
            bufo.write(buf,0,len);
            bufo.flush();
        }
        bufi.close();
        bufo.close();
    }
    private boolean deleteFile(File file) {
        boolean res = true;
        if (file.isFile()) {
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                System.out.println("删除文件失败");
                return false;
            }
            return true;
        }
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("list file fail");
            return false;
        }
        for (File cur : files) {
            if (cur.isDirectory()) {
                res = res & deleteFile(cur);
            }
            else{
                res = res& deleteFile(cur);
            }
        }
        res = res & file.delete();
        return res;
    }
}
