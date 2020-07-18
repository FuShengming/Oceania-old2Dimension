package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember,Integer> {
    List<GroupMember> getAllGroupMemberByGroupIdAndIsLeader(int groupId, int isLeader);
    GroupMember getGroupMemberByGroupIdAndUserId(int groupId,int userId);
    int countByGroupId(int groupId);
}
