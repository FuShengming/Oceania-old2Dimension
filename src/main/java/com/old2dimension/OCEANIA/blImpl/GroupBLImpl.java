package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GroupBL;
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
import java.util.List;

@Component
public class GroupBLImpl implements GroupBL {


    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;

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
        List<Integer> memberIds = new ArrayList<Integer>();
        GroupMember leader;
        leader = new GroupMember(0,groupNameAndCreatorIdForm.getCreatorId(),1);
        memberIds.add(groupNameAndCreatorIdForm.getCreatorId());

        Group res = new Group(0, groupNameAndCreatorIdForm.getName(),memberIds, groupNameAndCreatorIdForm.getCreatorId());
        res = groupRepository.save(res);

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
        List<GroupMember> groupMembers = groupMemberRepository.getAllGroupMemberByGroupIdAndIsLeader(groupIdAndLeaderForm.getGroupId(),1);
        if(groupMembers.size()!=1){
            return ResponseVO.buildFailure("this group has no leader!");
        }
        GroupMember leader = groupMembers.get(0);
        leader.setIsLeader(0);
        List<GroupMember> members = new ArrayList<>();
        GroupMember newLeader = groupMemberRepository.getGroupMemberByGroupIdAndUserId
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
    public ResponseVO inviteUser(String name) {
        return null;
    }

    @Override
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm) {
        GroupMember groupMember = groupMemberRepository.getGroupMemberByGroupIdAndUserId
                (groupIdAndUserForm.getGroupId(),groupIdAndUserForm.getUserId());
        if(groupMember==null){
            return ResponseVO.buildFailure("The group do not have this user.");
        }
        if(groupMember.getIsLeader()==1){
            return ResponseVO.buildFailure("leader must transfer the possession of leader before quiting group");
        }
        //------------------------------------------------------

        groupMemberRepository.delete(groupMember);
        int cnt = groupMemberRepository.countByGroupId(groupIdAndUserForm.getGroupId());
        if(cnt==0){
            groupRepository.deleteById(groupIdAndUserForm.getGroupId());
        }
        return ResponseVO.buildSuccess("you has quited the group");



    }

    @Override
    public ResponseVO joinGroup(GroupIdAndUserForm groupIdAndUserForm) {
        return null;
    }

    @Override
    public ResponseVO searchGroupByUser(String userId) {
        return null;
    }

    @Override
    public ResponseVO getGroupMembers(String groupId) {
        return null;
    }

    @Override
    public ResponseVO releaseAnnouncement(Announcement announcement) {
        return null;
    }

    @Override
    public ResponseVO getGroupAnnouncements(String groupId) {
        return null;
    }


}
