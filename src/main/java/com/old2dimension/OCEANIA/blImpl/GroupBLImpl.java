package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GroupBL;
import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.vo.GroupIdAndUserForm;
import com.old2dimension.OCEANIA.vo.GroupNameAndMembersForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.stereotype.Component;

@Component
public class GroupBLImpl implements GroupBL {

    @Override
    public ResponseVO findUser(String name) {
        return null;
    }

    @Override
    public ResponseVO createGroup(GroupNameAndMembersForm groupNameAndMembersForm) {
        return null;
    }

    @Override
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm) {
        return null;
    }

    @Override
    public ResponseVO inviteUser(String name) {
        return null;
    }

    @Override
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm) {
        return null;
    }

    @Override
    public ResponseVO quitGroup(String groupId) {
        return null;
    }

    @Override
    public ResponseVO searchGroupByUser(String userId) {
        return null;
    }

    @Override
    public ResponseVO releaseAnnouncement(Announcement announcement) {
        return null;
    }
}
