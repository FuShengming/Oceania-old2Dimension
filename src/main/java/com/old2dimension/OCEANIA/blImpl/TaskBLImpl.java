package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.TaskBL;
import com.old2dimension.OCEANIA.vo.GroupIdAndTaskNameForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.TaskVO;
import com.old2dimension.OCEANIA.vo.UserIdAndTaskIdForm;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TaskBLImpl implements TaskBL {
    @Override
    public ResponseVO createTask(TaskVO taskVO) {
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
    public ResponseVO modifyTask(TaskVO taskVO) {
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
