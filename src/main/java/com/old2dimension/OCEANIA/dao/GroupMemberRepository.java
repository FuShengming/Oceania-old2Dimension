package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Authority;
import com.old2dimension.OCEANIA.po.Group;
import com.old2dimension.OCEANIA.po.GroupMember;
import com.old2dimension.OCEANIA.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember,Integer> {
    List<GroupMember> findAllGroupMemberByGroupIdAndIsLeader(int groupId, int isLeader);
    GroupMember findGroupMemberByGroupIdAndUserId(int groupId, int userId);
    int countByGroupId(int groupId);
    List<GroupMember> findGroupMembersByUserId(int userId);
    List<GroupMember> findGroupMembersByGroupId(int groupId);
}
