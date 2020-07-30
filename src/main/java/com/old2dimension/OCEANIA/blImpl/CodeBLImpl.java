package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.CodeBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.WorkPlaceRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.CodeNode;
import com.old2dimension.OCEANIA.po.Vertex;
import com.old2dimension.OCEANIA.po.WorkSpace;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class CodeBLImpl implements CodeBL {

    @Autowired
    CodeRepository codeRepository;
    @Autowired
    WorkPlaceRepository workPlaceRepository;
    @Autowired
    GraphCalculateImpl graphCalculate;

    public void setGraphCalculate(GraphCalculateImpl graphCalculate) {
        this.graphCalculate = graphCalculate;
    }

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setWorkPlaceRepository(WorkPlaceRepository workPlaceRepository) {
        this.workPlaceRepository = workPlaceRepository;
    }

    public ResponseVO modifyName(CodeIdAndUserIdAndNameForm codeIdAndUserIdAndNameForm) {

        Code code = codeRepository.findCodeByIdAndUserId(codeIdAndUserIdAndNameForm.getCodeId(), codeIdAndUserIdAndNameForm.getUserId());
        if (code == null) {
            return ResponseVO.buildFailure("no such user or code");
        }
        try {
            code.setName(codeIdAndUserIdAndNameForm.getName());
            Code res = codeRepository.save(code);
            return ResponseVO.buildSuccess(res);
        } catch (Exception e) {
            return ResponseVO.buildFailure("modify name error");
        }
    }

    public ResponseVO delete(UserAndCodeForm userAndCodeForm) {
        Code code = codeRepository.findCodeByIdAndUserId(userAndCodeForm.getCodeId(), userAndCodeForm.getUserId());
        if (code == null) {
            return ResponseVO.buildFailure("code does not exist");
        }
        if (code.getIs_default() == 0) {
            File javaDir = new File("src/main/resources/analyzeCode/src/" + code.getId());
            File jarFile = new File("src/main/resources/jars/" + code.getId() + ".jar");
            File dependenciesFile = new File("src/main//resources/dependencies/" + code.getId() + ".txt");
            if (javaDir.exists()) {
                boolean isSuccess = deleteFile(javaDir);
                if (!isSuccess) {
                    return ResponseVO.buildFailure("delete java files fail");
                }
            }
            if (jarFile.exists()) {
                boolean isSuccess = deleteFile(jarFile);
                if (!isSuccess) {
                    return ResponseVO.buildFailure("delete jar file fail");
                }
            }
            if (dependenciesFile.exists()) {
                boolean isSuccess = deleteFile(dependenciesFile);
                if (!isSuccess) {
                    return ResponseVO.buildFailure("delete dependencies file fail");
                }
            }
        }
        try {
            codeRepository.delete(code);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("jpa exception");
        }
        return ResponseVO.buildSuccess("delete successfully");
    }


    public ResponseVO getCodesByUserId(int userId) {
        List<Code> dbRes = codeRepository.findCodesByUserId(userId);
        if (dbRes == null) {
            return ResponseVO.buildFailure("get codes error");
        }
        List<CodeAndDateForm> res = new ArrayList<CodeAndDateForm>();
        for (Code cur : dbRes) {
            CodeAndDateForm codeAndDateForm = null;
            WorkSpace tempWs = workPlaceRepository.findLatestWorkSpace(userId, cur.getId());
            if (tempWs == null) {
                codeAndDateForm = new CodeAndDateForm(cur, null);
            } else {
                codeAndDateForm = new CodeAndDateForm(cur, tempWs.getDate());
            }
            res.add(codeAndDateForm);
        }
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO getFuncCode(VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId) {
        Code code = codeRepository.findCodeByIdAndUserId(vertexVOAndUserIdAndCodeId.getCodeId(), vertexVOAndUserIdAndCodeId.getUserId());
        if (code == null) {
            return ResponseVO.buildFailure("no such user or code");
        }
        if (code.getIs_default() == 1) {
            return getFuncCodeWithVertexVO(vertexVOAndUserIdAndCodeId.getVertexVO(), code.getId(), 1);
        } else {
            return getFuncCodeWithVertexVO(vertexVOAndUserIdAndCodeId.getVertexVO(), code.getId(), 0);

            // return ResponseVO.buildFailure("目前不支持itrust以外的代码分析");
        }
    }

    private ResponseVO getFuncCodeWithVertexVO(VertexVO vertexVO, int codeId, int isDefault) {
        String basicPath = "";
        if (isDefault == 1) {
            basicPath = "src/main/resources/analyzeCode/src/0";
        } else {
            basicPath = "src/main/resources/analyzeCode/src/" + codeId;
        }

        File basicDir = new File(basicPath);
        if (!basicDir.exists()) {
            return ResponseVO.buildFailure("dictionary does not exist");
        }

        String packageStr = vertexVO.getBelongPackage();
        String classStr = vertexVO.getBelongClass();
        String internalClass = "";
        String lineSeparator = System.lineSeparator();
        String classNameBf = "";
        boolean isAbstract = false;


        int tempStrLength = packageStr.length();


        packageStr = packageStr.replaceAll("[.]", "/");


        //----------------------------------------------------
        String packPath = getPackagePath(basicPath, packageStr);
        if (packPath == null) {

            return ResponseVO.buildFailure("dictionary does not exist");
        }
        File packageDir = new File(packPath);
        //----------------------------------------------------


        if (classStr.contains("$")) {
            internalClass = classStr.substring(classStr.indexOf("$") + 1);
            classStr = classStr.substring(0, classStr.indexOf("$"));
        }
        tempStrLength = classStr.length();
        File[] files = packageDir.listFiles();
        File classFile = null;
        if (files == null) {
            return ResponseVO.buildFailure("package dir do not exist");
        }

        for (File curFile : files) {
            if (curFile.isFile()) {
                if (curFile.getName().equals(classStr + ".java")) {
                    System.out.println("is a className");
                    classFile = curFile;
                    break;
                }
            } else if (curFile.isDirectory()) {

            }
        }

        if (classFile == null) {
            return ResponseVO.buildFailure("class file do not exist");
        }


        StringBuffer fileContentBuffer = new StringBuffer("");


        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(classFile);
            br = new BufferedReader(fr);
            String tempStr;
            while ((tempStr = br.readLine()) != null) {
                fileContentBuffer.append(tempStr);
                fileContentBuffer.append(lineSeparator);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("io exception 2");
        }
        String content = fileContentBuffer.toString();
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("close fileReader exception ");
        }
        boolean isInternal = false;

        if (!internalClass.equals("")) {
            //----------------------------todo--------------------------
            System.out.println("internal class");
            isInternal = true;
            int classIndex = content.indexOf(internalClass);
            while (classIndex != -1) {

                int lineStart = content.lastIndexOf("\n", classIndex);
                if (content.substring(lineStart + 1, classIndex).contains("class") ||
                        content.substring(lineStart + 1, classIndex).contains("enum")) {
                    int startBrace = content.indexOf("{", classIndex);
                    int endBrace = getBackBrace(content, startBrace);
                    int classStartLineIndex = content.lastIndexOf("\n", classIndex);
                    content = content.substring(classStartLineIndex, endBrace + 1);
                    classNameBf = classStr;
                    classStr = internalClass;
                    break;
                } else {
                    classIndex = content.indexOf(internalClass, classIndex + 1);

                }
            }
            if (classIndex == -1) {
                return ResponseVO.buildFailure("internal class not found");
            }
        }


        boolean isInit = false;
        String funcName = vertexVO.getFuncName();
        //------------构造函数---------------
        if (funcName.equals("<init>")) {
            funcName = classStr;
            isInit = true;
            System.out.println("init:" + funcName);
        }
        //------------clinit----------------
        if (funcName.equals("<clinit>")) {
            return ResponseVO.buildSuccess("this function is class initialize function, it doesn't have a function body.");
        }


        boolean funcStringExist = false;
        int funcIndex = content.indexOf(funcName);
        int lineIndex = content.lastIndexOf(lineSeparator, funcIndex);
        if (funcIndex == -1) {
            if (funcName.equals(classStr)) {
                return ResponseVO.buildSuccess("do not have an explicit initialize function.");
            }
            return ResponseVO.buildFailure("do not find function name in file");
        }
        String funcLine = content.substring(lineIndex + 1, funcIndex);
        if (funcLine.contains("class") || funcLine.contains("enum")) {
            funcIndex = content.indexOf(funcName, funcIndex + 1);
        }


        char[] validBeforeFuncNameChar = {' ', '\t', '\n', '\r', '.'};


        while (funcIndex != -1) {
            int frontCurvesIndex = content.indexOf("(", funcIndex);

            char indexChar = '\0';
            if (funcIndex > 0) {
                indexChar = content.charAt(funcIndex - 1);
            }
            boolean indexCharValid = false;
            for (char c : validBeforeFuncNameChar) {
                if (indexChar == c) {
                    indexCharValid = true;
                    break;
                }
            }
            if ((!indexCharValid) && indexChar != '\0') {

                funcIndex = content.indexOf(funcName, funcIndex + 1);
                continue;
            }

            if (content.lastIndexOf(funcName, frontCurvesIndex) != funcIndex) {
                funcIndex = content.lastIndexOf(funcName, frontCurvesIndex);
                continue;
            }

            int tempFuncStart = content.lastIndexOf("\n", funcIndex);
            if (content.substring(tempFuncStart, funcIndex).contains("abstract")) {
                isAbstract = true;
            }

            int backCurvesIndex = getBackCurves(content, frontCurvesIndex);
            if (frontCurvesIndex == -1 || backCurvesIndex == -1) {
                return ResponseVO.buildFailure("do not have curves");
            }


            //-----------判断是对目标函数的声明还是调用----------
            int tempSemiIndex = content.indexOf(";", backCurvesIndex);
            int tempBraceIndex = content.indexOf("{", backCurvesIndex);

            if (tempBraceIndex > tempSemiIndex && !isAbstract) {
                System.out.println("调用");
                funcIndex = content.indexOf(funcName, funcIndex + 1);
                continue;
            }
            //------------------------------------------------

            String paramsStr = content.substring(frontCurvesIndex + 1, backCurvesIndex);
            System.out.println("param:" + paramsStr);

            String[] args = paramsStr.split(",");
            String[] vertexArgs = vertexVO.getArgs();
            for (int k = 0; k < vertexArgs.length; k++) {
                int temp = vertexArgs[k].indexOf("$");
                if (temp != -1) {
                    vertexArgs[k] = vertexArgs[k].substring(temp + 1);
                }
            }
            if (isInit && isInternal) {
                String[] temp = new String[vertexArgs.length - 1];
                for (int k = 1; k < vertexArgs.length; k++) {
                    temp[k - 1] = vertexArgs[k];
                }
                vertexArgs = temp;
            }

            if (args.length != vertexArgs.length) {
                funcIndex = content.indexOf(funcName, funcIndex + 1);
                continue;
            }
            boolean equal = true;
            funcStringExist = true;
            //--------------模板函数与模板类处理，不完全，classIndex应该迭代而不是预设class标志出现在文件最前---------
            int lineEndIndex = content.indexOf("\n", funcIndex);
            if (content.substring(funcIndex, lineEndIndex).contains("<")) {
                int start = content.substring(funcIndex, lineEndIndex).indexOf("<");
                int end = getBackAngle(content, start);
                String templateClass = content.substring(start + 1, end);
                System.out.println("templateClass:" + templateClass);
                for (int j = 0; j < vertexArgs.length; j++) {
                    if (vertexArgs[j].equals("java.lang.Object")) {
                        vertexArgs[j] = templateClass;
                    }
                }
            } else {
                int classIndex = content.indexOf(classStr);
                int lineStart = content.lastIndexOf("\n", classIndex);
                int tempEnd = content.indexOf("\n", classIndex);

                if (lineStart != -1) {

                    if ((content.substring(lineStart + 1, classIndex).contains("class") ||
                            content.substring(lineStart + 1, classIndex).contains("enum")) && content.substring(lineStart + 1, tempEnd).contains("<")) {

                        int start = content.indexOf("<", classIndex);

                        int end = getBackAngle(content, start);
                        String templateClass = content.substring(start + 1, end);
                        System.out.println("templateClass1:" + templateClass);
                        for (int j = 0; j < vertexArgs.length; j++) {
                            if (vertexArgs[j].equals("java.lang.Object")) {
                                vertexArgs[j] = templateClass;
                            }
                        }
                    }
                } else {
                    if ((content.substring(0, classIndex).contains("class") ||
                            content.substring(lineStart + 1, classIndex).contains("enum")) && content.indexOf("<", classIndex) != -1) {

                        int start = content.indexOf("<", classIndex);
                        int end = getBackAngle(content, start);
                        String templateClass = content.substring(start + 1, end);
                        System.out.println("templateClass2:" + templateClass);
                        for (int j = 0; j < vertexArgs.length; j++) {
                            if (vertexArgs[j].equals("java.lang.Object")) {
                                vertexArgs[j] = templateClass;
                            }
                        }
                    }
                }
            }

            //-------------------------------------------------------------------

            for (int i = 0; i < args.length; i++) {
                String arg = args[i].trim();
                System.out.println("arg:" + arg);
                String vertexArg = vertexArgs[i];

                String argKind = arg.split("[ \t\n]")[0];
                String vertexArgKind = vertexArg.substring(vertexArg.lastIndexOf(".") + 1);
                if (argKind.contains(".")) {
                    vertexArgKind = vertexArg;
                }


//                    if(arg.split("[ \t\n]")[0].equals("T")){
//
//                        if(!vertexArg.substring(vertexArg.lastIndexOf(".")+1).equals("Object")){
//                            funcIndex = content.indexOf(funcName,funcIndex+1);
//                            equal = false;
//                            break;
//                        }
//                    }else
                if (!argKind.equals(vertexArgKind)) {
                    System.out.println("withoutSpace:" + arg.split("[ \t\n]")[0]);
                    System.out.println("vertexArg:" + vertexArg.substring(vertexArg.lastIndexOf(".") + 1));
                    funcIndex = content.indexOf(funcName, funcIndex + 1);
                    equal = false;
                    break;
                }


            }

            if (equal) {
                break;
            }

        }

        if (funcIndex == -1) {
            if (funcStringExist) {
                return ResponseVO.buildFailure("doesn't have explicit declaration. It maybe a parent class function or library function.");
            }
            return ResponseVO.buildFailure("match args do not exist ");
        }
        String funcBody = getFuncBody(content, funcIndex, isAbstract);

        return ResponseVO.buildSuccess(funcBody);


        //return ResponseVO.buildFailure("test");
    }

    private String getPackagePath(String basicPath, String packagePath) {
        String fullPath = basicPath + "/" + packagePath;
        System.out.println("full:" + fullPath);
        if (new File(fullPath).exists()) {
            return fullPath;
        } else {
            File[] files = new File(basicPath).listFiles();
            if (files == null || files.length == 0) {
                return null;
            }
            for (File cur : files) {
                System.out.println(cur.getName());
                basicPath = cur.getAbsolutePath();
                String temp = getPackagePath(basicPath, packagePath);
                if (temp != null) {
                    return temp;
                }
            }
            return null;
        }


    }

    private String getFuncBody(String content, int funcIndex, boolean isAbstract) {
        String lineSeparator = System.lineSeparator();
        if (isAbstract) {
            int end = content.indexOf(";", funcIndex);
            int start = content.lastIndexOf(lineSeparator, funcIndex);
            return content.substring(start + 1, end + 1);
        } else {
            int frontCurvesIndex = content.indexOf("(", funcIndex);
            int backCurvesIndex = getBackCurves(content, frontCurvesIndex);
            int frontBraceIndex = content.indexOf("{", backCurvesIndex + 1);
            int backBraceIndex = getBackBrace(content, frontBraceIndex);

            int start = content.lastIndexOf(lineSeparator, funcIndex);
            return content.substring(start + 1, backBraceIndex + 1);
        }
    }

    private int getBackCurves(String content, int frontCurvesIndex) {
        int frontNum = 1;
        int backNum = 0;
        int contentLen = content.length();
        for (int i = frontCurvesIndex + 1; i < contentLen; i++) {
            if (content.charAt(i) == '(') {
                frontNum++;
            }
            if (content.charAt(i) == ')') {
                backNum++;
            }
            if (frontNum == backNum) {
                return i;
            }
        }
        return -1;
    }

    private int getBackAngle(String content, int frontAngleIndex) {
        int frontNum = 1;
        int backNum = 0;
        int contentLen = content.length();
        for (int i = frontAngleIndex + 1; i < contentLen; i++) {
            if (content.charAt(i) == '<') {
                frontNum++;
            }
            if (content.charAt(i) == '>') {
                backNum++;
            }
            if (frontNum == backNum) {
                return i;
            }
        }
        return -1;
    }

    private int getBackBrace(String content, int frontBraceIndex) {
        int frontNum = 1;
        int backNum = 0;
        int contentLen = content.length();
        for (int i = frontBraceIndex + 1; i < contentLen; i++) {
            if (content.charAt(i) == '{') {
                frontNum++;
            }
            if (content.charAt(i) == '}') {
                backNum++;
            }
            if (frontNum == backNum) {
                return i;
            }
        }
        return -1;
    }

    public ResponseVO getCodeStructure(UserAndCodeForm userAndCodeForm) {
        System.out.println("user:" + graphCalculate.curUserId);
        System.out.println("code:" + graphCalculate.curCodeId);
        ArrayList<Vertex> vertices = graphCalculate.getAllVertexes();
        if (userAndCodeForm.getCodeId() != graphCalculate.curCodeId || userAndCodeForm.getUserId() != graphCalculate.curUserId) {
            ResponseVO responseVO = graphCalculate.getGraph(userAndCodeForm);
            if (!responseVO.isSuccess()) {
                return responseVO;
            }
            vertices = graphCalculate.allVertexes;
        }

        Code code = codeRepository.findCodeById(userAndCodeForm.getCodeId());
        int tempId = 0;
        if (code.getIs_default() != 1) {
            tempId = code.getId();
        }

        String basicPath = "src/main/resources/analyzeCode/src/" + tempId;
        String tempbasicPath = basicPath;
        System.out.println();

        File basic = new File(basicPath);
//        Resource resource = new ClassPathResource(basicPath);
//        try {
//            basic = resource.getFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseVO.buildFailure("nop");
//        }
        if (!basic.exists()) {
            return ResponseVO.buildFailure("basic path does not exist");
        }


        File[] basicFiles = basic.listFiles();
        if (basicFiles == null) {
            return ResponseVO.buildFailure("dictionary error");
        }
        //---------目前只支持一份源码有一个最外层父文件夹------
        String rootPath = basicFiles[0].getName();
        //-------------------------------------------------
        basicPath = basicPath + "/" + rootPath;
        CodeNode codeNode = new CodeNode(rootPath);
//        resource = new ClassPathResource(basicPath);
//        File f = null;
//        try {
//            f = resource.getFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseVO.buildFailure("nop");
//        }
        File f = new File(basicPath);

        if (!basic.exists()) {
            return ResponseVO.buildFailure("basic child path does not exist");
        }

        File[] files = f.listFiles();
        if (files == null) {
            return ResponseVO.buildFailure("empty dir");
        }

        for (File file : files) {
            CodeNode child = createChildNode(rootPath, file, vertices, tempbasicPath);
            if (child != null)
                codeNode.addChild(child);
        }
        if (codeNode.getNodes().size() == 0) {
            codeNode.setNodes(null);
        }

        return ResponseVO.buildSuccess(codeNode);
    }

    private CodeNode createChildNode(String fatherPath, File file, ArrayList<Vertex> vertices, String basicPath) {

        String name = file.getName();
        CodeNode codeNode = new CodeNode(name);
        String path = fatherPath + "/" + name;
        //System.out.println("shit:"+path);
        if (name.length() > 5 && name.substring(name.length() - 5).equals(".java")) {
            codeNode.setText(name.substring(0, name.length() - 5));
            ArrayList<CodeNode> res = createJavaChild(path.substring(0, path.length() - 5), vertices);
            codeNode.setNodes(res);
            //System.out.println(res.size());
            return codeNode;
        } else {
            File f = new File(basicPath + "/" + path);

            if (!f.exists()) {
                return null;
            }
            File[] files = f.listFiles();
            if (files == null) {
                return null;
            }

            for (File file1 : files) {
                CodeNode child = createChildNode(path, file1, vertices, basicPath);
                if (child != null)
                    codeNode.addChild(child);

            }
            return codeNode;
        }
    }

    private ArrayList<CodeNode> createJavaChild(String path, ArrayList<Vertex> vertices) {
        HashSet<String> innerClass = new HashSet<String>();
        //System.out.println(path);
        ArrayList<CodeNode> codeNodes = new ArrayList<CodeNode>();
        String pkgName = path.substring(0, path.lastIndexOf("/"));
        String className = path.substring(path.lastIndexOf("/") + 1);
        CodeNode innerClassNode = null;
        for (Vertex vertex : vertices) {
            String vertexPkgName = vertex.getBelongPackage();
            vertexPkgName = vertexPkgName.replace('.', '/');
            //System.out.println(vertexPkgName);

            String vertexClassName = vertex.getBelongClass();

            if (pkgName.contains(vertexPkgName)) {
                if (vertexClassName.equals(className)) {
                    String funcName = vertex.getFuncName();
                    if (funcName.equals("<init>"))
                        funcName = "&lt;init&gt;";
                    if (funcName.equals("<clinit>"))
                        funcName = "&lt;clinit&gt;";
                    CodeNode funcNode = new CodeNode(funcName + "(" + vertex.getArgsString(vertex.getArgs()) + ")");
                    funcNode.setVertexId(vertex.getId());
                    codeNodes.add(funcNode);
                } else if ((vertexClassName.length() > className.length()) && ((vertexClassName.substring(0, className.length() + 1)).equals(className + "$"))) {
                    //System.out.println(vertexClassName);
                    if (!innerClass.contains(vertexClassName)) {
                        innerClassNode = new CodeNode(vertexClassName.substring(vertexClassName.indexOf("$") + 1));
                        ArrayList<CodeNode> res = createInnerChild(path + "/" + vertexClassName.substring(vertexClassName.indexOf("$") + 1), vertices);
                        innerClassNode.setNodes(res);
                        if (innerClassNode.getText().equals("1"))
                            innerClassNode.setText("InnerHiddenClass");
                        codeNodes.add(innerClassNode);
                        innerClass.add(vertexClassName);
                    }
                }
            }
        }
        return codeNodes;
    }

    private ArrayList<CodeNode> createInnerChild(String path, ArrayList<Vertex> vertices) {
        ArrayList<CodeNode> codeNodes = new ArrayList<CodeNode>();
        String innerClassName = path.substring(path.lastIndexOf("/") + 1);
        path = path.substring(0, path.lastIndexOf("/"));
        String className = path.substring(path.lastIndexOf("/") + 1);
        String pkgName = path.substring(0, path.lastIndexOf("/"));

        for (Vertex vertex : vertices) {
            String vertexPkgName = vertex.getBelongPackage();
            vertexPkgName = vertexPkgName.replace('.', '/');
            String vertexClassName = vertex.getBelongClass();
            if (vertexPkgName.equals(pkgName) && vertexClassName.equals(className + "$" + innerClassName)) {
                String funcName = vertex.getFuncName();
                if (funcName.equals("<init>"))
                    funcName = "&lt;init&gt;";
                if (funcName.equals("<clinit>"))
                    funcName = "&lt;clinit&gt;";
                CodeNode funcNode = new CodeNode(funcName + "(" + vertex.getArgsString(vertex.getArgs()) + ")");
                funcNode.setVertexId(vertex.getId());
                codeNodes.add(funcNode);
            }
        }
        return codeNodes;
    }

    private boolean deleteFile(File file) {
        boolean res = true;
        System.out.println(file.getAbsolutePath());
        if (file.isFile()) {
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                System.out.println("删除文件失败");
                return false;
            }
            return true;
        }
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("list file fail");
            return false;
        }
        for (File cur : files) {
            if (cur.isDirectory()) {
                res = res & deleteFile(cur);
            } else {
                res = res & deleteFile(cur);
            }
        }
        res = res & file.delete();
        return res;
    }
}
