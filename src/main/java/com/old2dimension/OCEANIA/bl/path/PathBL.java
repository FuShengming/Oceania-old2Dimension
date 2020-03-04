package com.old2dimension.OCEANIA.bl.path;

import com.old2dimension.OCEANIA.vo.FindPathVO;
import com.old2dimension.OCEANIA.vo.FuncInfoForm;

public interface PathBL {

    FindPathVO findPath(FuncInfoForm startId, FuncInfoForm endId);
}
