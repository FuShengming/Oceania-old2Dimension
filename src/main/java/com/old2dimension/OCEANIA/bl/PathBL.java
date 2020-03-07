package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.FuncInfoForm;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.stereotype.Component;

@Component
public interface PathBL {

    ResponseVO findPath(FuncInfoForm startId, FuncInfoForm endId);
}
