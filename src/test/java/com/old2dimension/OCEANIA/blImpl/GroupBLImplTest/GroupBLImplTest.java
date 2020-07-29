//package com.old2dimension.OCEANIA.blImpl.GroupBLImplTest;
//
//import com.old2dimension.OCEANIA.blImpl.GroupBLImpl;
//import com.old2dimension.OCEANIA.dao.AnnouncementRepository;
//import com.old2dimension.OCEANIA.dao.GroupMemberRepository;
//import com.old2dimension.OCEANIA.dao.GroupRepository;
//import com.old2dimension.OCEANIA.dao.UserRepository;
//import com.old2dimension.OCEANIA.po.Announcement;
//import com.old2dimension.OCEANIA.po.Group;
//import com.old2dimension.OCEANIA.po.GroupMember;
//import com.old2dimension.OCEANIA.po.User;
//import com.old2dimension.OCEANIA.vo.*;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import static org.mockito.Mockito.*;
//
//public class GroupBLImplTest {
//    @Test
//    public void findUserTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        groupBL.setUserRepository(userRepository);
//
//        User user = new User(1, "testUser", "testPwd");
//        when(userRepository.findUserByName("testUser")).thenReturn(user);
//
//        ResponseVO responseVO = groupBL.findUser("testUser");
//        Assert.assertEquals(((User)responseVO.getContent()).getId(), 1);
//        Assert.assertEquals(((User)responseVO.getContent()).getName(), "testUser");
//        Assert.assertEquals(((User)responseVO.getContent()).getPwd(), "testPwd");
//    }
//
//    @Test
//    public void findUserTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        groupBL.setUserRepository(userRepository);
//        User user = null;
//
//        when(userRepository.findUserByName("testUser")).thenReturn(user);
//
//        ResponseVO responseVO = groupBL.findUser("testUser");
//        Assert.assertEquals(responseVO.getContent(), null);
//        Assert.assertEquals(responseVO.getMessage(), "do not find this user");
//
//    }
//
//    @Test
//    public void createGroupTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        User user = new User(1, "testUser", "testPwd");
//        Group group = new Group(1, "testGroup");
//        GroupMember leader = new GroupMember(1, 1, 1);
//        leader.setId(1);
//        GroupNameAndCreatorIdForm groupNameAndCreatorIdForm = new GroupNameAndCreatorIdForm();
//        groupNameAndCreatorIdForm.setName("testGroup");
//        groupNameAndCreatorIdForm.setCreatorId(1);
//
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupRepository.save(any())).thenReturn(group);
//        when(groupMemberRepository.save(any())).thenReturn(leader);
//
//        ResponseVO responseVO = groupBL.createGroup(groupNameAndCreatorIdForm);
//        Assert.assertEquals(((Group) responseVO.getContent()).getId(), 1);
//        Assert.assertEquals(((Group) responseVO.getContent()).getName(), "testGroup");
//    }
//
//    @Test
//    public void createGroupTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        groupBL.setUserRepository(userRepository);
//
//        GroupNameAndCreatorIdForm groupNameAndCreatorIdForm = new GroupNameAndCreatorIdForm();
//        groupNameAndCreatorIdForm.setName("testGroup1");
//        groupNameAndCreatorIdForm.setCreatorId(1);
//
//        when(userRepository.findUserById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.createGroup(groupNameAndCreatorIdForm);
//        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
//    }
//
//    @Test
//    public void createGroupTest3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//
//        User user = new User(1, "testUser", "testPwd");
//        Group group = new Group(0, "testGroup");
//        GroupNameAndCreatorIdForm groupNameAndCreatorIdForm = new GroupNameAndCreatorIdForm();
//        groupNameAndCreatorIdForm.setName("testGroup");
//        groupNameAndCreatorIdForm.setCreatorId(1);
//
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupRepository.save(any())).thenReturn(group);
//
//        ResponseVO responseVO = groupBL.createGroup(groupNameAndCreatorIdForm);
//        Assert.assertEquals(responseVO.getMessage(), "creating group failed");
//    }
//
//    @Test
//    public void createGroupTest4() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        User user = new User(1, "testUser", "testPwd");
//        Group group = new Group(1, "testGroup");
//        GroupMember leader = new GroupMember(1, 1, 1);
//        leader.setId(0);
//        GroupNameAndCreatorIdForm groupNameAndCreatorIdForm = new GroupNameAndCreatorIdForm();
//        groupNameAndCreatorIdForm.setName("testGroup");
//        groupNameAndCreatorIdForm.setCreatorId(1);
//
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupRepository.save(any())).thenReturn(group);
//        when(groupMemberRepository.save(any())).thenReturn(leader);
//
//        ResponseVO responseVO = groupBL.createGroup(groupNameAndCreatorIdForm);
//        Assert.assertEquals(responseVO.getMessage(), "creating group failed");
//    }
//
//    @Test
//    public void setGroupLeader1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setGroupId(1);
//        groupIdAndUserForm.setUserId(2);
//        Group group = new Group(1, "testGroup");
//        User target = new User(2, "targetName", "targetPwd");
//        GroupMember newLeader = new GroupMember(1, 2, 0);
//        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
//        GroupMember groupMember = new GroupMember(1, 1, 1);
//        groupMembers.add(groupMember);
//        ArrayList<GroupMember> newGroupMembers = new ArrayList<GroupMember>();
//        GroupMember a = new GroupMember(1, 1, 0);
//        GroupMember b = new GroupMember(1, 2, 1);
//        newGroupMembers.add(a);
//        newGroupMembers.add(b);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(2)).thenReturn(target);
//        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
//        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 2)).thenReturn(newLeader);
//        when(groupMemberRepository.saveAll(any())).thenReturn(newGroupMembers);
//
//        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getContent(), "Modifying leader succeed.");
//    }
//
//    @Test
//    public void setGroupLeader2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setGroupId(1);
//        groupIdAndUserForm.setUserId(2);
//
//        when(groupRepository.findGroupById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
//    }
//
//    @Test
//    public void setGroupLeader3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setGroupId(1);
//        groupIdAndUserForm.setUserId(2);
//        Group group = new Group(1, "testGroup");
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(2)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
//    }
//
//    @Test
//    public void setGroupLeader4() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setGroupId(1);
//        groupIdAndUserForm.setUserId(2);
//        Group group = new Group(1, "testGroup");
//        User target = new User(2, "targetName", "targetPwd");
//        GroupMember newLeader = new GroupMember(1, 2, 0);
//        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(2)).thenReturn(target);
//        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
//
//        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "this group has no leader!");
//    }
//
//    @Test
//    public void setGroupLeader5() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setGroupId(1);
//        groupIdAndUserForm.setUserId(2);
//        Group group = new Group(1, "testGroup");
//        User target = new User(2, "targetName", "targetPwd");
//        GroupMember newLeader = new GroupMember(1, 2, 0);
//        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
//        GroupMember groupMember = new GroupMember(1, 1, 1);
//        groupMembers.add(groupMember);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(2)).thenReturn(target);
//        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
//        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 2)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "This user is not in the group.");
//    }
//
//    @Test
//    public void setGroupLeader6() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setGroupId(1);
//        groupIdAndUserForm.setUserId(2);
//        Group group = new Group(1, "testGroup");
//        User target = new User(2, "targetName", "targetPwd");
//        GroupMember newLeader = new GroupMember(1, 2, 0);
//        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
//        GroupMember groupMember = new GroupMember(1, 1, 1);
//        groupMembers.add(groupMember);
//        ArrayList<GroupMember> newGroupMembers = new ArrayList<GroupMember>();
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(2)).thenReturn(target);
//        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
//        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 2)).thenReturn(newLeader);
//        when(groupMemberRepository.saveAll(any())).thenReturn(newGroupMembers);
//
//        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "Modifying group leader failed.");
//    }
//
//    @Test
//    public void quitGroupTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//        User user = new User(1, "testName", "testPwd");
//        GroupMember groupMember = new GroupMember(1, 1, 0);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
//        when(groupMemberRepository.countByGroupId(1)).thenReturn(0);
//
//        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getContent(), "you has quited the group");
//    }
//
//    @Test
//    public void quitGroupTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//
//        when(groupRepository.findGroupById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
//    }
//
//    @Test
//    public void quitGroupTest3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
//    }
//
//    @Test
//    public void quitGroupTest4() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//        User user = new User(1, "testName", "testPwd");
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "The group do not have this user.");
//    }
//
//    @Test
//    public void quitGroupTest5() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//        User user = new User(1, "testName", "testPwd");
//        GroupMember groupMember = new GroupMember(1, 1, 1);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
//
//        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "leader must transfer the possession of leader before quiting group");
//    }
//
//    @Test
//    public void joinGroupTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//        User user = new User(1, "testName", "testPwd");
//        GroupMember groupMember = new GroupMember(1, 1, 0);
//        groupMember.setId(1);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupMemberRepository.save(any())).thenReturn(groupMember);
//
//        ResponseVO responseVO = groupBL.joinGroup(groupIdAndUserForm);
//        Assert.assertEquals(((GroupMember) responseVO.getContent()).getUserId(), 1);
//        Assert.assertEquals(((GroupMember) responseVO.getContent()).getGroupId(), 1);
//        Assert.assertEquals(((GroupMember) responseVO.getContent()).getIsLeader(), 0);
//    }
//
//    @Test
//    public void joinGroupTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//
//        when(groupRepository.findGroupById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.joinGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
//    }
//
//    @Test
//    public void joinGroupTest3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.joinGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
//    }
//
//    @Test
//    public void joinGroupTest4() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        UserRepository userRepository = mock(UserRepository.class);
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setUserRepository(userRepository);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
//        groupIdAndUserForm.setUserId(1);
//        groupIdAndUserForm.setGroupId(1);
//        Group group = new Group(1, "testGroup");
//        User user = new User(1, "testName", "testPwd");
//        GroupMember groupMember = new GroupMember(1, 1, 0);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(userRepository.findUserById(1)).thenReturn(user);
//        when(groupMemberRepository.save(any())).thenReturn(groupMember);
//
//        ResponseVO responseVO = groupBL.joinGroup(groupIdAndUserForm);
//        Assert.assertEquals(responseVO.getMessage(), "Joining group failed");
//    }
//
//    @Test
//    public void searchGroupByUserTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        Group group1 = new Group(1, "group1");
//        Group group2 = new Group(2, "group2");
//        Group group3 = new Group(5, "group5");
//        ArrayList<GroupMember> groupMembers = new ArrayList<>();
//        GroupMember groupMember1 = new GroupMember(1, 1, 1);
//        groupMember1.setId(1);
//        GroupMember groupMember2 = new GroupMember(2, 1, 0);
//        groupMember2.setId(2);
//        GroupMember groupMember3 = new GroupMember(5, 1, 0);
//        groupMember3.setId(3);
//        groupMembers.add(groupMember1);
//        groupMembers.add(groupMember2);
//        groupMembers.add(groupMember3);
//
//        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(groupMembers);
//        when(groupRepository.findGroupById(1)).thenReturn(group1);
//        when(groupRepository.findGroupById(2)).thenReturn(group2);
//        when(groupRepository.findGroupById(5)).thenReturn(group3);
//
//        ResponseVO responseVO = groupBL.searchGroupByUser(1);
//        Assert.assertEquals(((ArrayList<Group>) responseVO.getContent()).size(), 3);
//        Assert.assertEquals(((ArrayList<Group>) responseVO.getContent()).get(1).getName(), "group2");
//    }
//
//    @Test
//    public void searchGroupByUserTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.searchGroupByUser(1);
//        Assert.assertEquals(responseVO.getMessage(), "Getting group list failed.");
//    }
//
//    @Test
//    public void searchGroupByUserTest3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        ArrayList<GroupMember> groupMembers = new ArrayList<>();
//
//        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(groupMembers);
//
//        ResponseVO responseVO = groupBL.searchGroupByUser(1);
//        Assert.assertEquals(((ArrayList<Group>) responseVO.getContent()).size(), 0);
//    }
//
//    @Test
//    public void searchGroupByUserTest4() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        Group group1 = new Group(1, "group1");
//        Group group2 = new Group(2, "group2");
//        ArrayList<GroupMember> groupMembers = new ArrayList<>();
//        GroupMember groupMember1 = new GroupMember(1, 1, 1);
//        groupMember1.setId(1);
//        GroupMember groupMember2 = new GroupMember(2, 1, 0);
//        groupMember2.setId(2);
//        GroupMember groupMember3 = new GroupMember(5, 1, 0);
//        groupMember3.setId(3);
//        groupMembers.add(groupMember1);
//        groupMembers.add(groupMember2);
//        groupMembers.add(groupMember3);
//
//        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(groupMembers);
//        when(groupRepository.findGroupById(1)).thenReturn(group1);
//        when(groupRepository.findGroupById(2)).thenReturn(group2);
//        when(groupRepository.findGroupById(5)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.searchGroupByUser(1);
//        Assert.assertEquals(responseVO.getMessage(), "Getting group list failed.");
//    }
//
//    @Test
//    public void getGroupMembersTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        Group group = new Group(1, "testGroup");
//        ArrayList<GroupMember> groupMembers = new ArrayList<>();
//        GroupMember groupMember1 = new GroupMember(1, 1, 1);
//        groupMember1.setId(1);
//        GroupMember groupMember2 = new GroupMember(1, 2, 0);
//        groupMember2.setId(2);
//        GroupMember groupMember3 = new GroupMember(1, 5, 0);
//        groupMember3.setId(5);
//        groupMembers.add(groupMember1);
//        groupMembers.add(groupMember2);
//        groupMembers.add(groupMember3);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);
//
//        ResponseVO responseVO = groupBL.getGroupMembers(1);
//        Assert.assertEquals(((ArrayList<GroupMember>) responseVO.getContent()).size(), 3);
//        Assert.assertEquals(((ArrayList<GroupMember>) responseVO.getContent()).get(2).getUserId(), 5);
//    }
//
//    @Test
//    public void getGroupMembersTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//
//        when(groupRepository.findGroupById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.getGroupMembers(1);
//        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
//    }
//
//    @Test
//    public void getGroupMembersTest3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        Group group = new Group(1, "testGroup");
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.getGroupMembers(1);
//        Assert.assertEquals(responseVO.getMessage(), "Getting member list failed.");
//    }
//
//    @Test
//    public void getGroupMembersTest4() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setGroupMemberRepository(groupMemberRepository);
//
//        Group group = new Group(1, "testGroup");
//        ArrayList<GroupMember> groupMembers = new ArrayList<>();
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);
//
//        ResponseVO responseVO = groupBL.getGroupMembers(1);
//        Assert.assertEquals(responseVO.getMessage(), "group does not exist.");
//    }
//
//    @Test
//    public void getGroupAnnouncementsTest1() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setAnnouncementRepository(announcementRepository);
//
//        Group group = new Group(1, "testGroup");
//        ArrayList<Announcement> announcements = new ArrayList<>();
//        Announcement announcement = new Announcement();
//        announcement.setId(1);
//        announcement.setTitle("testTitle");
//        announcement.setContent("testContent");
//        announcement.setGroupId(1);
//        Date date = new Date();
//        announcement.setReleaseDate(date);
//        announcements.add(announcement);
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(announcementRepository.findAllByGroupId(1)).thenReturn(announcements);
//
//        ResponseVO responseVO = groupBL.getGroupAnnouncements(1);
//        Assert.assertEquals(((ArrayList<Announcement>) responseVO.getContent()).size(), 1);
//        Assert.assertEquals(((ArrayList<Announcement>) responseVO.getContent()).get(0).getTitle(), "testTitle");
//        Assert.assertEquals(((ArrayList<Announcement>) responseVO.getContent()).get(0).getContent(), "testContent");
//    }
//
//    @Test
//    public void getGroupAnnouncementsTest2() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//
//        when(groupRepository.findGroupById(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.getGroupAnnouncements(1);
//        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
//    }
//
//    @Test
//    public void getGroupAnnouncementsTest3() {
//        GroupBLImpl groupBL = new GroupBLImpl();
//        GroupRepository groupRepository = mock(GroupRepository.class);
//        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
//        groupBL.setGroupRepository(groupRepository);
//        groupBL.setAnnouncementRepository(announcementRepository);
//
//        Group group = new Group(1, "testGroup");
//
//        when(groupRepository.findGroupById(1)).thenReturn(group);
//        when(announcementRepository.findAllByGroupId(1)).thenReturn(null);
//
//        ResponseVO responseVO = groupBL.getGroupAnnouncements(1);
//        Assert.assertEquals(responseVO.getMessage(), "Getting announcement list failed.");
//    }
//}
