package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.blImpl.CodeBLImpl;
import com.old2dimension.OCEANIA.vo.CodeAndDateForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class CodeControllerTest {
//    @Test
//    public void getCodesByUserIdTest() throws Exception {
//        int USER_ID = 1;
//        CodeBLImpl codeBL = mock(CodeBLImpl.class);
//        CodeAndDateForm codeAndDateForm = new CodeAndDateForm(1, "iTrust", new Date());
//        ArrayList<CodeAndDateForm> codeAndDateForms = new ArrayList<>();
//        codeAndDateForms.add(codeAndDateForm);
//        when(codeBL.getCodesByUserId(USER_ID)).thenReturn(ResponseVO.buildSuccess(codeAndDateForms));
//        CodeController controller = new CodeController(codeBL);
//        MockMvc mockMvc = standaloneSetup(controller).build();
//        String url = "/code/getCodesByUserId/" + Integer.toString(USER_ID);
//        mockMvc.perform(get(url))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].codeId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].codeId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].codeId").value(1))
//        ;
//    }
}
