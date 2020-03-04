package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.FindPathVO;
import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import org.springframework.stereotype.Component;

@Component
public interface PathBL {

    FindPathVO findPath(FuncInfoForm startId, FuncInfoForm endId);
}
