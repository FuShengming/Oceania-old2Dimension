package com.old2dimension.OCEANIA.vo;

public class VertexVOAndUserIdAndCodeId {

    private VertexVO vertexVO;

    private int codeId;
    private int userId;

    public VertexVOAndUserIdAndCodeId() {
    }

    public VertexVOAndUserIdAndCodeId(VertexVO vertexVO, int codeId, int userId) {
        this.vertexVO = vertexVO;
        this.codeId = codeId;
        this.userId = userId;
    }

    public VertexVO getVertexVO() {
        return vertexVO;
    }

    public void setVertexVO(VertexVO vertexVO) {
        this.vertexVO = vertexVO;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
