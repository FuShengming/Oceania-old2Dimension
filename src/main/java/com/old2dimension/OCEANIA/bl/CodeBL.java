package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.ResponseVO;

import com.old2dimension.OCEANIA.vo.VertexVOAndUserIdAndCodeId;


public interface CodeBL {
    public ResponseVO getFuncCode(VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId);
    public ResponseVO getCodesByUserId(int userId);
}
