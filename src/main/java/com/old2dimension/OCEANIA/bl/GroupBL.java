package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.vo.GroupIdAndCodeIdForm;
import com.old2dimension.OCEANIA.vo.GroupIdAndUserForm;
import com.old2dimension.OCEANIA.vo.GroupNameAndMembersForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface GroupBL {
    public ResponseVO findUser(String name);
    public ResponseVO createGroup(GroupNameAndMembersForm groupNameAndMembersForm);
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm);
    public ResponseVO inviteUser( String name);
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm);
    public ResponseVO quitGroup(String groupId);

    public ResponseVO searchGroupByUser(String userId);
    public ResponseVO releaseAnnouncement(Announcement announcement);


}
