package com.old2dimension.OCEANIA.blImpl.StatisticsBLImplTest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.old2dimension.OCEANIA.blImpl.LabelBLImpl;
import com.old2dimension.OCEANIA.blImpl.StatisticsBLImpl;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;
public class StatisticsBLImplTest {
    @Test
    public void TestGetNumOfUser() {
        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        statisticsBL.setUserRepository(userRepository);
        ArrayList<User> users = new ArrayList<User>();
        User a = new User(1, "a", "123");
        User b = new User(2, "b", "456");
        users.add(a);
        users.add(b);

        when(userRepository.findAll()).thenReturn(users);
        int res = statisticsBL.getNumOfUser();
        Assert.assertEquals(res, 2);
    }

    @Test
    public void TestGetNumOfCode() {
        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        CodeRepository codeRepository = mock(CodeRepository.class);
        statisticsBL.setUserRepository(userRepository);
        statisticsBL.setCodeRepository(codeRepository);

        ArrayList<User> users = new ArrayList<User>();
        User a = new User(1, "a", "123");
        users.add(a);
        ArrayList<Code> codes = new ArrayList<Code>();
        Code p = new Code(1, 1, "p", 100, 10, 5, 1);
        Code q = new Code(2, 1, "q", 500, 200, 10, 1);
        codes.add(p);
        codes.add(q);

        when(userRepository.findAll()).thenReturn(users);
        when(codeRepository.findCodesByUserId(1)).thenReturn(codes);

        HashMap<Integer, Integer> hashMap = (HashMap<Integer, Integer>) statisticsBL.getNumOfCode();
        Assert.assertEquals((int) hashMap.get(1), 2);
    }

    @Test
    public void TestGetCodeMes() {
        StatisticsBLImpl statisticsBL = new StatisticsBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        statisticsBL.setCodeRepository(codeRepository);
        statisticsBL.setVertexLabelRepository(vertexLabelRepository);
        statisticsBL.setEdgeLabelRepository(edgeLabelRepository);
        statisticsBL.setDomainLabelRepository(domainLabelRepository);

        Code code = new Code(1, 1, "code", 100, 50, 20, 1);
        when(codeRepository.findCodeById(1)).thenReturn(code);
        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "114");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "514");
        vertexLabels.add(vertexLabel);
        edgeLabels.add(edgeLabel);

        when(vertexLabelRepository.findVertexLabelsByCodeId(1)).thenReturn(vertexLabels);
        when(edgeLabelRepository.findEdgeLabelsByCodeId(1)).thenReturn(edgeLabels);
        when(domainLabelRepository.findDomainLabelsByCodeId(1)).thenReturn(domainLabels);

        int[] res = statisticsBL.getCodeMes(1);
        Assert.assertEquals(res[0], 100);
        Assert.assertEquals(res[1], 1);
        Assert.assertEquals(res[2], 50);
        Assert.assertEquals(res[3], 1);
        Assert.assertEquals(res[4], 20);
        Assert.assertEquals(res[5], 0);

    }
}
