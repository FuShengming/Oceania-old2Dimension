package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Task;
import com.old2dimension.OCEANIA.vo.GroupIdAndTaskNameForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;

import com.old2dimension.OCEANIA.vo.UserIdAndTaskIdForm;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

public interface TaskBL {

    public ResponseVO createTask(Task task);
    public ResponseVO getAllTask(int groupId);
    public ResponseVO getTaskByName( GroupIdAndTaskNameForm groupIdAndTaskNameForm);
    public ResponseVO deleteTask(int taskId);
    public ResponseVO modifyTask(Task task);
    public ResponseVO deliverTaskForOneMember(UserIdAndTaskIdForm userIdAndTaskIdForm);
    public ResponseVO deliverTask(HashMap tasksForMember);

}
