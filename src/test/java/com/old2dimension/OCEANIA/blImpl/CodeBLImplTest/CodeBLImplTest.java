package com.old2dimension.OCEANIA.blImpl.CodeBLImplTest;

import com.old2dimension.OCEANIA.blImpl.CodeBLImpl;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.UserRepository;
import com.old2dimension.OCEANIA.dao.WorkPlaceRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.CodeNode;
import com.old2dimension.OCEANIA.po.User;
import com.old2dimension.OCEANIA.po.WorkSpace;
import com.old2dimension.OCEANIA.vo.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

public class CodeBLImplTest {

    String lineSeparator = System.lineSeparator();

    @Test
    public void getFuncCodeTest1() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust.dao.mysql");
        vertexVO.setBelongClass("ReferralDAO$ReferralListQuery");
        vertexVO.setFuncName("<init>");
        String[] args = {"edu.ncsu.csc.itrust.dao.mysql.ReferralDAO",
                "edu.ncsu.csc.itrust.dao.DAOFactory",
                "long"};
        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        String funcCode = "\n\t\tpublic ReferralListQuery(DAOFactory factory, long userid) {\r\n\t\t\tthis.factory = factory;\r\n\t\t\tthis.userid = userid;\r\n\t\t\t// initialize lookup map\r\n\t\t\tsortColumns = new HashMap<String,String>();\r\n\t\t\tsortColumns.put(\"patientName\", \"CONCAT(patients.lastName, ' ', patients.firstName)\");\r\n\t\t\tsortColumns.put(\"receiverName\", \"CONCAT(preceiver.lastName, preceiver.firstName)\");\r\n\t\t\tsortColumns.put(\"senderName\", \"CONCAT(psender.lastName, psender.firstName)\");\r\n\t\t\tsortColumns.put(\"timestamp\", \"referrals.timestamp\");\r\n\t\t\tsortColumns.put(\"priority\", \"referrals.PriorityCode\");\r\n\t\t}";
        funcCode = funcCode.replace(lineSeparator,"");
        funcCode = funcCode.replace("\n","");
        funcCode = funcCode.replace("\r","");
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(((String)responseVO.getContent()).replace(lineSeparator,"")
                .replace("\n","").replace("\r","")
                ,funcCode);
    }


    @Test
    public void getFuncCodeTest2() {

        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);

        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(2);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GraphCalculateImpl graphCalculate = mock(GraphCalculateImpl.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getMessage(), "dictionary does not exist");
    }

    @Test
    public void getFuncCodeTest3() {

        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);

        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(2);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GraphCalculateImpl graphCalculate = mock(GraphCalculateImpl.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(null);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getMessage(), "no such user or code");
    }

    @Test
    public void getFuncCodeTest4() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust.enums");
        vertexVO.setBelongClass("Gender");
        vertexVO.setFuncName("equals");
        String[] args = {"java.lang.Object"
        };


        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        //String funcCode = "\n\t\tpublic ReferralListQuery(DAOFactory factory, long userid) {\r\n\t\t\tthis.factory = factory;\r\n\t\t\tthis.userid = userid;\r\n\t\t\t// initialize lookup map\r\n\t\t\tsortColumns = new HashMap<String,String>();\r\n\t\t\tsortColumns.put(\"patientName\", \"CONCAT(patients.lastName, ' ', patients.firstName)\");\r\n\t\t\tsortColumns.put(\"receiverName\", \"CONCAT(preceiver.lastName, preceiver.firstName)\");\r\n\t\t\tsortColumns.put(\"senderName\", \"CONCAT(psender.lastName, psender.firstName)\");\r\n\t\t\tsortColumns.put(\"timestamp\", \"referrals.timestamp\");\r\n\t\t\tsortColumns.put(\"priority\", \"referrals.PriorityCode\");\r\n\t\t}";
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getMessage(), "doesn't have explicit declaration. It maybe a parent class function or library function.");
    }

    @Test
    public void getFuncCodeTest5() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust.enums");
        vertexVO.setBelongClass("Gender");
        vertexVO.setFuncName("rua");
        String[] args = {"java.lang.Object"
        };


        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        //String funcCode = "\n\t\tpublic ReferralListQuery(DAOFactory factory, long userid) {\r\n\t\t\tthis.factory = factory;\r\n\t\t\tthis.userid = userid;\r\n\t\t\t// initialize lookup map\r\n\t\t\tsortColumns = new HashMap<String,String>();\r\n\t\t\tsortColumns.put(\"patientName\", \"CONCAT(patients.lastName, ' ', patients.firstName)\");\r\n\t\t\tsortColumns.put(\"receiverName\", \"CONCAT(preceiver.lastName, preceiver.firstName)\");\r\n\t\t\tsortColumns.put(\"senderName\", \"CONCAT(psender.lastName, psender.firstName)\");\r\n\t\t\tsortColumns.put(\"timestamp\", \"referrals.timestamp\");\r\n\t\t\tsortColumns.put(\"priority\", \"referrals.PriorityCode\");\r\n\t\t}";
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getMessage(), "do not find function name in file");
    }

    @Test
    public void getFuncCodeTest6() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust.action");
        vertexVO.setBelongClass("Localization");
        vertexVO.setFuncName("<clinit>");
        String[] args = {""
        };


        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getMessage(), "class file do not exist");
    }

    @Test
    public void getFuncCodeTest7() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust");
        vertexVO.setBelongClass("Messages");
        vertexVO.setFuncName("<clinit>");
        String[] args = {""
        };


        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getContent(), "this function is class initialize function, it doesn't have a function body.");
    }

    @Test
    public void getFuncCodeTest8() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsA.csc.itrust.action");
        vertexVO.setBelongClass("Localization");
        vertexVO.setFuncName("<clinit>");
        String[] args = {""
        };


        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        Assert.assertEquals(responseVO.getMessage(), "dictionary does not exist");
    }

    @Test
    public void getFuncCodeTest9() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust.action");
        vertexVO.setBelongClass("GetVisitRemindersAction$ReminderType");
        vertexVO.setFuncName("getTypeName");
        String[] args = {""
        };


        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        String content = (String)responseVO.getContent();
        String ex = "\n\t\tpublic String getTypeName() {\r\n\t\t\treturn typeName;\r\n\t\t}".replace(lineSeparator,"");
        ex = ex.replace("\n","");
        ex = ex.replace("\r","");
        content = content.replace(lineSeparator,"");
        content = content.replace("\n","");
        content = content.replace("\r","");
        Assert.assertEquals(content, ex);

    }


    @Test
    public void getFuncCodeTest10() {

        VertexVO vertexVO = new VertexVO();
        vertexVO.setBelongPackage("edu.ncsu.csc.itrust");
        vertexVO.setBelongClass("BeanBuilder");
        vertexVO.setFuncName("build");
        String[] args = {"java.util.Map",
                "java.lang.Object"
        };

        String expectedFuncBody = "\n\tpublic T build(Map map, T bean) throws Exception {\r\n\t\t// JavaBeans should not have overloaded methods, according to their API\r\n\t\t// (a stupid limitation!)\r\n\t\t// Nevertheless, we should check for it\r\n\t\tcheckOverloadedMethods(bean);\r\n\r\n\t\t// Use an introspector to find all of the getXXX or setXXX, we only want\r\n\t\t// the setXXX\r\n\t\tPropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(bean.getClass())\r\n\t\t\t\t.getPropertyDescriptors();\r\n\t\tfor (PropertyDescriptor descriptor : propertyDescriptors) {\r\n\t\t\t// if object is null, either it was ignored or empty - just go with\r\n\t\t\t// bean's default\r\n\t\t\tString[] value = (String[]) map.get(descriptor.getName());\r\n\t\t\tMethod writeMethod = descriptor.getWriteMethod();\r\n\t\t\tif (!\"class\".equals(descriptor.getName()) && value != null && writeMethod != null) {\r\n\t\t\t\t// descriptor's name is the name of your property; like\r\n\t\t\t\t// firstName\r\n\t\t\t\t// only take the first string\r\n\t\t\t\ttry {\r\n\t\t\t\t\t// Skip the setters for enumerations\r\n\t\t\t\t\tif (writeMethod.getParameterTypes()[0].getEnumConstants() == null)\r\n\t\t\t\t\t\twriteMethod.invoke(bean, new Object[] { value[0] });\r\n\t\t\t\t} catch (IllegalArgumentException e) {\r\n\t\t\t\t\t// Throw a more informative exception\r\n\t\t\t\t\tthrow new IllegalArgumentException(e.getMessage() + \" with \" + writeMethod.getName()\r\n\t\t\t\t\t\t\t+ \" and \" + value[0]);\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn bean;\r\n\t}";
        expectedFuncBody = expectedFuncBody.replace(lineSeparator,"");
        expectedFuncBody = expectedFuncBody.replace("\n","");
        expectedFuncBody = expectedFuncBody.replace("\r","");
        vertexVO.setArgs(args);
        VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId = new VertexVOAndUserIdAndCodeId();
        vertexVOAndUserIdAndCodeId.setCodeId(1);
        vertexVOAndUserIdAndCodeId.setUserId(1);
        vertexVOAndUserIdAndCodeId.setVertexVO(vertexVO);
        Code expected = new Code();
        expected.setId(1);
        expected.setIs_default(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(expected);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.getFuncCode(vertexVOAndUserIdAndCodeId);
        String content = (String)responseVO.getContent();
        content = content.replace(lineSeparator,"");
        content = content.replace("\n","");
        content = content.replace("\r","");
        Assert.assertEquals(content, expectedFuncBody);

    }

    @Test
    public void TestGetCodeStructure1() {
        CodeBLImpl codeBL = new CodeBLImpl();
        GraphCalculateImpl graphCalculate = new GraphCalculateImpl();
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        UserRepository userRepository = mock(UserRepository.class);
        CodeRepository codeRepository = mock(CodeRepository.class);

        User user = new User(1, "gr", "123456");
        //graphCalculate.setCodeRepository(codeRepository);

        when(userRepository.findUserById(1)).thenReturn(user);
        Code code = new Code(1, 1, "iTrust", 1979, 3834, 64, 1);
        when(codeRepository.findCodeById(1)).thenReturn(code);
        when(codeRepository.findCodeByIdAndUserId(1, 1)).thenReturn(code);
        codeBL.setCodeRepository(codeRepository);
        graphCalculate.setUserRepository(userRepository);
        codeBL.setGraphCalculate(graphCalculate);
        graphCalculate.setCodeRepository(codeRepository);
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

    @Test
    public void getCodesByUserIdTest1() {
        ArrayList<Code> res = new ArrayList<Code>();
        Code code = new Code();
        code.setId(1);
        code.setName("name");
        CodeBLImpl codeBL = new CodeBLImpl();
        res.add(code);
        WorkSpace workSpace = new WorkSpace();
        workSpace.setDate(new Date());
        CodeRepository codeRepository = mock(CodeRepository.class);
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        codeBL.setCodeRepository(codeRepository);
        codeBL.setWorkPlaceRepository(workPlaceRepository);
        when(codeRepository.findCodesByUserId(1)).thenReturn(res);
        when(workPlaceRepository.findLatestWorkSpace(1, 1)).thenReturn(workSpace);
        ResponseVO responseVO = codeBL.getCodesByUserId(1);
        Assert.assertEquals("name", ((ArrayList<CodeAndDateForm>) (responseVO.getContent())).get(0).getCodeName());
    }

    @Test
    public void getCodesByUserIdTest2() {
        ArrayList<Code> res = new ArrayList<Code>();
        CodeBLImpl codeBL = new CodeBLImpl();
        WorkSpace workSpace = new WorkSpace();
        workSpace.setDate(new Date());
        CodeRepository codeRepository = mock(CodeRepository.class);
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        codeBL.setCodeRepository(codeRepository);
        codeBL.setWorkPlaceRepository(workPlaceRepository);
        when(codeRepository.findCodesByUserId(1)).thenReturn(res);
        ResponseVO responseVO = codeBL.getCodesByUserId(1);
        Assert.assertEquals(0, ((ArrayList<CodeAndDateForm>) (responseVO.getContent())).size());
    }

    @Test
    public void modifyNameTest1(){
        Code code = new Code();
        code.setUserId(1);
        code.setName("name1");
        CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm = new CodeIdAndUserIdAndNameForm();
        codeIdAndUserIdAndNameForm.setCodeId(1);
        codeIdAndUserIdAndNameForm.setName("name2");
        codeIdAndUserIdAndNameForm.setUserId(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1,1)).thenReturn(code);
        when(codeRepository.save(any())).thenReturn(code);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.modifyName(codeIdAndUserIdAndNameForm);
        Assert.assertEquals(((Code)responseVO.getContent()).getName(),"name2");

    }

    @Test
    public void modifyNameTest2(){
        Code code = new Code();
        code.setUserId(1);
        code.setName("name1");
        CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm = new CodeIdAndUserIdAndNameForm();
        codeIdAndUserIdAndNameForm.setCodeId(1);
        codeIdAndUserIdAndNameForm.setName("name2");
        codeIdAndUserIdAndNameForm.setUserId(1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1,1)).thenReturn(null);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.modifyName(codeIdAndUserIdAndNameForm);
        Assert.assertEquals(responseVO.getMessage(),"no such user or code");

    }

    @Test
    public void deleteTest1(){
        Code code = new Code();
        code.setUserId(1);
        code.setName("name1");

        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1,1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(1,1)).thenReturn(null);
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.delete(userAndCodeForm);
        Assert.assertEquals(responseVO.getMessage(),"code does not exist");
    }
    @Test
    public void deleteTest2(){
        Code code = new Code();
        code.setUserId(1);
        code.setName("name1");
        code.setId(-1);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1,-1);
        CodeBLImpl codeBL = new CodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.findCodeByIdAndUserId(-1,1)).thenReturn(code);
        doNothing().when(codeRepository).delete(any());
        codeBL.setCodeRepository(codeRepository);
        ResponseVO responseVO = codeBL.delete(userAndCodeForm);
        Assert.assertEquals(responseVO.getContent(),"delete successfully");
    }
}


