package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findTasksByGroupId(int groupId);
    List<Task> findTasksByName(String name);
    void deleteById(int id);
    Task findTaskByIdAndGroupId(int id, int groupId);
    Task findTaskById(int id);
}
