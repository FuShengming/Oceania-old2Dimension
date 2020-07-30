package com.old2dimension.OCEANIA.blImpl;

import com.google.common.collect.Lists;
import com.old2dimension.OCEANIA.bl.UserBL;
import com.old2dimension.OCEANIA.dao.AuthorityRepository;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.Authority;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.JwtUser;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service("userBLImpl")
public class UserBLImpl implements UserBL, UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    AuthorityRepository authorityRepository;


    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public ResponseVO getAllUser() {

        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();
        ArrayList<UserVO> users = userList2UserVOList(allUsers);
        return ResponseVO.buildSuccess(users);
    }

    @Transactional
    public ResponseVO signUp(UserVO userInfo) {
        try {
            User user = new User(userInfo);

            User hasUser = userRepository.findUserByName(user.getName());
            if (hasUser != null) {
                return ResponseVO.buildFailure("用户名已存在");
            }
            user = userRepository.save(user);
            System.out.println("dasd");
            Code code = new Code(0, user.getId(), "iTrust", 1979, 3834, 64, 1);
            codeRepository.save(code);
            authorityRepository.insertByUserId(user.getId());
            return ResponseVO.buildSuccess(user);
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseVO.buildFailure("sign up fail");
        }
    }

    private ArrayList<UserVO> userList2UserVOList(ArrayList<User> userList) {
        ArrayList<UserVO> res = new ArrayList<UserVO>();
        for (User u : userList) {
            res.add(new UserVO(u));
        }
        return res;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(s);
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        JwtUser user1 = new JwtUser(user);
        List<Authority> authorities = authorityRepository.findByUserId(user.getId());
        user1.setAuthorities(authorities);
        authorities.forEach(authority -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
            grantedAuthorities.add(grantedAuthority);
        });
        return user1;
    }
    
    @Override
    public ResponseVO getNamesByIds(int[] userIds) {
        List<UserVO> result = new ArrayList<>();
        for (int i = 0; i < userIds.length; i++) {
            User user = userRepository.findUserById(userIds[i]);
            if (user != null) {
                user.setPwd(null);
                result.add(new UserVO(user));
            }
        }
        return ResponseVO.buildSuccess(result);
    }

    @Override
    public ResponseVO getUserByName(String name) {
        User user = userRepository.findUserByName(name);
        if (user != null) {
            UserVO userVO = new UserVO(user);
            userVO.setPwd(null);
            return ResponseVO.buildSuccess(userVO);
        }
        return ResponseVO.buildFailure("No user");
    }

    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

}
