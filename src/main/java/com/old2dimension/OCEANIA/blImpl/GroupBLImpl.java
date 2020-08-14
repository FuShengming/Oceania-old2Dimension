package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.MessageServer.AnnouncementServer;
import com.old2dimension.OCEANIA.MessageServer.InvitationServer;
import com.old2dimension.OCEANIA.bl.GroupBL;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
public class GroupBLImpl implements GroupBL {


    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    AnnouncementReadRepository announcementReadRepository;
    @Autowired
    AnnouncementServer announcementServer;
    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    InvitationServer invitationServer;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void setGroupMemberRepository(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    public void setAnnouncementRepository(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public void setAnnouncementReadRepository(AnnouncementReadRepository announcementReadRepository) {
        this.announcementReadRepository = announcementReadRepository;
    }

    public void setAnnouncementServer(AnnouncementServer announcementServer) {
        this.announcementServer = announcementServer;
    }

    public void setInvitationRepository(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public void setInvitationServer(InvitationServer invitationServer) {
        this.invitationServer = invitationServer;
    }

    @Override
    public ResponseVO findUser(String name) {
        User user = userRepository.findUserByName(name);
        if (user == null) {
            return ResponseVO.buildFailure("do not find this user");
        }
        return ResponseVO.buildSuccess(user);

    }

    @Override
    public ResponseVO createGroup(CreateGroupForm createGroupForm) {

        //------------------检查userId是否存在-----------------
        if (userRepository.findUserById(createGroupForm.getCreatorId()) == null) {
            return ResponseVO.buildFailure("This user does not exist.");
        }


        List<Integer> memberIds = new ArrayList<Integer>();

        memberIds.add(createGroupForm.getCreatorId());

        Group res = new Group(0, createGroupForm.getName(), createGroupForm.getDescription());
        res = groupRepository.save(res);
        GroupMember leader;
        leader = new GroupMember(res.getId(), createGroupForm.getCreatorId(), 1);
        if (res.getId() == 0) {
            return ResponseVO.buildFailure("creating group failed");
        }
        leader = groupMemberRepository.save(leader);
        if (leader.getId() == 0) {
            groupRepository.delete(res);
            return ResponseVO.buildFailure("creating group failed");
        }

        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm) {
        if (groupRepository.findGroupById(groupIdAndLeaderForm.getGroupId()) == null) {
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if (userRepository.findUserById(groupIdAndLeaderForm.getUserId()) == null) {
            return ResponseVO.buildFailure("This user does not exist.");
        }
        List<GroupMember> groupMembers = groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(groupIdAndLeaderForm.getGroupId(), 1);
        if (groupMembers.size() != 1) {
            return ResponseVO.buildFailure("this group has no leader!");
        }

        GroupMember leader = groupMembers.get(0);
        leader.setIsLeader(0);
        List<GroupMember> members = new ArrayList<>();
        GroupMember newLeader = groupMemberRepository.findGroupMemberByGroupIdAndUserId
                (groupIdAndLeaderForm.getGroupId(), groupIdAndLeaderForm.getUserId());
        if (newLeader == null) {
            return ResponseVO.buildFailure("This user is not in the group.");
        }
        newLeader.setIsLeader(1);
        members.add(leader);
        members.add(newLeader);
        members = groupMemberRepository.saveAll(members);

        if (members.size() == 0) {
            return ResponseVO.buildFailure("Modifying group leader failed.");
        }

        return ResponseVO.buildSuccess("Modifying leader succeed.");
    }

    @Override
    public ResponseVO inviteUser(Invitation invitation) {
        invitation.setHasRead(0);
        invitation.setState(0);

        if (groupMemberRepository.findGroupMemberByGroupIdAndUserId(invitation.getGroupId(), invitation.getInviterId()) == null) {
            return ResponseVO.buildFailure("Do not have the access of inviting user.");
        }

        if (invitation.getId() != 0) {
            return ResponseVO.buildFailure("The invitation has to be a new one.");
        }
        if (groupMemberRepository.findGroupMemberByGroupIdAndUserId(invitation.getGroupId(), invitation.getUserId()) != null) {
            return ResponseVO.buildFailure("This user have been group member.");
        }
        invitation = invitationRepository.save(invitation);
        if (invitation.getId() == 0) {
            return ResponseVO.buildFailure("Saving invitation failed.");
        }
        List<Invitation> temp = new ArrayList<>();
        temp.add(invitation);
        invitationServer.sendInfo(invitation.getUserId(), invitationRepository.countInvitationsByUserIdAndHasRead(invitation.getUserId(), 0));

        return ResponseVO.buildSuccess(invitation);
    }

    @Override
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm) {
        if (groupRepository.findGroupById(groupIdAndUserForm.getGroupId()) == null) {
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if (userRepository.findUserById(groupIdAndUserForm.getUserId()) == null) {
            return ResponseVO.buildFailure("This user does not exist.");
        }

        GroupMember groupMember = groupMemberRepository.findGroupMemberByGroupIdAndUserId
                (groupIdAndUserForm.getGroupId(), groupIdAndUserForm.getUserId());
        if (groupMember == null) {
            return ResponseVO.buildFailure("The group do not have this user.");
        }
        int cnt = groupMemberRepository.countByGroupId(groupIdAndUserForm.getGroupId());
        if (groupMember.getIsLeader() == 1 && cnt != 1) {
            return ResponseVO.buildFailure("leader must transfer the possession of leader before quiting group");
        }


        groupMemberRepository.delete(groupMember);
        cnt = groupMemberRepository.countByGroupId(groupIdAndUserForm.getGroupId());
        if (cnt == 0) {
            groupRepository.deleteById(groupIdAndUserForm.getGroupId());
        }
        return ResponseVO.buildSuccess("you has quited the group");


    }


    @Override
    public ResponseVO joinGroup(Invitation invitation) {


        if (groupRepository.findGroupById(invitation.getGroupId()) == null) {
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if (userRepository.findUserById(invitation.getUserId()) == null) {
            return ResponseVO.buildFailure("This user does not exist.");
        }


        GroupMember member = new GroupMember(invitation.getGroupId(), invitation.getUserId(), 0);
        member = groupMemberRepository.save(member);
        if (member.getId() == 0) {
            return ResponseVO.buildFailure("Joining group failed");
        }

        Invitation oldInvitation = invitationRepository.findInvitationById(invitation.getId());

        if (oldInvitation == null) {
            return ResponseVO.buildFailure("Finding invitation failed.");
        }
        if (invitation.getUserId() != oldInvitation.getUserId()) {
            return ResponseVO.buildFailure("Do not have the access of reading invitation.");
        }
        oldInvitation.setHasRead(1);
        oldInvitation.setState(1);
        oldInvitation = invitationRepository.save(oldInvitation);
        if (oldInvitation.getHasRead() != 1 || oldInvitation.getState() != 1) {
            return ResponseVO.buildFailure("Setting state of invitation failed.");
        }
        invitationServer.sendInfo(invitation.getUserId(), invitationRepository.countInvitationsByUserIdAndHasRead(invitation.getUserId(), 0));
        return ResponseVO.buildSuccess(member);
    }

    @Override
    public ResponseVO getUserInvitation(int userId) {
        List<Invitation> res = invitationRepository.findInvitationsByUserId(userId);
        if (res == null) {
            return ResponseVO.buildFailure("Getting invitations failed.");
        }
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO searchGroupByUser(int userId) {
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMembersByUserId(userId);
        List<Group> res = new ArrayList<>();
        if (groupMembers == null) {
            return ResponseVO.buildFailure("Getting group list failed.");
        }
        if (groupMembers.size() == 0) {
            res = new ArrayList<>();
            return ResponseVO.buildSuccess(res);
        }

        HashSet<Integer> groupIdSet = new HashSet<>();
        for (GroupMember cur : groupMembers) {
            groupIdSet.add(cur.getGroupId());
        }

        ArrayList<Integer> groupIds = new ArrayList<>(groupIdSet);
        for (int i : groupIds) {
            Group cur = groupRepository.findGroupById(i);
            if (cur == null) {
                return ResponseVO.buildFailure("Getting group list failed.");
            }
            res.add(cur);
        }

        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO getGroupMembers(int groupId) {
        if (groupRepository.findGroupById(groupId) == null) {
            return ResponseVO.buildFailure("this group does not exist!");
        }


        List<GroupMember> members = groupMemberRepository.findGroupMembersByGroupId(groupId);
        if (members == null) {
            return ResponseVO.buildFailure("Getting member list failed.");
        }
        if (members.size() == 0) {
            return ResponseVO.buildFailure("group does not exist.");
        }
        return ResponseVO.buildSuccess(members);

    }

    @Override
    public ResponseVO getGroupMemberNames(int groupId) {
        if(groupRepository.findGroupById(groupId)==null){
            return ResponseVO.buildFailure("this group does not exist!");
        }

        List<User> members = userRepository.findUserByGroupId(groupId);
        if(members==null){
            return ResponseVO.buildFailure("Getting member list failed.");
        }
        for (int i = 0; i < members.size(); i++) {
            members.get(i).setPwd(null);
        }
        return ResponseVO.buildSuccess(members);
    }

    @Override
    public ResponseVO releaseAnnouncement(Announcement announcement) {
        announcement = announcementRepository.save(announcement);
        int groupId = announcement.getGroupId();
        int announcementId = announcement.getId();
        List<GroupMember> members = groupMemberRepository.findGroupMembersByGroupId(groupId);
        List<Integer> ids = new ArrayList<>();
        for (GroupMember member : members) {
            ids.add(member.getUserId());
        }
        List<AnnouncementRead> announcementReads = new ArrayList<>();
        for (Integer id : ids) {
            AnnouncementRead cur = new AnnouncementRead(id, announcementId, 0);
            announcementReads.add(cur);
        }
        announcementReadRepository.saveAll(announcementReads);
        List<Announcement> temp = new ArrayList<>();
        temp.add(announcement);

        for (Integer id : ids) {
            announcementServer.sendInfo(id, announcementReadRepository.findAnnouncementReadsByUserIdAndHasRead(id, 0).size());
        }

        return ResponseVO.buildSuccess(announcement);
    }

    @Override
    public ResponseVO getGroupAnnouncements(int groupId, int userId) {
        Group group = groupRepository.findGroupById(groupId);
        if (group == null) {
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if (groupMemberRepository.findGroupMemberByGroupIdAndUserId(groupId, userId) == null) {
            return ResponseVO.buildFailure("Do not have the access of getting announcements.");
        }
        List<Announcement> announcements = announcementRepository.findAnnouncementsByGroupId(groupId);

        if (announcements == null) {
            return ResponseVO.buildFailure("Getting announcement list failed.");
        }

        List<AnnouncementAndUserReadForm> res = new ArrayList<>();
        for (Announcement a : announcements) {
            AnnouncementRead announcementRead = announcementReadRepository.findAnnouncementReadByUserIdAndAnnouncementId(userId, a.getId());
            if (announcementRead == null) {
                announcementRead = new AnnouncementRead();
                announcementRead.setHasRead(1);
            }
            AnnouncementAndUserReadForm cur = new AnnouncementAndUserReadForm(a, userId, announcementRead
                    .getHasRead());
            res.add(cur);
        }

        return ResponseVO.buildSuccess(res);


    }

    @Override
    public ResponseVO readAnnouncement(int userId, int announcementId) {
        AnnouncementRead announcementRead = announcementReadRepository.findAnnouncementReadByUserIdAndAnnouncementId(userId, announcementId);
        if (announcementRead == null) {
            return ResponseVO.buildFailure("Getting has_read failed.");
        }
        announcementRead.setHasRead(1);
        announcementRead = announcementReadRepository.save(announcementRead);
        if (announcementRead.getHasRead() == 0) {
            return ResponseVO.buildFailure("Modifying has_read failed.");
        }

        announcementServer.sendInfo(userId, announcementReadRepository.findAnnouncementReadsByUserIdAndHasRead(userId, 0).size());
        return ResponseVO.buildSuccess(announcementRead);
    }

    @Override
    public ResponseVO updateGroupInfo(GroupAndUserIdForm groupAndUserIdForm) {
        GroupMember groupMember = groupMemberRepository.findGroupMemberByGroupIdAndUserId(groupAndUserIdForm.getGroup().getId(),
                groupAndUserIdForm.getUserId());

        if (groupMember == null || groupMember.getIsLeader() != 1) {
            return ResponseVO.buildFailure("Do not have the access of updating group data.");
        }
        Group group = groupAndUserIdForm.getGroup();
        group = groupRepository.save(group);
        return ResponseVO.buildSuccess(group);

    }

    @Override
    public ResponseVO getGroupName(int groupId) {
        Group group = groupRepository.findGroupById(groupId);
        if (group == null) {
            return ResponseVO.buildFailure("Can not find group.");
        }
        return ResponseVO.buildSuccess(group.getName());
    }

    @Override
    public ResponseVO getUnreadAnnouncements(int userId) {
        List<AnnouncementRead> announcementReads = announcementReadRepository.findAnnouncementReadsByUserIdAndHasRead(userId, 0);
        List<AnnouncementAndGroupName> res = new ArrayList<>();
        for (AnnouncementRead a : announcementReads) {
            Announcement announcement = announcementRepository.findAnnouncementById(a.getAnnouncementId());
            if (announcement == null) {
                return ResponseVO.buildFailure("find announcements failed.");
            }
            Group group = groupRepository.findGroupById(announcement.getGroupId());
            if (group == null) {
                return ResponseVO.buildFailure("find group failed.");
            }
            AnnouncementAndGroupName announcementAndGroupName = new AnnouncementAndGroupName();
            announcementAndGroupName.setAnnouncement(announcement);
            announcementAndGroupName.setGroupName(group.getName());
            res.add(announcementAndGroupName);

        }
        return ResponseVO.buildSuccess(res);
    }


    @Override
    public ResponseVO readInvitation(int userId, int invitationId) {
        Invitation invitation = invitationRepository.findInvitationById(invitationId);

        if (invitation == null) {
            return ResponseVO.buildFailure("Finding invitation failed.");
        }
        if (invitation.getUserId() != userId) {
            return ResponseVO.buildFailure("Do not have the access of reading invitation.");
        }
        invitation.setHasRead(1);
        invitation = invitationRepository.save(invitation);
        if (invitation.getHasRead() != 1) {
            return ResponseVO.buildFailure("Setting the has-reading of invitation failed.");
        }
        invitationServer.sendInfo(userId, invitationRepository.countInvitationsByUserIdAndHasRead(userId, 0));
        return ResponseVO.buildSuccess(invitation);
    }

    @Override
    public ResponseVO ignoreInvitation(int userId, int invitationId) {
        Invitation invitation = invitationRepository.findInvitationById(invitationId);

        if (invitation == null) {
            return ResponseVO.buildFailure("Finding invitation failed.");
        }
        if (invitation.getUserId() != userId) {
            return ResponseVO.buildFailure("Do not have the access of ignoring invitation.");
        }
        invitation.setHasRead(1);
        invitation.setState(2);
        invitation = invitationRepository.save(invitation);
        if (invitation.getState() != 2 || invitation.getHasRead() != 1) {
            return ResponseVO.buildFailure("Setting the state of invitation failed.");
        }
        invitationServer.sendInfo(userId, invitationRepository.countInvitationsByUserIdAndHasRead(userId, 0));
        return ResponseVO.buildSuccess(invitation);
    }

}
