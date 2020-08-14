package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Announcement;
import com.old2dimension.OCEANIA.po.Group;
import com.old2dimension.OCEANIA.po.Invitation;
import com.old2dimension.OCEANIA.vo.GroupAndUserIdForm;
import com.old2dimension.OCEANIA.vo.GroupIdAndUserForm;
import com.old2dimension.OCEANIA.vo.CreateGroupForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;


public interface GroupBL {
    public ResponseVO findUser(String name);
    public ResponseVO createGroup(CreateGroupForm createGroupForm);
    public ResponseVO setGroupLeader(GroupIdAndUserForm groupIdAndLeaderForm);
    public ResponseVO inviteUser(Invitation invitation);
    public ResponseVO readInvitation(int userId,int invitationId);
    public ResponseVO ignoreInvitation(int userId,int invitationId);
    public ResponseVO quitGroup(GroupIdAndUserForm groupIdAndUserForm);
    public ResponseVO joinGroup(Invitation invitation);
    public ResponseVO getUserInvitation(int userId);

    public ResponseVO searchGroupByUser(int userId);
    public ResponseVO getGroupMembers(int groupId);
    public ResponseVO getGroupMemberNames(int groupId);
    public ResponseVO releaseAnnouncement(Announcement announcement);
    public ResponseVO getGroupAnnouncements(int groupId,int userId);
    public ResponseVO readAnnouncement(int userId,int announcementId);

    public ResponseVO updateGroupInfo(GroupAndUserIdForm groupAndUserIdForm);
    public ResponseVO getGroupName(int groupId);
    public ResponseVO getUnreadAnnouncements(int userId);
}
