package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.vo.GroupIdAndUserForm;
import com.old2dimension.OCEANIA.vo.GroupNameAndCreatorIdForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.web.bind.annotation.PathVariable;


public interface GroupBL {
    public ResponseVO findUser(String name);
    public ResponseVO createGroup(GroupNameAndCreatorIdForm groupNameAndCreatorIdForm);
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm);
    public ResponseVO inviteUser( String name);
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm);
    public ResponseVO joinGroup(GroupIdAndUserForm groupIdAndUserForm);

    public ResponseVO searchGroupByUser(String userId);
    public ResponseVO getGroupMembers(String groupId);
    public ResponseVO releaseAnnouncement(Announcement announcement);
    public ResponseVO getGroupAnnouncements(String groupId);


}
