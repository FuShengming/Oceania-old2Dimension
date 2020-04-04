package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.VertexVO;
import org.springframework.stereotype.Component;

public interface CodeBL {
    public ResponseVO getFuncCode(VertexVO vertexVO);
}
