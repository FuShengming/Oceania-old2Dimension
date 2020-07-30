package com.old2dimension.OCEANIA.blImpl.GroupBLImplTest;

import com.old2dimension.OCEANIA.MessageServer.AnnouncementServer;
import com.old2dimension.OCEANIA.MessageServer.InvitationServer;
import com.old2dimension.OCEANIA.blImpl.GroupBLImpl;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

public class GroupBLImplTest {
    @Test
    public void findUserTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        groupBL.setUserRepository(userRepository);

        User user = new User(1, "testUser", "testPwd");
        when(userRepository.findUserByName("testUser")).thenReturn(user);

        ResponseVO responseVO = groupBL.findUser("testUser");
        Assert.assertEquals(((User)responseVO.getContent()).getId(), 1);
        Assert.assertEquals(((User)responseVO.getContent()).getName(), "testUser");
        Assert.assertEquals(((User)responseVO.getContent()).getPwd(), "testPwd");
    }

    @Test
    public void findUserTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        groupBL.setUserRepository(userRepository);
        User user = null;

        when(userRepository.findUserByName("testUser")).thenReturn(user);

        ResponseVO responseVO = groupBL.findUser("testUser");
        Assert.assertEquals(responseVO.getContent(), null);
        Assert.assertEquals(responseVO.getMessage(), "do not find this user");

    }

    @Test
    public void createGroupTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        User user = new User(1, "testUser", "testPwd");
        Group group = new Group(1, "testGroup");
        GroupMember leader = new GroupMember(1, 1, 1);
        leader.setId(1);
        CreateGroupForm createGroupForm = new CreateGroupForm();
        createGroupForm.setName("testGroup");
        createGroupForm.setCreatorId(1);

        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupRepository.save(any())).thenReturn(group);
        when(groupMemberRepository.save(any())).thenReturn(leader);

        ResponseVO responseVO = groupBL.createGroup(createGroupForm);
        Assert.assertEquals(((Group) responseVO.getContent()).getId(), 1);
        Assert.assertEquals(((Group) responseVO.getContent()).getName(), "testGroup");
    }

    @Test
    public void createGroupTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        groupBL.setUserRepository(userRepository);

        CreateGroupForm createGroupForm = new CreateGroupForm();
        createGroupForm.setName("testGroup1");
        createGroupForm.setCreatorId(1);

        when(userRepository.findUserById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.createGroup(createGroupForm);
        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
    }

    @Test
    public void createGroupTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);

        User user = new User(1, "testUser", "testPwd");
        Group group = new Group(0, "testGroup");
        CreateGroupForm createGroupForm = new CreateGroupForm();
        createGroupForm.setName("testGroup");
        createGroupForm.setCreatorId(1);

        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupRepository.save(any())).thenReturn(group);

        ResponseVO responseVO = groupBL.createGroup(createGroupForm);
        Assert.assertEquals(responseVO.getMessage(), "creating group failed");
    }

    @Test
    public void createGroupTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        User user = new User(1, "testUser", "testPwd");
        Group group = new Group(1, "testGroup");
        GroupMember leader = new GroupMember(1, 1, 1);
        leader.setId(0);
        CreateGroupForm createGroupForm = new CreateGroupForm();
        createGroupForm.setName("testGroup");
        createGroupForm.setCreatorId(1);

        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupRepository.save(any())).thenReturn(group);
        when(groupMemberRepository.save(any())).thenReturn(leader);

        ResponseVO responseVO = groupBL.createGroup(createGroupForm);
        Assert.assertEquals(responseVO.getMessage(), "creating group failed");
    }

    @Test
    public void setGroupLeader1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setGroupId(1);
        groupIdAndUserForm.setUserId(2);
        Group group = new Group(1, "testGroup");
        User target = new User(2, "targetName", "targetPwd");
        GroupMember newLeader = new GroupMember(1, 2, 0);
        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
        GroupMember groupMember = new GroupMember(1, 1, 1);
        groupMembers.add(groupMember);
        ArrayList<GroupMember> newGroupMembers = new ArrayList<GroupMember>();
        GroupMember a = new GroupMember(1, 1, 0);
        GroupMember b = new GroupMember(1, 2, 1);
        newGroupMembers.add(a);
        newGroupMembers.add(b);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(2)).thenReturn(target);
        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 2)).thenReturn(newLeader);
        when(groupMemberRepository.saveAll(any())).thenReturn(newGroupMembers);

        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getContent(), "Modifying leader succeed.");
    }

    @Test
    public void setGroupLeader2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setGroupRepository(groupRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setGroupId(1);
        groupIdAndUserForm.setUserId(2);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
    }

    @Test
    public void setGroupLeader3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setGroupId(1);
        groupIdAndUserForm.setUserId(2);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(2)).thenReturn(null);

        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
    }

    @Test
    public void setGroupLeader4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setGroupId(1);
        groupIdAndUserForm.setUserId(2);
        Group group = new Group(1, "testGroup");
        User target = new User(2, "targetName", "targetPwd");
        GroupMember newLeader = new GroupMember(1, 2, 0);
        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(2)).thenReturn(target);
        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);

        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "this group has no leader!");
    }

    @Test
    public void setGroupLeader5() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setGroupId(1);
        groupIdAndUserForm.setUserId(2);
        Group group = new Group(1, "testGroup");
        User target = new User(2, "targetName", "targetPwd");
        GroupMember newLeader = new GroupMember(1, 2, 0);
        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
        GroupMember groupMember = new GroupMember(1, 1, 1);
        groupMembers.add(groupMember);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(2)).thenReturn(target);
        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 2)).thenReturn(null);

        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "This user is not in the group.");
    }

    @Test
    public void setGroupLeader6() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setGroupId(1);
        groupIdAndUserForm.setUserId(2);
        Group group = new Group(1, "testGroup");
        User target = new User(2, "targetName", "targetPwd");
        GroupMember newLeader = new GroupMember(1, 2, 0);
        ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
        GroupMember groupMember = new GroupMember(1, 1, 1);
        groupMembers.add(groupMember);
        ArrayList<GroupMember> newGroupMembers = new ArrayList<GroupMember>();

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(2)).thenReturn(target);
        when(groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(1, 1)).thenReturn(groupMembers);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 2)).thenReturn(newLeader);
        when(groupMemberRepository.saveAll(any())).thenReturn(newGroupMembers);

        ResponseVO responseVO = groupBL.setGroupLeader(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "Modifying group leader failed.");
    }

    @Test
    public void quitGroupTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setUserId(1);
        groupIdAndUserForm.setGroupId(1);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 0);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(groupMemberRepository.countByGroupId(1)).thenReturn(0);

        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getContent(), "you has quited the group");
    }

    @Test
    public void quitGroupTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setGroupRepository(groupRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setUserId(1);
        groupIdAndUserForm.setGroupId(1);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
    }

    @Test
    public void quitGroupTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setUserId(1);
        groupIdAndUserForm.setGroupId(1);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
    }

    @Test
    public void quitGroupTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setUserId(1);
        groupIdAndUserForm.setGroupId(1);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(null);

        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "The group do not have this user.");
    }

    @Test
    public void quitGroupTest5() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        GroupIdAndUserForm groupIdAndUserForm = new GroupIdAndUserForm();
        groupIdAndUserForm.setUserId(1);
        groupIdAndUserForm.setGroupId(1);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 1);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);

        ResponseVO responseVO = groupBL.quitGroup(groupIdAndUserForm);
        Assert.assertEquals(responseVO.getMessage(), "leader must transfer the possession of leader before quiting group");
    }

    @Test
    public void joinGroupTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        InvitationServer invitationServer = mock(InvitationServer.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setInvitationRepository(invitationRepository);
        groupBL.setInvitationServer(invitationServer);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 0);
        groupMember.setId(1);
        Invitation oldInvitation = new Invitation(1, 1, 1, 2, 0, 0);
        Invitation newInvitation = new Invitation(1, 1, 1, 2, 1, 1);
        ArrayList<Invitation> invitations = new ArrayList<>();
        invitations.add(oldInvitation);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.save(any())).thenReturn(groupMember);
        when(invitationRepository.findInvitationById(1)).thenReturn(oldInvitation);
        when(invitationRepository.save(any())).thenReturn(newInvitation);
        when(invitationRepository.findInvitationsByUserIdAndHasRead(1, 0)).thenReturn(invitations);
        doNothing().when(invitationServer).sendInfo(1, 1);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(((GroupMember) responseVO.getContent()).getUserId(), 1);
        Assert.assertEquals(((GroupMember) responseVO.getContent()).getIsLeader(), 0);
    }

    @Test
    public void joinGroupTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setGroupRepository(groupRepository);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
    }

    @Test
    public void joinGroupTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(responseVO.getMessage(), "This user does not exist.");
    }

    @Test
    public void joinGroupTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 0);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.save(any())).thenReturn(groupMember);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(responseVO.getMessage(), "Joining group failed");
    }

    @Test
    public void joinGroupTest5() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 0);
        groupMember.setId(1);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.save(any())).thenReturn(groupMember);
        when(invitationRepository.findInvitationById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(responseVO.getMessage(), "Finding invitation failed.");
    }

    @Test
    public void joinGroupTest6() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 0);
        groupMember.setId(1);
        Invitation oldInvitation = new Invitation(1, 1, 5, 2, 0, 0);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.save(any())).thenReturn(groupMember);
        when(invitationRepository.findInvitationById(1)).thenReturn(oldInvitation);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(responseVO.getMessage(), "Do not have the access of reading invitation.");
    }

    @Test
    public void joinGroupTest7() {
        GroupBLImpl groupBL = new GroupBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        InvitationServer invitationServer = mock(InvitationServer.class);
        groupBL.setUserRepository(userRepository);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setInvitationRepository(invitationRepository);
        groupBL.setInvitationServer(invitationServer);

        Invitation invitation = new Invitation(1, 1, 1, 2, 0, 0);
        Group group = new Group(1, "testGroup");
        User user = new User(1, "testName", "testPwd");
        GroupMember groupMember = new GroupMember(1, 1, 0);
        groupMember.setId(1);
        Invitation oldInvitation = new Invitation(1, 1, 1, 2, 0, 0);
        Invitation newInvitation = new Invitation(1, 1, 1, 2, 0, 1);
        ArrayList<Invitation> invitations = new ArrayList<>();
        invitations.add(oldInvitation);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(userRepository.findUserById(1)).thenReturn(user);
        when(groupMemberRepository.save(any())).thenReturn(groupMember);
        when(invitationRepository.findInvitationById(1)).thenReturn(oldInvitation);
        when(invitationRepository.save(any())).thenReturn(newInvitation);
        when(invitationRepository.findInvitationsByUserIdAndHasRead(1, 0)).thenReturn(invitations);

        ResponseVO responseVO = groupBL.joinGroup(invitation);
        Assert.assertEquals(responseVO.getMessage(), "Setting state of invitation failed.");
    }

    @Test
    public void searchGroupByUserTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Group group1 = new Group(1, "group1");
        Group group2 = new Group(2, "group2");
        Group group3 = new Group(5, "group5");
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        GroupMember groupMember1 = new GroupMember(1, 1, 1);
        groupMember1.setId(1);
        GroupMember groupMember2 = new GroupMember(2, 1, 0);
        groupMember2.setId(2);
        GroupMember groupMember3 = new GroupMember(5, 1, 0);
        groupMember3.setId(3);
        groupMembers.add(groupMember1);
        groupMembers.add(groupMember2);
        groupMembers.add(groupMember3);

        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(groupMembers);
        when(groupRepository.findGroupById(1)).thenReturn(group1);
        when(groupRepository.findGroupById(2)).thenReturn(group2);
        when(groupRepository.findGroupById(5)).thenReturn(group3);

        ResponseVO responseVO = groupBL.searchGroupByUser(1);
        Assert.assertEquals(((ArrayList<Group>) responseVO.getContent()).size(), 3);
        Assert.assertEquals(((ArrayList<Group>) responseVO.getContent()).get(1).getName(), "group2");
    }

    @Test
    public void searchGroupByUserTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.searchGroupByUser(1);
        Assert.assertEquals(responseVO.getMessage(), "Getting group list failed.");
    }

    @Test
    public void searchGroupByUserTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        ArrayList<GroupMember> groupMembers = new ArrayList<>();

        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(groupMembers);

        ResponseVO responseVO = groupBL.searchGroupByUser(1);
        Assert.assertEquals(((ArrayList<Group>) responseVO.getContent()).size(), 0);
    }

    @Test
    public void searchGroupByUserTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Group group1 = new Group(1, "group1");
        Group group2 = new Group(2, "group2");
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        GroupMember groupMember1 = new GroupMember(1, 1, 1);
        groupMember1.setId(1);
        GroupMember groupMember2 = new GroupMember(2, 1, 0);
        groupMember2.setId(2);
        GroupMember groupMember3 = new GroupMember(5, 1, 0);
        groupMember3.setId(3);
        groupMembers.add(groupMember1);
        groupMembers.add(groupMember2);
        groupMembers.add(groupMember3);

        when(groupMemberRepository.findGroupMembersByUserId(1)).thenReturn(groupMembers);
        when(groupRepository.findGroupById(1)).thenReturn(group1);
        when(groupRepository.findGroupById(2)).thenReturn(group2);
        when(groupRepository.findGroupById(5)).thenReturn(null);

        ResponseVO responseVO = groupBL.searchGroupByUser(1);
        Assert.assertEquals(responseVO.getMessage(), "Getting group list failed.");
    }

    @Test
    public void getGroupMembersTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Group group = new Group(1, "testGroup");
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        GroupMember groupMember1 = new GroupMember(1, 1, 1);
        groupMember1.setId(1);
        GroupMember groupMember2 = new GroupMember(1, 2, 0);
        groupMember2.setId(2);
        GroupMember groupMember3 = new GroupMember(1, 5, 0);
        groupMember3.setId(5);
        groupMembers.add(groupMember1);
        groupMembers.add(groupMember2);
        groupMembers.add(groupMember3);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);

        ResponseVO responseVO = groupBL.getGroupMembers(1);
        Assert.assertEquals(((ArrayList<GroupMember>) responseVO.getContent()).size(), 3);
        Assert.assertEquals(((ArrayList<GroupMember>) responseVO.getContent()).get(2).getUserId(), 5);
    }

    @Test
    public void getGroupMembersTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setGroupRepository(groupRepository);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.getGroupMembers(1);
        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
    }

    @Test
    public void getGroupMembersTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.getGroupMembers(1);
        Assert.assertEquals(responseVO.getMessage(), "Getting member list failed.");
    }

    @Test
    public void getGroupMembersTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Group group = new Group(1, "testGroup");
        ArrayList<GroupMember> groupMembers = new ArrayList<>();

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);

        ResponseVO responseVO = groupBL.getGroupMembers(1);
        Assert.assertEquals(responseVO.getMessage(), "group does not exist.");
    }

    @Test
    public void getGroupAnnouncementsTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        AnnouncementReadRepository announcementReadRepository = mock(AnnouncementReadRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setAnnouncementRepository(announcementRepository);
        groupBL.setAnnouncementReadRepository(announcementReadRepository);

        Group group = new Group(1, "testGroup");
        GroupMember groupMember = new GroupMember(1, 1, 1);
        ArrayList<Announcement> announcements = new ArrayList<>();
        Announcement announcement = new Announcement();
        announcement.setId(1);
        announcement.setTitle("testTitle");
        announcement.setContent("testContent");
        announcement.setGroupId(1);
        Date date = new Date();
        announcement.setReleaseDate(date);
        announcements.add(announcement);
        AnnouncementRead announcementRead = new AnnouncementRead(1, 1, 0);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(announcementRepository.findAnnouncementsByGroupId(1)).thenReturn(announcements);
        when(announcementReadRepository.findAnnouncementReadByUserIdAndAnnouncementId(1, 1)).thenReturn(announcementRead);

        ResponseVO responseVO = groupBL.getGroupAnnouncements(1, 1);
        ArrayList<AnnouncementAndUserReadForm> announcementAndUserReadForms = (ArrayList<AnnouncementAndUserReadForm>) responseVO.getContent();
        Assert.assertEquals(announcementAndUserReadForms.size(), 1);
        Assert.assertEquals(announcementAndUserReadForms.get(0).getAnnouncement().getContent(), "testContent");
    }

    @Test
    public void getGroupAnnouncementsTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        groupBL.setGroupRepository(groupRepository);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.getGroupAnnouncements(1, 1);
        Assert.assertEquals(responseVO.getMessage(), "this group does not exist!");
    }

    @Test
    public void getGroupAnnouncementsTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(null);

        ResponseVO responseVO = groupBL.getGroupAnnouncements(1, 1);
        Assert.assertEquals(responseVO.getMessage(), "Do not have the access of getting announcements.");
    }

    @Test
    public void getGroupAnnouncementsTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        AnnouncementReadRepository announcementReadRepository = mock(AnnouncementReadRepository.class);
        groupBL.setGroupRepository(groupRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setAnnouncementRepository(announcementRepository);
        groupBL.setAnnouncementReadRepository(announcementReadRepository);

        Group group = new Group(1, "testGroup");
        GroupMember groupMember = new GroupMember(1, 1, 1);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(announcementRepository.findAnnouncementsByGroupId(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.getGroupAnnouncements(1, 1);
        Assert.assertEquals(responseVO.getMessage(), "Getting announcement list failed.");
    }

    @Test
    public void releaseAnnouncementTest() {
        GroupBLImpl groupBL = new GroupBLImpl();
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        AnnouncementReadRepository announcementReadRepository = mock(AnnouncementReadRepository.class);
        AnnouncementServer announcementServer = mock(AnnouncementServer.class);
        groupBL.setAnnouncementRepository(announcementRepository);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setAnnouncementReadRepository(announcementReadRepository);
        groupBL.setAnnouncementServer(announcementServer);

        Announcement announcement = new Announcement();
        announcement.setId(1);
        announcement.setTitle("testTitle");
        announcement.setContent("testContent");
        announcement.setGroupId(1);
        announcement.setReleaseDate(new Date(120, 7, 1));
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        GroupMember groupMember1 = new GroupMember(1, 1, 1);
        GroupMember groupMember2 = new GroupMember(1, 2, 0);
        groupMembers.add(groupMember1);
        groupMembers.add(groupMember2);
        ArrayList<AnnouncementRead> announcementReads = new ArrayList<>();
        AnnouncementRead announcementRead1 = new AnnouncementRead(1, 1, 0);
        AnnouncementRead announcementRead2 = new AnnouncementRead(2, 1, 0);
        announcementReads.add(announcementRead1);
        announcementReads.add(announcementRead2);

        when(announcementRepository.save(any())).thenReturn(announcement);
        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);
        when(announcementReadRepository.saveAll(any())).thenReturn(announcementReads);
        doNothing().when(announcementServer).sendInfo(1, 2);
        doNothing().when(announcementServer).sendInfo(2, 2);

        ResponseVO responseVO = groupBL.releaseAnnouncement(announcement);
        Assert.assertEquals(((Announcement) responseVO.getContent()).getTitle(), "testTitle");
    }

    @Test
    public void readAnnouncementTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        AnnouncementReadRepository announcementReadRepository = mock(AnnouncementReadRepository.class);
        AnnouncementServer announcementServer = mock(AnnouncementServer.class);
        groupBL.setAnnouncementReadRepository(announcementReadRepository);
        groupBL.setAnnouncementServer(announcementServer);

        AnnouncementRead announcementRead = new AnnouncementRead(1, 1, 0);
        AnnouncementRead res = new AnnouncementRead(1, 1, 1);
        ArrayList<AnnouncementRead> announcementReads = new ArrayList<>();
        announcementReads.add(announcementRead);

        when(announcementReadRepository.findAnnouncementReadByUserIdAndAnnouncementId(1, 1)).thenReturn(announcementRead);
        when(announcementReadRepository.save(any())).thenReturn(res);
        when(announcementReadRepository.findAnnouncementReadsByUserIdAndHasRead(1, 0)).thenReturn(announcementReads);
        doNothing().when(announcementServer).sendInfo(1, 1);

        ResponseVO responseVO = groupBL.readAnnouncement(1, 1);
        Assert.assertEquals(((AnnouncementRead) responseVO.getContent()).getAnnouncementId(), 1);
        Assert.assertEquals(((AnnouncementRead) responseVO.getContent()).getHasRead(), 1);
    }

    @Test
    public void readAnnouncementTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        AnnouncementReadRepository announcementReadRepository = mock(AnnouncementReadRepository.class);
        groupBL.setAnnouncementReadRepository(announcementReadRepository);

        when(announcementReadRepository.findAnnouncementReadByUserIdAndAnnouncementId(1, 1)).thenReturn(null);

        ResponseVO responseVO = groupBL.readAnnouncement(1, 1);
        Assert.assertEquals(responseVO.getMessage(), "Getting has_read failed.");
    }

    @Test
    public void readAnnouncementTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        AnnouncementReadRepository announcementReadRepository = mock(AnnouncementReadRepository.class);
        groupBL.setAnnouncementReadRepository(announcementReadRepository);

        AnnouncementRead announcementRead = new AnnouncementRead(1, 1, 0);
        AnnouncementRead announcementRead2 = new AnnouncementRead(1, 1, 0);

        when(announcementReadRepository.findAnnouncementReadByUserIdAndAnnouncementId(1, 1)).thenReturn(announcementRead);
        when(announcementReadRepository.save(any())).thenReturn(announcementRead2);

        ResponseVO responseVO = groupBL.readAnnouncement(1, 1);
        Assert.assertEquals(responseVO.getMessage(), "Modifying has_read failed.");
    }

    @Test
    public void readInvitationTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        InvitationServer invitationServer = mock(InvitationServer.class);
        groupBL.setInvitationRepository(invitationRepository);
        groupBL.setInvitationServer(invitationServer);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);
        Invitation res = new Invitation(1, 1, 2, 1, 1, 0);
        Invitation oldInvitation = new Invitation(2, 3, 2, 3, 0, 0);
        ArrayList<Invitation> invitations = new ArrayList<>();
        invitations.add(oldInvitation);

        when(invitationRepository.findInvitationById(1)).thenReturn(invitation);
        when(invitationRepository.save(any())).thenReturn(res);
        when(invitationRepository.findInvitationsByUserIdAndHasRead(2, 0)).thenReturn(invitations);
        doNothing().when(invitationServer).sendInfo(2, 1);

        ResponseVO responseVO = groupBL.readInvitation(2, 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getInviterId(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getHasRead(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getGroupId(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getUserId(), 2);
    }

    @Test
    public void readInvitationTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setInvitationRepository(invitationRepository);

        when(invitationRepository.findInvitationById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.readInvitation(2, 1);
        Assert.assertEquals(responseVO.getMessage(), "Finding invitation failed.");
    }

    @Test
    public void readInvitationTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);

        when(invitationRepository.findInvitationById(1)).thenReturn(invitation);

        ResponseVO responseVO = groupBL.readInvitation(6, 1);
        Assert.assertEquals(responseVO.getMessage(), "Do not have the access of reading invitation.");
    }

    @Test
    public void readInvitationTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);
        Invitation res = new Invitation(1, 1, 2, 1, 0, 0);

        when(invitationRepository.findInvitationById(1)).thenReturn(invitation);
        when(invitationRepository.save(any())).thenReturn(res);

        ResponseVO responseVO = groupBL.readInvitation(2, 1);
        Assert.assertEquals(responseVO.getMessage(), "Setting the has-reading of invitation failed.");
    }

    @Test
    public void ignoreInvitationTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        InvitationServer invitationServer = mock(InvitationServer.class);
        groupBL.setInvitationRepository(invitationRepository);
        groupBL.setInvitationServer(invitationServer);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);
        Invitation res = new Invitation(1, 1, 2, 1, 1, 2);
        Invitation oldInvitation = new Invitation(2, 3, 2, 3, 0, 0);
        ArrayList<Invitation> invitations = new ArrayList<>();
        invitations.add(oldInvitation);

        when(invitationRepository.findInvitationById(1)).thenReturn(invitation);
        when(invitationRepository.save(any())).thenReturn(res);
        when(invitationRepository.findInvitationsByUserIdAndHasRead(2, 0)).thenReturn(invitations);
        doNothing().when(invitationServer).sendInfo(2, 1);

        ResponseVO responseVO = groupBL.ignoreInvitation(2, 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getInviterId(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getHasRead(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getGroupId(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getUserId(), 2);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getState(), 2);
    }

    @Test
    public void ignoreInvitationTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setInvitationRepository(invitationRepository);

        when(invitationRepository.findInvitationById(1)).thenReturn(null);

        ResponseVO responseVO = groupBL.ignoreInvitation(2, 1);
        Assert.assertEquals(responseVO.getMessage(), "Finding invitation failed.");
    }

    @Test
    public void ignoreInvitationTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);

        when(invitationRepository.findInvitationById(1)).thenReturn(invitation);

        ResponseVO responseVO = groupBL.ignoreInvitation(6, 1);
        Assert.assertEquals(responseVO.getMessage(), "Do not have the access of ignoring invitation.");
    }

    @Test
    public void ignoreInvitationTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);
        Invitation res = new Invitation(1, 1, 2, 1, 1, 1);

        when(invitationRepository.findInvitationById(1)).thenReturn(invitation);
        when(invitationRepository.save(any())).thenReturn(res);

        ResponseVO responseVO = groupBL.ignoreInvitation(2, 1);
        Assert.assertEquals(responseVO.getMessage(), "Setting the state of invitation failed.");
    }

    @Test
    public void inviteUserTest1() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        InvitationServer invitationServer = mock(InvitationServer.class);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setInvitationRepository(invitationRepository);
        groupBL.setInvitationServer(invitationServer);

        Invitation invitation = new Invitation(0, 1, 2, 1, 0, 0);
        Invitation res = new Invitation(1, 1, 2, 1, 0, 0);
        GroupMember groupMember = new GroupMember(1, 2, 0);
        ArrayList<Invitation> invitations = new ArrayList<>();

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(invitationRepository.save(any())).thenReturn(res);
        when(invitationRepository.findInvitationsByUserIdAndHasRead(2, 0)).thenReturn(invitations);
        doNothing().when(invitationServer).sendInfo(2, 0);

        ResponseVO responseVO = groupBL.inviteUser(invitation);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getId(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getGroupId(), 1);
        Assert.assertEquals(((Invitation) responseVO.getContent()).getUserId(), 2);
    }

    @Test
    public void inviteUserTest2() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Invitation invitation = new Invitation(0, 1, 2, 3, 0, 0);
        GroupMember groupMember = new GroupMember(1, 2, 0);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);

        ResponseVO responseVO = groupBL.inviteUser(invitation);
        Assert.assertEquals(responseVO.getMessage(), "Do not have the access of inviting user.");
    }

    @Test
    public void inviteUserTest3() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        groupBL.setGroupMemberRepository(groupMemberRepository);

        Invitation invitation = new Invitation(1, 1, 2, 1, 0, 0);
        GroupMember groupMember = new GroupMember(1, 2, 0);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);

        ResponseVO responseVO = groupBL.inviteUser(invitation);
        Assert.assertEquals(responseVO.getMessage(), "The invitation has to be a new one.");
    }

    @Test
    public void inviteUserTest4() {
        GroupBLImpl groupBL = new GroupBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        InvitationRepository invitationRepository = mock(InvitationRepository.class);
        groupBL.setGroupMemberRepository(groupMemberRepository);
        groupBL.setInvitationRepository(invitationRepository);

        Invitation invitation = new Invitation(0, 1, 2, 1, 0, 0);
        Invitation res = new Invitation(0, 1, 2, 1, 0, 0);
        GroupMember groupMember = new GroupMember(1, 2, 0);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(invitationRepository.save(any())).thenReturn(res);

        ResponseVO responseVO = groupBL.inviteUser(invitation);
        Assert.assertEquals(responseVO.getMessage(), "Saving invitation failed.");
    }

}