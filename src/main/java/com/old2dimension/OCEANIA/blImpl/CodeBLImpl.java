package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.CodeBL;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.VertexVO;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CodeBLImpl implements CodeBL {
    public ResponseVO getFuncCode(VertexVO vertexVO){
        String basicPath = "analyzeCode/src/";
        String packageStr = vertexVO.getBelongPackage();
        String classStr = vertexVO.getBelongClass();
        String internalClass="";
        StringBuilder sb = new StringBuilder(basicPath);
        String lineSeparator = System.lineSeparator();
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
        if(!internalClass.equals("")){System.out.println("internal class");}
        else{
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
            String funcName = vertexVO.getFuncName();
            int funcIndex = content.indexOf(funcName);
            if(funcIndex==-1){return ResponseVO.buildFailure("do not find funName in file");}

            while(funcIndex != -1){
                int frontCurvesIndex = content.indexOf("(",funcIndex);
                int backCurvesIndex = getBackCurves(content,frontCurvesIndex);
                if(frontCurvesIndex == -1 || backCurvesIndex == -1){return ResponseVO.buildFailure("do not have curves");}

                String paramsStr = content.substring(frontCurvesIndex+1,backCurvesIndex);
                System.out.println(packageStr);

                String[] args = paramsStr.split(",");
                String[] vertexArgs = vertexVO.getArgs();
                if(args.length != vertexArgs.length){funcIndex = content.indexOf(funcName,funcIndex+1); continue;}
                boolean equal = true;
                for(int i = 0; i<args.length;i++){
                    String arg = args[i].trim();
                    System.out.println(arg);
                    String vertexArg = vertexArgs[i];

                    if(arg.split("[ \t]")[0].equals("T")){

                        if(!vertexArg.substring(vertexArg.lastIndexOf(".")+1).equals("Object")){
                            funcIndex = content.indexOf(funcName,funcIndex+1);
                            equal = false;
                            break;
                        }
                    }

                   else if(!arg.split("[ \t]")[0].equals(vertexArg.substring(vertexArg.lastIndexOf(".")+1))){
                        funcIndex = content.indexOf(funcName,funcIndex+1);
                        equal = false;
                        break;
                    }


                }

                if(equal){
                    break;
                }

            }

            if(funcIndex == -1){return ResponseVO.buildFailure("match args do not exist ");}
            String funcBody = getFuncBody(content,funcIndex);
            System.out.println(funcBody);
            return ResponseVO.buildSuccess(funcBody);
        }







        return ResponseVO.buildFailure("test");
    }

    private String getFuncBody(String content, int funcIndex){
        int frontCurvesIndex = content.indexOf("(",funcIndex);
        int backCurvesIndex = getBackCurves(content,frontCurvesIndex);
        int frontBraceIndex = content.indexOf("{",backCurvesIndex+1);
        int backBraceIndex = getBackBrace(content,frontBraceIndex);

        return content.substring(funcIndex,backBraceIndex+1);
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
