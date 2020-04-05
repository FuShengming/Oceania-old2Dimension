package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.CodeBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.vo.CodeVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.VertexVO;

import com.old2dimension.OCEANIA.vo.VertexVOAndUserIdAndCodeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CodeBLImpl implements CodeBL {

    @Autowired
    CodeRepository codeRepository;

    public ResponseVO getCodesByUserId(int userId){
        List<Code> dbRes = codeRepository.findCodesByUserId(userId);
        if(dbRes == null){
            return ResponseVO.buildFailure("that user doesn't have any codes");
        }
        List<CodeVO> res = new ArrayList<CodeVO>();
        for(Code cur : dbRes){
            res.add(new CodeVO(cur));
        }
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO getFuncCode(VertexVOAndUserIdAndCodeId vertexVOAndUserIdAndCodeId) {
        Code code = codeRepository.findCodeByIdAndUserId(vertexVOAndUserIdAndCodeId.getCodeId(),vertexVOAndUserIdAndCodeId.getUserId());
        if(code == null){
            return ResponseVO.buildFailure("no such user or code");
        }
        if(code.getIs_default()==1){
        return getFuncCodeWithVertexVO(vertexVOAndUserIdAndCodeId.getVertexVO());
        }
        else{
            return ResponseVO.buildFailure("目前不支持itrust以外的代码分析");
        }
    }

    private ResponseVO getFuncCodeWithVertexVO(VertexVO vertexVO){
        String basicPath = "analyzeCode/src/";
        String packageStr = vertexVO.getBelongPackage();
        String classStr = vertexVO.getBelongClass();
        String internalClass="";
        StringBuilder sb = new StringBuilder(basicPath);
        String lineSeparator = System.lineSeparator();
        String classNameBf = "";
        int tempStrLength = packageStr.length();
        for(int i = 0; i < tempStrLength; i++){
            char curChar = packageStr.charAt(i);
            if(curChar != '.'){
                sb.append(curChar);
            }
            else{
                sb.append('/');
            }
        }
        basicPath = sb.toString();
        Resource resource = new ClassPathResource(basicPath);
        File packageDir = new File("");
        try {
            packageDir = resource.getFile();
        }
        catch (IOException e){
            e.printStackTrace();
            return ResponseVO.buildFailure("finding file fail");
        }

        if(classStr.contains("$")){
            internalClass = classStr.substring(classStr.indexOf("$")+1);
            classStr = classStr.substring(0,classStr.indexOf("$"));
        }
        tempStrLength = classStr.length();
       File[] files = packageDir.listFiles();
       File classFile = null;
        if(files == null){
            return ResponseVO.buildFailure("package dir do not exist");
        }

        for (File curFile : files) {
            if(curFile.isFile()&&curFile.getName().equals(classStr+".java")){
                System.out.println("is a className");
                classFile = curFile;
                break;
            }
        }

        if(classFile == null){
            return ResponseVO.buildFailure("class file do not exist");
        }


        StringBuffer fileContentBuffer = new StringBuffer("");


        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(classFile));
            String tempStr;
            while ((tempStr = br.readLine()) != null) {
                fileContentBuffer.append(tempStr);
                fileContentBuffer.append(lineSeparator);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return ResponseVO.buildFailure("io exception 2");
        }
        String content = fileContentBuffer.toString();

        boolean isInternal = false;

        if(!internalClass.equals("")){
            //----------------------------todo--------------------------
            System.out.println("internal class");
            isInternal = true;
            int classIndex = content.indexOf(internalClass);
            while (classIndex != -1){

            int lineStart = content.lastIndexOf("\n",classIndex);
            if(content.substring(lineStart+1,classIndex).contains("class")||
                    content.substring(lineStart+1,classIndex).contains("enum")){
                int startBrace = content.indexOf("{",classIndex);
                int endBrace = getBackBrace(content,startBrace);
                int classStartLineIndex = content.lastIndexOf("\n",classIndex);
                content = content.substring(classStartLineIndex,endBrace+1);
                classNameBf = classStr;
                classStr = internalClass;
                System.out.println("internal class:\n"+content);
                break;
            }
            else{
                classIndex = content.indexOf(internalClass,classIndex+1);

            }
            }
            if(classIndex == -1){
                return ResponseVO.buildFailure("internal class not found");
            }
        }


            boolean isInit = false;
            String funcName = vertexVO.getFuncName();
            //------------构造函数---------------
            if(funcName.equals("<init>")){
                funcName = classStr;
                isInit = true;
                System.out.println("init:"+funcName);
            }
            //------------clinit----------------
            if(funcName.equals("<clinit>")){
                return ResponseVO.buildSuccess("this function is class initialize function, it doesn't have a function body.");
            }

            //------------纯接口-------------



            int funcIndex = content.indexOf(funcName);
            int lineIndex = content.lastIndexOf(lineSeparator,funcIndex);
            String funcLine= content.substring(lineIndex+1,funcIndex);
            if(funcLine.contains("class")||funcLine.contains("enum")){
                funcIndex = content.indexOf(funcName,funcIndex+1);
            }

            if(funcIndex==-1){
                if(funcName.equals(classStr)){return ResponseVO.buildSuccess("do not have an explicit initialize function.");}
                return ResponseVO.buildFailure("do not find function name in file");
            }

            while(funcIndex != -1){
                int frontCurvesIndex = content.indexOf("(",funcIndex);

                char indexChar = '\0';
                if(funcIndex>0){
                   indexChar = content.charAt(funcIndex-1);
                }
                if((!(indexChar == ' '|| indexChar == '\t' || indexChar == '\n' || indexChar == '\r'))&&indexChar!='\0'){
                    System.out.println("ass1");
                    funcIndex = content.indexOf(funcName,funcIndex+1);
                    continue;
                }

                if(content.lastIndexOf(funcName,frontCurvesIndex) != funcIndex){
                    System.out.println("ass2");
                    funcIndex=content.lastIndexOf(funcName,frontCurvesIndex);
                    continue;
                }

                int backCurvesIndex = getBackCurves(content,frontCurvesIndex);
                if(frontCurvesIndex == -1 || backCurvesIndex == -1){return ResponseVO.buildFailure("do not have curves");}


                //-----------判断是对目标函数的声明还是调用----------
                int tempSemiIndex = content.indexOf(";",backCurvesIndex);
                int tempBraceIndex = content.indexOf("{",backCurvesIndex);
                if(tempBraceIndex>tempSemiIndex){
                    System.out.println("调用");
                    funcIndex = content.indexOf(funcName,funcIndex+1);
                    continue;
                }
                //------------------------------------------------


                String paramsStr = content.substring(frontCurvesIndex+1,backCurvesIndex);
                System.out.println("param:"+paramsStr);

                String[] args = paramsStr.split(",");
                String[] vertexArgs = vertexVO.getArgs();


                if(isInit&&isInternal){
                    String [] temp = new String[vertexArgs.length-1];
                    for(int  k = 1;k<vertexArgs.length;k++){
                        temp[k-1]=vertexArgs[k];
                    }
                    vertexArgs =temp;



                }
                if(args.length != vertexArgs.length){funcIndex = content.indexOf(funcName,funcIndex+1); continue;}
                boolean equal = true;

                //--------------模板函数与模板类处理，不完全，classIndex应该迭代而不是预设class标志出现在文件最前---------
                int lineEndIndex = content.indexOf("\n",funcIndex);
                if(content.substring(funcIndex,lineEndIndex).contains("<")){
                    int start = content.substring(funcIndex,lineEndIndex).indexOf("<");
                    int end  = getBackAngle(content,start);
                    String templateClass = content.substring(start+1,end);
                    System.out.println("templateClass:"+templateClass);
                    for(int j =0; j<vertexArgs.length;j++){
                       if(vertexArgs[j].equals("java.lang.Object")){
                           vertexArgs[j]=templateClass;
                       }
                    }
                }
                else {
                    int classIndex = content.indexOf(classStr);
                    int lineStart = content.lastIndexOf("\n",classIndex);
                    if(lineStart!=-1){
                        if((content.substring(lineStart+1,classIndex).contains("class")||
                                content.substring(lineStart+1,classIndex).contains("enum"))&&content.indexOf("<",classIndex)!=-1){

                            int start = content.indexOf("<",classIndex);

                            int end = getBackAngle(content,start);
                            String templateClass = content.substring(start+1,end);
                            System.out.println("templateClass1:"+templateClass);
                            for(int j =0; j<vertexArgs.length;j++){
                                if(vertexArgs[j].equals("java.lang.Object")){
                                    vertexArgs[j]=templateClass;
                                }
                            }
                        }
                    }
                    else{
                        if((content.substring(0,classIndex).contains("class")||
                                content.substring(lineStart+1,classIndex).contains("enum"))&&content.indexOf("<",classIndex)!=-1){

                            int start = content.indexOf("<",classIndex);
                            int end = getBackAngle(content,start);
                            String templateClass = content.substring(start+1,end);
                            System.out.println("templateClass2:"+templateClass);
                            for(int j =0; j<vertexArgs.length;j++){
                                if(vertexArgs[j].equals("java.lang.Object")){
                                    vertexArgs[j]=templateClass;
                                }
                            }
                        }
                    }
                }

                //-------------------------------------------------------------------

                for(int i = 0; i<args.length;i++){
                    String arg = args[i].trim();
                    System.out.println("arg:"+arg);
                    String vertexArg = vertexArgs[i];

                    String argKind = arg.split("[ \t\n]")[0];
                    String vertexArgKind = vertexArg.substring(vertexArg.lastIndexOf(".")+1);
                    if(argKind.contains(".")){
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
                   if(!argKind.equals(vertexArgKind)){
                       System.out.println("withoutSpace:"+arg.split("[ \t\n]")[0]);
                       System.out.println("vertexArg:"+vertexArg.substring(vertexArg.lastIndexOf(".")+1));
                        funcIndex = content.indexOf(funcName,funcIndex+1);
                        equal = false;
                        break;
                    }


                }

                if(equal){
                    break;
                }

            }

            if(funcIndex == -1){

                return ResponseVO.buildFailure("match args do not exist ");}
            String funcBody = getFuncBody(content,funcIndex);
            System.out.println(funcBody);
            return ResponseVO.buildSuccess(funcBody);


        //return ResponseVO.buildFailure("test");
    }

    private String getFuncBody(String content, int funcIndex){
        int frontCurvesIndex = content.indexOf("(",funcIndex);
        int backCurvesIndex = getBackCurves(content,frontCurvesIndex);
        int frontBraceIndex = content.indexOf("{",backCurvesIndex+1);
        int backBraceIndex = getBackBrace(content,frontBraceIndex);
        String lineSeparator = System.lineSeparator();
        int start = content.lastIndexOf(lineSeparator,funcIndex);
        return content.substring(start+1,backBraceIndex+1);
    }

    private int getBackCurves(String content,int frontCurvesIndex){
        int frontNum = 1;
        int backNum = 0;
        int contentLen = content.length();
        for(int i = frontCurvesIndex+1;i<contentLen;i++){
            if(content.charAt(i)=='('){frontNum++;}
            if(content.charAt(i)==')'){backNum++;}
            if(frontNum == backNum){return i;}
        }
        return -1;
    }
    private int getBackAngle(String content,int frontAngleIndex){
        int frontNum = 1;
        int backNum = 0;
        int contentLen = content.length();
        for(int i = frontAngleIndex+1;i<contentLen;i++){
            if(content.charAt(i)=='<'){frontNum++;}
            if(content.charAt(i)=='>'){backNum++;}
            if(frontNum == backNum){return i;}
        }
        return -1;
    }

    private int getBackBrace(String content,int frontBraceIndex){
        int frontNum = 1;
        int backNum = 0;
        int contentLen = content.length();
        for(int i = frontBraceIndex+1;i<contentLen;i++){
            if(content.charAt(i)=='{'){frontNum++;}
            if(content.charAt(i)=='}'){backNum++;}
            if(frontNum == backNum){return i;}
        }
        return -1;
    }
}
