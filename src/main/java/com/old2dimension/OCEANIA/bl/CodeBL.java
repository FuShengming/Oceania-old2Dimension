package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.*;


public interface CodeBL {
    public ResponseVO getFuncCode(VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId);
    public ResponseVO getCodesByUserId(int userId);
    public ResponseVO getCodeStructure(UserAndCodeForm userAndCodeForm);
    public ResponseVO addCode(UserAndCodeForm userAndCodeForm);
    public ResponseVO modifyName(CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm);
}
