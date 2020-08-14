package com.old2dimension.OCEANIA.blImpl;
import com.old2dimension.OCEANIA.bl.TaskBL;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.Task;
import com.old2dimension.OCEANIA.po.TaskAssignment;
import com.old2dimension.OCEANIA.vo.GroupIdAndTaskNameForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TaskBLImpl implements TaskBL {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    TaskAssignmentRepository taskAssignmentRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void setTaskAssignmentRepository(TaskAssignmentRepository taskAssignmentRepository) {
        this.taskAssignmentRepository = taskAssignmentRepository;
    }

    public void setGroupMemberRepository(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    @Override
    public ResponseVO createTask(Task task) {
        int id = task.getId();
        int groupId = task.getGroupId();
        if(groupRepository.findGroupById(groupId)==null){
            return ResponseVO.buildFailure("This group does not exist!");
        }
        if(id!=0){
            return ResponseVO.buildFailure("This task existed!");
        }
        task = taskRepository.save(task);
        if(task.getId()==0){
            return ResponseVO.buildFailure("Creating task failed");
        }


        return ResponseVO.buildSuccess(task);
    }

    @Override
    public ResponseVO getAllTask(int groupId) {
        if(groupRepository.findGroupById(groupId)==null){
            return ResponseVO.buildFailure("This group does not exist!");
        }
        HashMap<String,List> map = new HashMap<>();
        List<Task> res = taskRepository.findTasksByGroupId(groupId);
        map.put("tasks",res);

        if(res==null){
            return ResponseVO.buildFailure("Getting task list failed");
        }

        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findTaskAssignmentsByGroupId(groupId);
        if(taskAssignments==null){
            return ResponseVO.buildFailure("Getting task assignments failed.");
        }
        map.put("assignments",taskAssignments);

        return ResponseVO.buildSuccess(map);
    }

    @Override
    public ResponseVO getTaskByName(GroupIdAndTaskNameForm groupIdAndTaskNameForm) {
        if(groupRepository.findGroupById(groupIdAndTaskNameForm.getGroupId())==null){
            return ResponseVO.buildFailure("This group does not exist!");
        }
        List<Task> res = taskRepository.findTasksByName(groupIdAndTaskNameForm.getTaskName());
        if(res == null){
            return ResponseVO.buildFailure("Getting task list failed.");
        }
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO deleteTask(int taskId) {
        //------------------delete是否要传入userId和groupId来验证？是否有什么异常？-----------------------

        if(taskRepository.findTaskById(taskId)==null){
            return ResponseVO.buildFailure("This task does not exist.");
        }
        taskRepository.deleteById(taskId);
        return ResponseVO.buildSuccess("Deleting task succeed.");
    }

    @Override
    public ResponseVO modifyTask(Task task) {
        if(taskRepository.findTaskById(task.getId())==null){
            return ResponseVO.buildFailure("This task does not exist.");
        }
        task = taskRepository.save(task);
        if(task.getId()==0){
            return ResponseVO.buildFailure("Modifying task failed.");
        }
        return ResponseVO.buildSuccess(task);
    }

    @Override
    public ResponseVO deliverTaskForOneMember(TaskAssignment taskAssignment) {
        //deleteTask()
        if(groupMemberRepository.findGroupMemberByGroupIdAndUserId(taskAssignment.getGroupId(),taskAssignment.getUserId())==null){
            return ResponseVO.buildFailure("Group or member does not exist.");
        }
        if(taskRepository.findTaskById(taskAssignment.getTaskId())==null){
            return ResponseVO.buildFailure("Task does not exist.");
        }

        taskAssignment = taskAssignmentRepository.save(taskAssignment);
        if(taskAssignment.getId()==0){
            return ResponseVO.buildFailure("Saving task failed.");
        }
        return ResponseVO.buildSuccess(taskAssignment);


    }

    @Override
    public ResponseVO getUserTaskList(int groupId, int userId) {
        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findTaskAssignmentsByGroupIdAndUserId(groupId,userId);
        if(taskAssignments==null){return ResponseVO.buildFailure("getting task list failed.");}

        List<Integer> ids = new ArrayList<>();
        for(TaskAssignment a:taskAssignments){
            ids.add(a.getTaskId());
        }
        List<Task> res = taskRepository.findAllById(ids);
        HashMap<String,List> map = new HashMap<>();
        map.put("tasks",res);
        map.put("assignments",taskAssignments);

        return ResponseVO.buildSuccess(map);
    }

    @Override
    public ResponseVO deliverTask(HashMap tasksForMember) {
        //是否需要？
        return null;
    }

    @Override
    public ResponseVO completeTask(int taskId){
        Task t = taskRepository.findTaskById(taskId);
        if(t==null){return ResponseVO.buildFailure("This task does not exist.");}
        if(t.getState()==1){return  ResponseVO.buildFailure("This task has already completed.");}
        t.setState(1);
        taskRepository.save(t);
        return ResponseVO.buildSuccess("The task has completed successfully.");
    }
}
