package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.blImpl.StatisticsBLImpl;
import com.old2dimension.OCEANIA.vo.CodeMesVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.StatisticsContentVO;
import com.old2dimension.OCEANIA.vo.UserIdAndCodeMesVOs;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class StatisticsControllerTest {

    @Test
    public void countUserTest() throws Exception {
        int DEFAULT = 10;
        StatisticsBLImpl statisticsBL = mock(StatisticsBLImpl.class);
        when(statisticsBL.getNumOfUser()).thenReturn(DEFAULT);

        StatisticsController controller = new StatisticsController();
        controller.setStatisticsBL(statisticsBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/statistics/countUser"))
                .andExpect(MockMvcResultMatchers.content().string(Integer.toString(DEFAULT)))
        ;
        verify(statisticsBL).getNumOfUser();
    }

    @Test
    public void getCodeMesVOTest() throws Exception {
        int CODE_ID = 1;
        int[] CODE_MES_VO = {1979,1,3834,1,64,1};
        StatisticsBLImpl statisticsBL = mock(StatisticsBLImpl.class);

        when(statisticsBL.getCodeMes(CODE_ID)).thenReturn(CODE_MES_VO);

        StatisticsController controller = new StatisticsController();
        controller.setStatisticsBL(statisticsBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/statistics/getCodeMesVO/" + Integer.toString(CODE_ID)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(".[0]").value(CODE_MES_VO[0]))
                .andExpect(MockMvcResultMatchers.jsonPath(".[1]").value(CODE_MES_VO[1]))
                .andExpect(MockMvcResultMatchers.jsonPath(".[2]").value(CODE_MES_VO[2]))
                .andExpect(MockMvcResultMatchers.jsonPath(".[3]").value(CODE_MES_VO[3]))
                .andExpect(MockMvcResultMatchers.jsonPath(".[4]").value(CODE_MES_VO[4]))
                .andExpect(MockMvcResultMatchers.jsonPath(".[5]").value(CODE_MES_VO[5]))
        ;
        verify(statisticsBL).getCodeMes(CODE_ID);
    }

    @Test
    public void getAllMesTest() throws Exception {
        int USER_ID = 1;
        int NUM_OF_USER = 2;
        CodeMesVO codeMesVO = new CodeMesVO("iTrust", 1979, 3834, 64, 0, 0, 0);
        ArrayList<CodeMesVO> codeMesVOS = new ArrayList<>();
        codeMesVOS.add(codeMesVO);
        UserIdAndCodeMesVOs userIdAndCodeMesVO = new UserIdAndCodeMesVOs(USER_ID, codeMesVOS);
        ArrayList<UserIdAndCodeMesVOs> userIdAndCodeMesVOs = new ArrayList<>();
        userIdAndCodeMesVOs.add(userIdAndCodeMesVO);
        StatisticsContentVO statisticsContentVO = new StatisticsContentVO(NUM_OF_USER, userIdAndCodeMesVOs);
        StatisticsBLImpl statisticsBL = mock(StatisticsBLImpl.class);

        when(statisticsBL.getAllMes()).thenReturn(ResponseVO.buildSuccess(statisticsContentVO));

        StatisticsController controller = new StatisticsController();
        controller.setStatisticsBL(statisticsBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/statistics/getAllMes/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath(".content.numOfUser").value(NUM_OF_USER))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].userId").value(USER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].codeName").value("iTrust"))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].numOfVertex").value(1979))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].numOfEdge").value(3834))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].numOfDomain").value(64))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].numOfVertexLabel").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].numOfEdgeLabel").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath(".content.content[0].codeMesVOs[0].numOfDomainLabel").value(0))
        ;
        verify(statisticsBL).getAllMes();
    }


}
