package com.old2dimension.OCEANIA.blImpl.UserBLTest;


import com.old2dimension.OCEANIA.blImpl.UserBLImpl;
import com.old2dimension.OCEANIA.dao.AuthorityRepository;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UserBLTest {

    @Test
    public void signUpTest1() {
        UserVO userVO = new UserVO(1, "test", "123456");
        User expected = new User();
        expected.setId(1);
        expected.setName("test");
        expected.setPwd("123456");
        UserBLImpl userBL = new UserBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findUserByName("test")).thenReturn(expected);
        CodeRepository codeRepository = mock(CodeRepository.class);
        userBL.setUserRepository(userRepository);
        userBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = userBL.signUp(userVO);
        Assert.assertEquals(responseVO.getMessage(), "用户名已存在");
    }

    @Test
    public void signUpTest2(){
        UserVO userVO =new UserVO(1,"test","123456");
        User user = new User(userVO);
        User expected = new User();
        expected.setId(1);
        expected.setPwd("123456");
        expected.setName("test");

        UserBLImpl userBL = new UserBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findUserByName("test")).thenReturn(null);
        when(userRepository.save(any())).thenReturn(expected);
        when(userRepository.findUserById(1)).thenReturn(expected);
        AuthorityRepository authorityRepository = mock(AuthorityRepository.class);
        Code code = new Code(0, expected.getId(), "iTrust", 1979, 3834, 64,1);
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.save(any())).thenReturn(code);
        userBL.setUserRepository(userRepository);
        userBL.setCodeRepository(codeRepository);
        userBL.setAuthorityRepository(authorityRepository);
        ResponseVO responseVO = userBL.signUp(userVO);
        Assert.assertEquals(((User)responseVO.getContent()).getId(), user.getId());
    }



    @Test
    public void signUpTest3() {
        UserVO userVO = new UserVO(0, "test", "123456");
        User user = new User(userVO);
        User expected = new User();
        expected.setId(1);
        expected.setPwd("123456");
        expected.setName("test");

        UserBLImpl userBL = new UserBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findUserByName("test")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(expected);
        when(userRepository.findUserById(1)).thenReturn(expected);
        Code code = new Code(0, expected.getId(), "iTrust", 1979, 3834, 64, 1);
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.save(code)).thenReturn(code);
        userBL.setUserRepository(userRepository);
        userBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = userBL.signUp(userVO);
        Assert.assertEquals(responseVO.getMessage(), "sign up fail");
    }
}
