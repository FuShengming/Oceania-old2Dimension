package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.*;


public interface CodeBL {
    ResponseVO getFuncCode(VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId);

    ResponseVO getCodesByUserId(int userId);

    ResponseVO getCodeStructure(UserAndCodeForm userAndCodeForm);

    ResponseVO delete(UserAndCodeForm userAndCodeForm);

    ResponseVO modifyName(CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm);
}
