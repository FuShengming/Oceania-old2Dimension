package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.Group;
import com.old2dimension.OCEANIA.vo.GroupIdAndCodeIdForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;

public interface GroupCodeBL {
    public ResponseVO addCode(GroupIdAndCodeIdForm groupIdAndCodeIdForm);
    public ResponseVO deleteCode(GroupIdAndCodeIdForm groupIdAndCodeIdForm);
    public ResponseVO getGroupCodeList(int groupId);
    public ResponseVO getCodeStatistics(GroupIdAndCodeIdForm groupIdAndCodeIdForm);
}
