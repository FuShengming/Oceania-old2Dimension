package com.old2dimension.OCEANIA.blImpl.CodeBLImplTest;


import com.old2dimension.OCEANIA.blImpl.CodeBLImpl;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.CodeNode;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
public class CodeBLImplTest {
    @Test
    public void TestGetCodeStructure() {
        CodeBLImpl codeBL = new CodeBLImpl();
        GraphCalculateImpl graphCalculate = mock(GraphCalculateImpl.class);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        UserRepository userRepository = mock(UserRepository.class);
        CodeRepository codeRepository = mock(CodeRepository.class);
        codeBL.setGraphCalculate(graphCalculate);
        User user = new User(1, "gr", "123456");
        when(userRepository.findUserById(1)).thenReturn(user);

        Code code = new Code(1, 1, "iTrust", 1979, 3834, 64, 1);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(code);

        ResponseVO responseVO = codeBL.getCodeStructure(userAndCodeForm);
        CodeNode codeNode = (CodeNode) responseVO.getContent();
        Assert.assertEquals(codeNode.getText(), "edu");
        ArrayList<CodeNode> codeNodes1 = codeNode.getNodes();
        CodeNode codeNode1 = codeNodes1.get(0);
        //Assert.assertEquals(codeNode1.getText(), "ncsu");
        ArrayList<CodeNode> codeNodes2 = codeNode1.getNodes();
        CodeNode codeNode2 = codeNodes2.get(0);
        //Assert.assertEquals(codeNode2.getText(), "csc");
        ArrayList<CodeNode> codeNodes3 = codeNode2.getNodes();
        CodeNode codeNode3 = codeNodes3.get(0);
        //Assert.assertEquals(codeNode3.getText(), "itrust");

        ArrayList<CodeNode> rua = codeNode3.getNodes();
        Assert.assertEquals(rua.size(), 19);
    }
}
