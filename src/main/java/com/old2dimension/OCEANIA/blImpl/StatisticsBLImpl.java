package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.StatisticsBL;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.CodeMesVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.StatisticsContentVO;
import com.old2dimension.OCEANIA.vo.UserIdAndCodeMesVOs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class StatisticsBLImpl implements StatisticsBL {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CodeRepository codeRepository;
    @Autowired
    VertexLabelRepository vertexLabelRepository;
    @Autowired
    EdgeLabelRepository edgeLabelRepository;
    @Autowired
    DomainLabelRepository domainLabelRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setVertexLabelRepository(VertexLabelRepository vertexLabelRepository) {
        this.vertexLabelRepository = vertexLabelRepository;
    }

    public void setEdgeLabelRepository(EdgeLabelRepository edgeLabelRepository) {
        this.edgeLabelRepository = edgeLabelRepository;
    }

    public void setDomainLabelRepository(DomainLabelRepository domainLabelRepository) {
        this.domainLabelRepository = domainLabelRepository;
    }

    public int getNumOfUser() {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        return users.size();
    }

    public Map<Integer, Integer> getNumOfCode() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for (User u : users) {
            ArrayList<Code> codes = (ArrayList<Code>) codeRepository.findCodesByUserId(u.getId());
            map.put(u.getId(), codes.size());
        }
        return map;
    }

    public int[] getCodeMes(int codeId) {
        int[] res = new int[6];
        Code code = codeRepository.findCodeById(codeId);
        res[0] = code.getNumOfVertices();
        res[2] = code.getNumOfEdges();
        res[4] = code.getNumOfDomains();

        ArrayList<VertexLabel> vertexLabels = (ArrayList<VertexLabel>) vertexLabelRepository.findVertexLabelsByCodeId(codeId);
        ArrayList<EdgeLabel> edgeLabels = (ArrayList<EdgeLabel>) edgeLabelRepository.findEdgeLabelsByCodeId(codeId);
        ArrayList<DomainLabel> domainLabels = (ArrayList<DomainLabel>) domainLabelRepository.findDomainLabelsByCodeId(codeId);

        res[1] = vertexLabels.size();
        res[3] = edgeLabels.size();
        res[5] = domainLabels.size();

        return res;
    }

    public ResponseVO getAllMes() {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        int numOfUser = users.size();
        if (numOfUser == 0)
            return ResponseVO.buildFailure("There is no user.");
        StatisticsContentVO statisticsContentVO = new StatisticsContentVO();
        statisticsContentVO.setNumOfUser(numOfUser);
        ArrayList<UserIdAndCodeMesVOs> userIdAndCodeMesVOses = new ArrayList<UserIdAndCodeMesVOs>();

        for (User u : users) {
            int userId = u.getId();
            ArrayList<Code> codes = (ArrayList<Code>) codeRepository.findCodesByUserId(userId);
            UserIdAndCodeMesVOs userIdAndCodeMesVOs = new UserIdAndCodeMesVOs();
            userIdAndCodeMesVOs.setUserId(userId);
            ArrayList<CodeMesVO> codeMesVOs = new ArrayList<>();
            for (Code c : codes) {
                ArrayList<VertexLabel> vertexLabels = (ArrayList<VertexLabel>) vertexLabelRepository.findVertexLabelsByCodeIdAndUserId(c.getId(), userId);
                int numOfVertexLabel = vertexLabels.size();
                ArrayList<EdgeLabel> edgeLabels = (ArrayList<EdgeLabel>) edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(c.getId(), userId);
                int numOfEdgeLabel = edgeLabels.size();
                ArrayList<DomainLabel> domainLabels = (ArrayList<DomainLabel>) domainLabelRepository.findDomainLabelsByCodeIdAndUserId(c.getId(), userId);
                int numOfDomainLabel = domainLabels.size();

                CodeMesVO codeMesVO = new CodeMesVO(c.getName(), c.getNumOfVertices(), c.getNumOfEdges(), c.getNumOfDomains(),
                        numOfVertexLabel, numOfEdgeLabel, numOfDomainLabel);
                codeMesVOs.add(codeMesVO);
            }
            userIdAndCodeMesVOs.setCodeMesVOs(codeMesVOs);
            userIdAndCodeMesVOses.add(userIdAndCodeMesVOs);
        }

        statisticsContentVO.setContent(userIdAndCodeMesVOses);
        return ResponseVO.buildSuccess(statisticsContentVO);
    }

}
