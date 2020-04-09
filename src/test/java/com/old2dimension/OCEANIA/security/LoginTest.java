package com.old2dimension.OCEANIA.security;

import com.google.common.collect.Lists;
import com.old2dimension.OCEANIA.blImpl.UserBLImpl;
import com.old2dimension.OCEANIA.dao.AuthorityRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.Authority;
import com.old2dimension.OCEANIA.po.User;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LoginTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loginTest1() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        map.put("pwd", "123456");
        try {
            mvc.perform(MockMvcRequestBuilders.post("/user/login")
                    .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(map)))
                    .andExpect(unauthenticated());
        }
        catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

//    @Test
//    public void loginTest2() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "test");
//        map.put("pwd", "123456");
//
//        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
//        String pwd = encode.encode("123456");
//        User expected = new User();
//        expected.setId(1);
//        expected.setName("test");
//        expected.setPwd(pwd);
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findUserByName("test")).thenReturn(expected);
//
//        List<Authority> authorities = Lists.newArrayList();
//        Authority authority = new Authority();
//        authority.setAuthority("ROLE_USER");
//        authority.setId((long) 2);
//        authorities.add(authority);
//
//        AuthorityRepository authorityRepository = mock(AuthorityRepository.class);
//        when(authorityRepository.findByUserId(1)).thenReturn(authorities);
//
//        UserBLImpl userBL = new UserBLImpl();
//        userBL.setUserRepository(userRepository);
//        userBL.setAuthorityRepository(authorityRepository);
//        try {
//            mvc.perform(MockMvcRequestBuilders.post("/user/login")
//                    .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(map)))
//                    .andExpect(authenticated());
//        }
//        catch (Exception e) {
//            System.out.println(Arrays.toString(e.getStackTrace()));
//        }
//
//    }
}
