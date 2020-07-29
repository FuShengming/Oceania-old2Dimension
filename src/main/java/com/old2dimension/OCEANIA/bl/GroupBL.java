package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.po.Invitation;
import com.old2dimension.OCEANIA.vo.GroupIdAndUserForm;
import com.old2dimension.OCEANIA.vo.GroupNameAndCreatorIdForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.web.bind.annotation.PathVariable;


public interface GroupBL {
    public ResponseVO findUser(String name);
    public ResponseVO createGroup(GroupNameAndCreatorIdForm groupNameAndCreatorIdForm);
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm);
    public ResponseVO inviteUser(Invitation invitation);
    public ResponseVO readInvitation(int userId,int invitationId);
    public ResponseVO ignoreInvitation(int userId,int invitationId);
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm);
    public ResponseVO joinGroup(Invitation invitation);

    public ResponseVO searchGroupByUser(int userId);
    public ResponseVO getGroupMembers(int groupId);
    public ResponseVO releaseAnnouncement(Announcement announcement);
    public ResponseVO getGroupAnnouncements(int groupId);
    public ResponseVO readAnnouncement(int userId,int announcementId);


}
