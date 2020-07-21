package com.old2dimension.OCEANIA.blImpl;
import com.old2dimension.OCEANIA.bl.TaskBL;
import com.old2dimension.OCEANIA.dao.TaskRepository;
import com.old2dimension.OCEANIA.po.Task;
import com.old2dimension.OCEANIA.vo.GroupIdAndTaskNameForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserIdAndTaskIdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TaskBLImpl implements TaskBL {


    @Autowired
    TaskRepository taskRepository;

    @Override
    public ResponseVO createTask(Task task) {
        int id = task.getId();
        int groupId = task.getGroupId();

        return null;
    }

    @Override
    public ResponseVO getAllTask(int groupId) {
        return null;
    }

    @Override
    public ResponseVO getTaskByName(GroupIdAndTaskNameForm groupIdAndTaskNameForm) {
        return null;
    }

    @Override
    public ResponseVO deleteTask(int taskId) {
        return null;
    }

    @Override
    public ResponseVO modifyTask(Task task) {
        return null;
    }

    @Override
    public ResponseVO deliverTaskForOneMember(UserIdAndTaskIdForm userIdAndTaskIdForm) {
        return null;
    }

    @Override
    public ResponseVO deliverTask(HashMap tasksForMember) {
        return null;
    }
}
