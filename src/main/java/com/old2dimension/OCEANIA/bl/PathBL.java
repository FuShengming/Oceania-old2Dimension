package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.VertexVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;


public interface PathBL {

    ResponseVO findPath(VertexVO startId, VertexVO endId);
}
