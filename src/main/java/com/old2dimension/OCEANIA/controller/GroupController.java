package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.GroupBL;
import com.old2dimension.OCEANIA.bl.GroupCodeBL;
import com.old2dimension.OCEANIA.bl.TaskBL;
import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.po.Invitation;
import com.old2dimension.OCEANIA.po.Task;
import com.old2dimension.OCEANIA.po.TaskAssignment;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupBL groupBL;
    @Autowired
    TaskBL taskBL;
    @Autowired
    GroupCodeBL groupCodeBL;

    @RequestMapping(value = "/getUserInvitations/{userId}", method = RequestMethod.GET)
    public ResponseVO getUserInvitation(@PathVariable int userId) {
        return groupBL.getUserInvitation(userId);
    }

    @RequestMapping(value = "/findUser/{name}", method = RequestMethod.GET)
    public ResponseVO findUser(@PathVariable String name) {
        return groupBL.findUser(name);
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public ResponseVO createGroup(@RequestBody CreateGroupForm createGroupForm) {
        return groupBL.createGroup(createGroupForm);
    }

    @RequestMapping(value = "/setGroupLeader", method = RequestMethod.POST)
    public ResponseVO setGroupLeader(@RequestBody GroupIdAndUserForm groupIdAndLeaderForm) {
        return groupBL.setGroupLeader(groupIdAndLeaderForm);
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseVO createTask(@RequestBody Task task) {
        return taskBL.createTask(task);
    }

    @RequestMapping(value = "/getAllTask/{groupId}", method = RequestMethod.GET)
    public ResponseVO getAllTask(@PathVariable int groupId) {
        return taskBL.getAllTask(groupId);
    }

    @RequestMapping(value = "/getTaskByName", method = RequestMethod.POST)
    public ResponseVO getTaskByName(@RequestBody GroupIdAndTaskNameForm groupIdAndTaskNameForm) {
        return taskBL.getTaskByName(groupIdAndTaskNameForm);
    }

    @RequestMapping(value = "/inviteUser", method = RequestMethod.POST)
    public ResponseVO inviteUser(@RequestBody Invitation invitation) {
        return groupBL.inviteUser(invitation);
    }

    @RequestMapping(value = "/quitGroup", method = RequestMethod.POST)
    public ResponseVO quitGroup(@RequestBody GroupIdAndUserForm groupIdAndUserForm) {
        return groupBL.quitGroup(groupIdAndUserForm);
    }

    @RequestMapping(value = "/joinGroup", method = RequestMethod.POST)
    public ResponseVO joinGroup(@RequestBody Invitation invitation) {
        return groupBL.joinGroup(invitation);
    }

    @RequestMapping(value = "/addCode", method = RequestMethod.POST)
    public ResponseVO addCode(@RequestBody GroupIdAndCodeIdForm groupIdAndCodeIdForm) {

        return groupCodeBL.addCode(groupIdAndCodeIdForm);
    }

    @RequestMapping(value = "/deleteCode", method = RequestMethod.POST)
    public ResponseVO deleteCode(@RequestBody GroupIdAndCodeIdForm groupIdAndCodeIdForm) {
        return groupCodeBL.deleteCode(groupIdAndCodeIdForm);
    }

    @RequestMapping(value = "/getGroupCodeList/{groupId}", method = RequestMethod.GET)
    public ResponseVO getGroupCodeList(@PathVariable int groupId) {
        return groupCodeBL.getGroupCodeList(groupId);
    }

    @RequestMapping(value = "/getAllGroups/{userId}", method = RequestMethod.GET)
    public ResponseVO searchGroupByUser(@PathVariable int userId) {
        return groupBL.searchGroupByUser(userId);
    }

    @RequestMapping(value = "/deleteTask/{taskId}", method = RequestMethod.GET)
    public ResponseVO deleteTask(@PathVariable int taskId) {
        return taskBL.deleteTask(taskId);
    }

    @RequestMapping(value = "/modifyTask", method = RequestMethod.POST)
    public ResponseVO modifyTask(@RequestBody Task task) {
        return taskBL.modifyTask(task);
    }

    @RequestMapping(value = "/deliverTaskForOneMember", method = RequestMethod.POST)
    public ResponseVO deliverTaskForOneMember(@RequestBody TaskAssignment taskAssignment) {
        return taskBL.deliverTaskForOneMember(taskAssignment);
    }

    @RequestMapping(value = "/task/complete/{taskId}", method = RequestMethod.GET)
    public ResponseVO completeTask(@PathVariable int taskId) {
        return taskBL.completeTask(taskId);
    }

    @RequestMapping(value = "/getUserTask", method = RequestMethod.POST)
    public ResponseVO getUserTask(@RequestBody GroupIdAndUserForm groupIdAndUserForm) {
        return taskBL.getUserTaskList(groupIdAndUserForm.getGroupId(), groupIdAndUserForm.getUserId());
    }

    @RequestMapping(value = "/getGroupName/{groupId}",method = RequestMethod.GET)
    public ResponseVO getGroupName(@PathVariable int groupId){
        return groupBL.getGroupName(groupId);
    }


    @RequestMapping(value = "/releaseAnnouncement", method = RequestMethod.POST)
    public ResponseVO releaseAnnouncement(@RequestBody Announcement announcement) {
        return groupBL.releaseAnnouncement(announcement);
    }

    @RequestMapping(value = "/getCodeStatistics", method = RequestMethod.POST)
    public ResponseVO getCodeStatistics(@RequestBody GroupIdAndCodeIdForm groupIdAndCodeIdForm) {
        return groupCodeBL.getCodeStatistics(groupIdAndCodeIdForm);
    }

    @RequestMapping(value = "/getGroupMember/{groupId}", method = RequestMethod.GET)
    public ResponseVO getGroupMembers(@PathVariable int groupId) {
        return groupBL.getGroupMembers(groupId);
    }

    @RequestMapping(value = "/getGroupMemberNames/{groupId}", method = RequestMethod.GET)
    public ResponseVO getGroupMemberNames(@PathVariable int groupId) {
        return groupBL.getGroupMemberNames(groupId);
    }


    @RequestMapping(value = "/modifyGroupInfo", method = RequestMethod.POST)
    public ResponseVO modifyGroupInfo(@RequestBody GroupAndUserIdForm groupAndUserIdForm) {
        return groupBL.updateGroupInfo(groupAndUserIdForm);
    }

    @RequestMapping(value = "/getGroupAnnouncements", method = RequestMethod.POST)
    public ResponseVO getGroupAnnouncements(@RequestBody GroupIdAndUserForm groupIdAndUserForm) {
        return groupBL.getGroupAnnouncements(groupIdAndUserForm.getGroupId(), groupIdAndUserForm.getUserId());
    }

    @RequestMapping(value = "/getUnreadAnnouncements/{userId}", method = RequestMethod.GET)
    public ResponseVO getUnreadAnnouncements(@PathVariable int userId) {
        return groupBL.getUnreadAnnouncements(userId);
    }


    @RequestMapping(value = "/readAnnouncement", method = RequestMethod.POST)
    public ResponseVO readAnnouncement(@RequestBody UserIdAndAnnouncementId userIdAndAnnouncementId) {
        return groupBL.readAnnouncement(userIdAndAnnouncementId.getUserId(), userIdAndAnnouncementId.getAnnouncementId());
    }

    @RequestMapping(value = "/readInvitation", method = RequestMethod.POST)
    public ResponseVO readInvitation(@RequestBody UserIdAndInvitationIdForm userIdAndInvitationIdForm) {
        return groupBL.readInvitation(userIdAndInvitationIdForm.getUserId(), userIdAndInvitationIdForm.getInvitationId());
    }

    @RequestMapping(value = "/ignoreInvitation", method = RequestMethod.POST)
    public ResponseVO ignoreInvitation(@RequestBody UserIdAndInvitationIdForm userIdAndInvitationIdForm) {
        return groupBL.ignoreInvitation(userIdAndInvitationIdForm.getUserId(), userIdAndInvitationIdForm.getInvitationId());
    }



}
