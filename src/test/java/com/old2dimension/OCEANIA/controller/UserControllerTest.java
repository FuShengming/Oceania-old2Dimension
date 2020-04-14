package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.blImpl.UserBLImpl;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void signUpTest() throws Exception {
        String encrypted = bCryptPasswordEncoder.encode("123456");
        UserBLImpl userBL = mock(UserBLImpl.class);
        User user = new User(1, "test", encrypted);
        UserVO userVO = new UserVO();
        userVO.setName("test");
        userVO.setPwd(encrypted);

        when(userBL.signUp(userVO)).thenReturn(ResponseVO.buildSuccess(user));

        UserController controller = new UserController(userBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/user/signUp")
                .param("name", "test")
                .param("pwd", "123456"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andDo(print())
        ;

        verify(userBL).signUp(userVO);
    }

}


