package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/group")
public class GroupController {

    @RequestMapping(value = "/findUser/{name}",method = RequestMethod.GET)
    public ResponseVO findUser(@PathVariable String name){
        return null;
    }

    @RequestMapping(value = "/createGroup",method = RequestMethod.POST)
    public ResponseVO createGroup(@RequestBody GroupNameAndMembersForm groupNameAndMembersForm){
        return null;
    }

    @RequestMapping(value = "/setGroupLeader",method = RequestMethod.POST)
    public ResponseVO setGroupLeader(@RequestBody GroupIdAndLeaderForm groupIdAndLeaderForm){
        return null;
    }

    @RequestMapping(value = "/createTask",method = RequestMethod.POST)
    public ResponseVO createTask(@RequestBody TaskVO taskVO){
        return null;
    }

    @RequestMapping(value = "/getAllTask/{groupId}",method = RequestMethod.GET)
    public ResponseVO getAllTask(@PathVariable int groupId){
        return null;
    }

    @RequestMapping(value = "/getTaskByName",method = RequestMethod.GET)
    public ResponseVO getTaskByName(@RequestBody GroupIdAndTaskNameForm groupIdAndTaskNameForm){
        return null;
    }
/*    @RequestMapping(value = "/getAllTask/{}",method = RequestMethod.GET)
    public ResponseVO getAllTask(@PathVariable int groupId){
        return null;
    }
    */



    @RequestMapping(value = "/deleteTask/{taskId}",method = RequestMethod.GET)
    public ResponseVO deleteTask(@PathVariable int taskId){
        return null;
    }

    @RequestMapping(value = "/modifyTask",method = RequestMethod.POST)
    public ResponseVO modifyTask(@RequestBody TaskVO taskVO){
        return null;
    }

    @RequestMapping(value = "/deliverTaskForOneMember",method = RequestMethod.POST)
    public ResponseVO deliverTaskForOneMember(@RequestBody UserIdAndTaskIdForm userIdAndTaskIdForm){
        return null;
    }

    @RequestMapping(value = "/deliverTasks",method = RequestMethod.POST)
    public ResponseVO deliverTask(@RequestParam HashMap tasksForMember){
        return null;
    }

    @RequestMapping(value = "/releaseAnnouncement",method = RequestMethod.POST)
    public ResponseVO releaseAnnouncement(@RequestBody Announcement announcement){
        return null;
    }


}
