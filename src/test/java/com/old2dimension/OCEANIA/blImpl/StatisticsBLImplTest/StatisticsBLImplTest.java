package com.old2dimension.OCEANIA.blImpl.StatisticsBLImplTest;

import com.old2dimension.OCEANIA.blImpl.StatisticsBLImpl;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.CodeMesVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.StatisticsContentVO;
import com.old2dimension.OCEANIA.vo.UserIdAndCodeMesVOs;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class StatisticsBLImplTest {
    @Test
    public void TestGetAllMes() {
        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
        User u1 = new User(1, "gr", "123456");
        User u2 = new User(2, "rg", "654321");
        ArrayList<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(users);

        Code code = new Code(1, 1, "itrust", 1, 1, 0, 1);
        ArrayList<Code> codes1 = new ArrayList<>();
        ArrayList<Code> codes2 = new ArrayList<>();
        codes1.add(code);
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodesByUserId(1)).thenReturn(codes1);
        when(codeRepository.findCodesByUserId(2)).thenReturn(codes2);

        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vertexLabel = new VertexLabel(1,1,1,"vertex","label");
        vertexLabels.add(vertexLabel);
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();

        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        when(vertexLabelRepository.findVertexLabelsByCodeIdAndUserId(1, 1)).thenReturn(vertexLabels);
        when(edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(1, 1)).thenReturn(edgeLabels);
        when(domainLabelRepository.findDomainLabelsByCodeIdAndUserId(1, 1)).thenReturn(domainLabels);

        statisticsBL.setUserRepository(userRepository);
        statisticsBL.setCodeRepository(codeRepository);
        statisticsBL.setVertexLabelRepository(vertexLabelRepository);
        statisticsBL.setEdgeLabelRepository(edgeLabelRepository);
        statisticsBL.setDomainLabelRepository(domainLabelRepository);

        ResponseVO responseVO = statisticsBL.getAllMes();
        StatisticsContentVO statisticsContentVO = (StatisticsContentVO) responseVO.getContent();

        ArrayList<UserIdAndCodeMesVOs> userIdAndCodeMesVOses = statisticsContentVO.getContent();
        UserIdAndCodeMesVOs userIdAndCodeMesVOs = userIdAndCodeMesVOses.get(0);
        CodeMesVO codeMesVO = userIdAndCodeMesVOs.getCodeMesVOs().get(0);

        Assert.assertEquals(userIdAndCodeMesVOses.size(), 2);
        Assert.assertEquals(statisticsContentVO.getNumOfUser(), 2);

        Assert.assertEquals(codeMesVO.getCodeName(), "itrust");
        Assert.assertEquals(codeMesVO.getNumOfVertex(), 1);
        Assert.assertEquals(codeMesVO.getNumOfVertexLabel(), 1);
        Assert.assertEquals(codeMesVO.getNumOfEdge(), 1);
        Assert.assertEquals(codeMesVO.getNumOfEdgeLabel(), 0);
        Assert.assertEquals(codeMesVO.getNumOfDomain(), 0);
        Assert.assertEquals(codeMesVO.getNumOfDomainLabel(), 0);

    }
}








//package com.old2dimension.OCEANIA.blImpl.StatisticsBLImplTest;
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.old2dimension.OCEANIA.blImpl.LabelBLImpl;
//import com.old2dimension.OCEANIA.blImpl.StatisticsBLImpl;
//import com.old2dimension.OCEANIA.dao.*;
//import com.old2dimension.OCEANIA.po.*;
//import com.old2dimension.OCEANIA.vo.*;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static org.mockito.Mockito.*;
//public class StatisticsBLImplTest {
//    @Test
//    public void TestGetNumOfUser() {
//        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        statisticsBL.setUserRepository(userRepository);
//        ArrayList<User> users = new ArrayList<User>();
//        User a = new User(1, "a", "123");
//        User b = new User(2, "b", "456");
//        users.add(a);
//        users.add(b);
//
//        when(userRepository.findAll()).thenReturn(users);
//        int res = statisticsBL.getNumOfUser();
//        Assert.assertEquals(res, 2);
//    }
//
//    @Test
//    public void TestGetNumOfCode() {
//        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        CodeRepository codeRepository = mock(CodeRepository.class);
//        statisticsBL.setUserRepository(userRepository);
//        statisticsBL.setCodeRepository(codeRepository);
//
//        ArrayList<User> users = new ArrayList<User>();
//        User a = new User(1, "a", "123");
//        users.add(a);
//        ArrayList<Code> codes = new ArrayList<Code>();
//        Code p = new Code(1, 1, "p", 100, 10, 5, 1);
//        Code q = new Code(2, 1, "q", 500, 200, 10, 1);
//        codes.add(p);
//        codes.add(q);
//
//        when(userRepository.findAll()).thenReturn(users);
//        when(codeRepository.findCodesByUserId(1)).thenReturn(codes);
//
//        HashMap<Integer, Integer> hashMap = (HashMap<Integer, Integer>) statisticsBL.getNumOfCode();
//        Assert.assertEquals((int) hashMap.get(1), 2);
//    }
//
//    @Test
//    public void TestGetCodeMes() {
//        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
//        CodeRepository codeRepository = mock(CodeRepository.class);
//        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
//        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
//        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
//        statisticsBL.setCodeRepository(codeRepository);
//        statisticsBL.setVertexLabelRepository(vertexLabelRepository);
//        statisticsBL.setEdgeLabelRepository(edgeLabelRepository);
//        statisticsBL.setDomainLabelRepository(domainLabelRepository);
//
//        Code code = new Code(1, 1, "code", 100, 50, 20, 1);
//        when(codeRepository.findCodeById(1)).thenReturn(code);
//        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
//        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
//        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
//        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "114");
//        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "514");
//        vertexLabels.add(vertexLabel);
//        edgeLabels.add(edgeLabel);
//
//        when(vertexLabelRepository.findVertexLabelsByCodeId(1)).thenReturn(vertexLabels);
//        when(edgeLabelRepository.findEdgeLabelsByCodeId(1)).thenReturn(edgeLabels);
//        when(domainLabelRepository.findDomainLabelsByCodeId(1)).thenReturn(domainLabels);
//
//        int[] res = statisticsBL.getCodeMesVO(1);
//        Assert.assertEquals(res[0], 100);
//        Assert.assertEquals(res[1], 1);
//        Assert.assertEquals(res[2], 50);
//        Assert.assertEquals(res[3], 1);
//        Assert.assertEquals(res[4], 20);
//        Assert.assertEquals(res[5], 0);
//
//    }
//}
