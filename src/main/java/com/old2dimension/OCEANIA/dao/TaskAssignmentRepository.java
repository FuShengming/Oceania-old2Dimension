package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment,Integer> {
    List<TaskAssignment> findTaskAssignmentsByGroupIdAndUserId(int groupId,int userId);
    List<TaskAssignment> findTaskAssignmentsByGroupId(int groupId);
}
