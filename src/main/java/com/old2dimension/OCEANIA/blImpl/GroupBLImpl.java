package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GroupBL;
import com.old2dimension.OCEANIA.dao.AnnouncementRepository;
import com.old2dimension.OCEANIA.dao.GroupMemberRepository;
import com.old2dimension.OCEANIA.dao.GroupRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.po.Group;
import com.old2dimension.OCEANIA.po.GroupMember;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.GroupIdAndUserForm;
import com.old2dimension.OCEANIA.vo.GroupNameAndCreatorIdForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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


    @Override
    public ResponseVO findUser(String name) {
        User user = userRepository.findUserByName(name);
        if(user==null){
            return ResponseVO.buildFailure("do not find this user");
        }
        return ResponseVO.buildSuccess(user);
    }

    @Override
    public ResponseVO createGroup(GroupNameAndCreatorIdForm groupNameAndCreatorIdForm) {

        //------------------检查userId是否存在-----------------
        if(userRepository.findUserById(groupNameAndCreatorIdForm.getCreatorId())==null){
            return ResponseVO.buildFailure("This user does not exist.");
        }


        List<Integer> memberIds = new ArrayList<Integer>();

        memberIds.add(groupNameAndCreatorIdForm.getCreatorId());

        Group res = new Group(0, groupNameAndCreatorIdForm.getName());
        res = groupRepository.save(res);
        GroupMember leader;
        leader = new GroupMember(res.getId(),groupNameAndCreatorIdForm.getCreatorId(),1);
        if(res.getId()==0){
            return ResponseVO.buildFailure("creating group failed");
        }
        leader = groupMemberRepository.save(leader);
        if(leader.getId()==0){
            groupRepository.delete(res);
            return ResponseVO.buildFailure("creating group failed");
        }

        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm) {
        if(groupRepository.findGroupById(groupIdAndLeaderForm.getGroupId())==null){
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if(userRepository.findUserById(groupIdAndLeaderForm.getUserId())==null){
            return ResponseVO.buildFailure("This user does not exist.");
        }
        List<GroupMember> groupMembers = groupMemberRepository.findAllGroupMemberByGroupIdAndIsLeader(groupIdAndLeaderForm.getGroupId(),1);
        if(groupMembers.size()!=1){
            return ResponseVO.buildFailure("this group has no leader!");
        }

        GroupMember leader = groupMembers.get(0);
        leader.setIsLeader(0);
        List<GroupMember> members = new ArrayList<>();
        GroupMember newLeader = groupMemberRepository.findGroupMemberByGroupIdAndUserId
                (groupIdAndLeaderForm.getGroupId(),groupIdAndLeaderForm.getUserId());
        if(newLeader==null){
            return ResponseVO.buildFailure("This user is not in the group.");
        }
        newLeader.setIsLeader(1);
        members.add(leader);
        members.add(newLeader);
        members = groupMemberRepository.saveAll(members);
        if(members.size()==0){
            return ResponseVO.buildFailure("Modifying group leader failed.");
        }

        return ResponseVO.buildSuccess("Modifying leader succeed.");
    }

    @Override
    public ResponseVO inviteUser(int userId) {
        return null;
    }

    @Override
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm) {
        if(groupRepository.findGroupById(groupIdAndUserForm.getGroupId())==null){
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if(userRepository.findUserById(groupIdAndUserForm.getUserId())==null){
            return ResponseVO.buildFailure("This user does not exist.");
        }

        GroupMember groupMember = groupMemberRepository.findGroupMemberByGroupIdAndUserId
                (groupIdAndUserForm.getGroupId(),groupIdAndUserForm.getUserId());
        if(groupMember==null){
            return ResponseVO.buildFailure("The group do not have this user.");
        }
        if(groupMember.getIsLeader()==1){
            return ResponseVO.buildFailure("leader must transfer the possession of leader before quiting group");
        }


        groupMemberRepository.delete(groupMember);
        int cnt = groupMemberRepository.countByGroupId(groupIdAndUserForm.getGroupId());
        if(cnt==0){
            groupRepository.deleteById(groupIdAndUserForm.getGroupId());
        }
        return ResponseVO.buildSuccess("you has quited the group");



    }

    @Override
    public ResponseVO joinGroup(GroupIdAndUserForm groupIdAndUserForm) {
        if(groupRepository.findGroupById(groupIdAndUserForm.getGroupId())==null){
            return ResponseVO.buildFailure("this group does not exist!");
        }
        if(userRepository.findUserById(groupIdAndUserForm.getUserId())==null){
            return ResponseVO.buildFailure("This user does not exist.");
        }


        GroupMember member = new GroupMember(groupIdAndUserForm.getGroupId(),groupIdAndUserForm.getUserId(),0);
        member = groupMemberRepository.save(member);
        if(member.getId()==0){
            return ResponseVO.buildFailure("Joining group failed");
        }
        return ResponseVO.buildSuccess(member);
    }

    @Override
    public ResponseVO searchGroupByUser(int userId) {


        List<GroupMember> groupMembers = groupMemberRepository.findGroupMembersByUserId(userId);
        List<Group> res = new ArrayList<>();
        if(groupMembers==null){
            return ResponseVO.buildFailure("Getting group list failed.");
        }
        if(groupMembers.size()==0){
            res = new ArrayList<>();
            return ResponseVO.buildSuccess(res);
        }

        HashSet<Integer> groupIdSet = new HashSet<>();
        for(GroupMember cur:groupMembers){
            groupIdSet.add(cur.getGroupId());
        }

        ArrayList<Integer>  groupIds = new ArrayList<>(groupIdSet);
        for(int i : groupIds){
            Group cur =  groupRepository.findGroupById(i);
            if(cur==null){
                return ResponseVO.buildFailure("Getting group list failed.");
            }
            res.add(cur);
        }

        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO getGroupMembers(int groupId) {
        if(groupRepository.findGroupById(groupId)==null){
            return ResponseVO.buildFailure("this group does not exist!");
        }


        List<GroupMember> members = groupMemberRepository.findGroupMembersByGroupId(groupId);
        if(members==null){
            return ResponseVO.buildFailure("Getting member list failed.");
        }
        if(members.size()==0){
            return ResponseVO.buildFailure("group does not exist.");
        }
        return ResponseVO.buildSuccess(members);

    }

    @Override
    public ResponseVO releaseAnnouncement(Announcement announcement) {
        return null;
    }

    @Override
    public ResponseVO getGroupAnnouncements(int groupId) {
        if(groupRepository.findGroupById(groupId)==null){
            return ResponseVO.buildFailure("this group does not exist!");
        }

        List<Announcement> res = announcementRepository.findAllByGroupId(groupId);
        if(res==null){
            return ResponseVO.buildFailure("Getting announcement list failed.");
        }

        return ResponseVO.buildSuccess(res);


    }


}
