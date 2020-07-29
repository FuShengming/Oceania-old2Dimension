package com.old2dimension.OCEANIA.blImpl.TaskBLImplTest;

import com.old2dimension.OCEANIA.blImpl.TaskBLImpl;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.GroupIdAndTaskNameForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

public class TaskBLImplTest {
    @Test
    public void createTaskTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupRepository(groupRepository);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(0, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        Task t = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(taskRepository.save(any())).thenReturn(t);

        ResponseVO responseVO = taskBL.createTask(task);
        Assert.assertEquals(((Task) responseVO.getContent()).getDescription(), "testDescription");
        Assert.assertEquals(((Task) responseVO.getContent()).getState(), 0);
    }

    @Test
    public void createTaskTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        taskBL.setGroupRepository(groupRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(0, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.createTask(task);
        Assert.assertEquals(responseVO.getMessage(), "This group does not exist!");
    }

    @Test
    public void createTaskTest3() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        taskBL.setGroupRepository(groupRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);

        ResponseVO responseVO = taskBL.createTask(task);
        Assert.assertEquals(responseVO.getMessage(), "This task existed!");
    }

    @Test
    public void createTaskTest4() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupRepository(groupRepository);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(0, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(taskRepository.save(any())).thenReturn(task);

        ResponseVO responseVO = taskBL.createTask(task);
        Assert.assertEquals(responseVO.getMessage(), "Creating task failed");
    }

    @Test
    public void getAllTaskTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupRepository(groupRepository);
        taskBL.setTaskRepository(taskRepository);

        ArrayList<Task> tasks = new ArrayList<>();
        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(0, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        tasks.add(task);
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(taskRepository.findTasksByGroupId(1)).thenReturn(tasks);

        ResponseVO responseVO = taskBL.getAllTask(1);
        Assert.assertEquals(((ArrayList<Task>) responseVO.getContent()).size(), 1);
        Assert.assertEquals(((ArrayList<Task>) responseVO.getContent()).get(0).getLabel(), "testLabel");
    }

    @Test
    public void getAllTaskTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        taskBL.setGroupRepository(groupRepository);

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.getAllTask(1);
        Assert.assertEquals(responseVO.getMessage(), "This group does not exist!");
    }

    @Test
    public void getAllTaskTest3() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupRepository(groupRepository);
        taskBL.setTaskRepository(taskRepository);

        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(taskRepository.findTasksByGroupId(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.getAllTask(1);
        Assert.assertEquals(responseVO.getMessage(), "Getting task list failed");
    }

    @Test
    public void getTaskByNameTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupRepository(groupRepository);
        taskBL.setTaskRepository(taskRepository);

        GroupIdAndTaskNameForm groupIdAndTaskNameForm = new GroupIdAndTaskNameForm();
        groupIdAndTaskNameForm.setGroupId(1);
        groupIdAndTaskNameForm.setTaskName("testTask");
        Group group = new Group(1, "testGroup");
        ArrayList<Task> tasks = new ArrayList<>();
        Date startDate1 = new Date(120, 5, 1);
        Date endDate1 = new Date(120, 5, 20);
        Date startDate2 = new Date(120, 7, 1);
        Date endDate2 = new Date(120, 7, 31);
        Task task1 = new Task(1, 1, "testTask1", "testLabel", "testDescription", startDate1, endDate1, 0);
        Task task2 = new Task(2, 1, "testTask2", "testLabel", "testDescription", startDate1, endDate1, 0);
        tasks.add(task1);
        tasks.add(task2);

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(taskRepository.findTasksByName("testTask")).thenReturn(tasks);

        ResponseVO responseVO = taskBL.getTaskByName(groupIdAndTaskNameForm);
        Assert.assertEquals(((ArrayList<Task>) responseVO.getContent()).size(), 2);
        Assert.assertEquals(((ArrayList<Task>) responseVO.getContent()).get(1).getName(), "testTask2");
    }

    @Test
    public void getTaskByNameTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        taskBL.setGroupRepository(groupRepository);

        GroupIdAndTaskNameForm groupIdAndTaskNameForm = new GroupIdAndTaskNameForm();
        groupIdAndTaskNameForm.setGroupId(1);
        groupIdAndTaskNameForm.setTaskName("testTask");

        when(groupRepository.findGroupById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.getTaskByName(groupIdAndTaskNameForm);
        Assert.assertEquals(responseVO.getMessage(), "This group does not exist!");
    }

    @Test
    public void getTaskByNameTest3() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupRepository groupRepository = mock(GroupRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupRepository(groupRepository);
        taskBL.setTaskRepository(taskRepository);

        GroupIdAndTaskNameForm groupIdAndTaskNameForm = new GroupIdAndTaskNameForm();
        groupIdAndTaskNameForm.setGroupId(1);
        groupIdAndTaskNameForm.setTaskName("testTask");
        Group group = new Group(1, "testGroup");

        when(groupRepository.findGroupById(1)).thenReturn(group);
        when(taskRepository.findTasksByName("testTask")).thenReturn(null);

        ResponseVO responseVO = taskBL.getTaskByName(groupIdAndTaskNameForm);
        Assert.assertEquals(responseVO.getMessage(), "Getting task list failed.");
    }

    @Test
    public void deleteTaskTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(taskRepository.findTaskById(1)).thenReturn(task);

        ResponseVO responseVO = taskBL.deleteTask(1);
        Assert.assertEquals(responseVO.getContent(), "Deleting task succeed.");
    }

    @Test
    public void deleteTaskTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        when(taskRepository.findTaskById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.deleteTask(1);
        Assert.assertEquals(responseVO.getMessage(), "This task does not exist.");
    }

    @Test
    public void modifyTaskTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(taskRepository.findTaskById(1)).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(task);

        ResponseVO responseVO = taskBL.modifyTask(task);
        Assert.assertEquals(((Task) responseVO.getContent()).getLabel(), "testLabel");
    }

    @Test
    public void modifyTaskTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(2, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(taskRepository.findTaskById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.modifyTask(task);
        Assert.assertEquals(responseVO.getMessage(), "This task does not exist.");
    }

    @Test
    public void modifyTaskTest3() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        Task res = new Task(0, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(taskRepository.findTaskById(1)).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(res);

        ResponseVO responseVO = taskBL.modifyTask(task);
        Assert.assertEquals(responseVO.getMessage(), "Modifying task failed.");
    }

    @Test
    public void deliverTaskForOneMemberTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        TaskAssignmentRepository taskAssignmentRepository = mock(TaskAssignmentRepository.class);
        taskBL.setGroupMemberRepository(groupMemberRepository);
        taskBL.setTaskRepository(taskRepository);
        taskBL.setTaskAssignmentRepository(taskAssignmentRepository);

        TaskAssignment taskAssignment = new TaskAssignment(1, 1, 1);
        taskAssignment.setId(1);
        GroupMember groupMember = new GroupMember(1, 1, 1);
        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(taskRepository.findTaskById(1)).thenReturn(task);
        when(taskAssignmentRepository.save(any())).thenReturn(taskAssignment);

        ResponseVO responseVO = taskBL.deliverTaskForOneMember(taskAssignment);
        Assert.assertEquals(((TaskAssignment) responseVO.getContent()).getTaskId(), 1);
        Assert.assertEquals(((TaskAssignment) responseVO.getContent()).getGroupId(), 1);
    }

    @Test
    public void deliverTaskForOneMemberTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        taskBL.setGroupMemberRepository(groupMemberRepository);

        TaskAssignment taskAssignment = new TaskAssignment(1, 1, 1);
        taskAssignment.setId(1);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(null);

        ResponseVO responseVO = taskBL.deliverTaskForOneMember(taskAssignment);
        Assert.assertEquals(responseVO.getMessage(), "Group or member does not exist.");
    }

    @Test
    public void deliverTaskForOneMemberTest3() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setGroupMemberRepository(groupMemberRepository);
        taskBL.setTaskRepository(taskRepository);

        TaskAssignment taskAssignment = new TaskAssignment(1, 1, 1);
        taskAssignment.setId(1);
        GroupMember groupMember = new GroupMember(1, 1, 1);
        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(2, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(taskRepository.findTaskById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.deliverTaskForOneMember(taskAssignment);
        Assert.assertEquals(responseVO.getMessage(), "Task does not exist.");
    }

    @Test
    public void deliverTaskForOneMemberTest4() {
        TaskBLImpl taskBL = new TaskBLImpl();
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        TaskAssignmentRepository taskAssignmentRepository = mock(TaskAssignmentRepository.class);
        taskBL.setGroupMemberRepository(groupMemberRepository);
        taskBL.setTaskRepository(taskRepository);
        taskBL.setTaskAssignmentRepository(taskAssignmentRepository);

        TaskAssignment taskAssignment = new TaskAssignment(1, 1, 1);
        taskAssignment.setId(0);
        GroupMember groupMember = new GroupMember(1, 1, 1);
        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);

        when(groupMemberRepository.findGroupMemberByGroupIdAndUserId(1, 1)).thenReturn(groupMember);
        when(taskRepository.findTaskById(1)).thenReturn(task);
        when(taskAssignmentRepository.save(any())).thenReturn(taskAssignment);

        ResponseVO responseVO = taskBL.deliverTaskForOneMember(taskAssignment);
        Assert.assertEquals(responseVO.getMessage(), "Saving task failed.");
    }

    @Test
    public void completeTaskTest1() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 0);
        Task res = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 1);

        when(taskRepository.findTaskById(1)).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(res);

        ResponseVO responseVO = taskBL.completeTask(1);
        Assert.assertEquals(responseVO.getContent(), "The task has completed successfully.");
    }

    @Test
    public void completeTaskTest2() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        when(taskRepository.findTaskById(1)).thenReturn(null);

        ResponseVO responseVO = taskBL.completeTask(1);
        Assert.assertEquals(responseVO.getMessage(), "This task does not exist.");
    }

    @Test
    public void completeTaskTest3() {
        TaskBLImpl taskBL = new TaskBLImpl();
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskBL.setTaskRepository(taskRepository);

        Date startDate = new Date(120, 6, 1);
        Date endDate = new Date(120, 6, 31);
        Task task = new Task(1, 1, "testTask", "testLabel", "testDescription", startDate, endDate, 1);

        when(taskRepository.findTaskById(1)).thenReturn(task);

        ResponseVO responseVO = taskBL.completeTask(1);
        Assert.assertEquals(responseVO.getMessage(), "This task has already completed.");
    }
}
