package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.UploadBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import gr.gousiosg.javacg.stat.JCallGraph;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
@Component
public class UploadBLImpl implements UploadBL {
    @Autowired
    CodeRepository codeRepository;
    public ResponseVO uploadCode(int userId, MultipartFile[] files) throws IOException  {
        if (files == null) return  ResponseVO.buildFailure("NULL");
        if (files.length == 0) return  ResponseVO.buildFailure("EMPTY");

        Code code = new Code();
        code.setName("name");
        code.setIs_default(0);
        code.setUserId(userId);
        Code savedCode = codeRepository.save(code);

        for (MultipartFile multipartFile : files) {

            //--------------创建目录和文件-------------
            String fileFullName = multipartFile.getOriginalFilename();
            if(fileFullName==null){
                return ResponseVO.buildFailure("file error");
            }
            String dirName = "src/main/resources/analyzeCode/src/"+ (savedCode.getId()+"") + "/"+fileFullName.substring(0,fileFullName.lastIndexOf("/"));
            String fileName = fileFullName.substring(fileFullName.lastIndexOf("/")+1);
            fileFullName = dirName +"/"+ fileName;
            File dir = new File(dirName);
            if(!dir.exists()){
                boolean isSuccess = dir.mkdirs();
                if(!isSuccess){
                    System.out.println(dirName);
                    return ResponseVO.buildFailure("mkdirs fails");}
            }

            File javaFile = new File(fileFullName);
            if(!javaFile.exists()){

                boolean isSuccess = javaFile.createNewFile();
                if(!isSuccess){return ResponseVO.buildFailure("create file fails");}
            }
            //----------------------------------
            String content = new BufferedReader(new InputStreamReader(multipartFile.getInputStream())).lines().collect(Collectors.joining(System.lineSeparator()));
            FileOutputStream out = new FileOutputStream(javaFile);
            out.write(content.getBytes());
        }

        return  ResponseVO.buildSuccess(savedCode);
    }


    public ResponseVO analyzeJar(int codeId) throws IOException{


        File basicDir = new File("src/main/resources/analyzeCode/src/"+codeId);
        if(!basicDir.exists()){
            return ResponseVO.buildFailure("dictionary does not exist,please upload code");
        }
        ArrayList<String> packageStrings = getPackages(basicDir);

        Code code = codeRepository.findCodeById(codeId);
        if(code == null){
            return ResponseVO.buildFailure("no such code");
        }
        String jarPath = "src/main/resources/jars";
        if(code.getIs_default() == 1){
            jarPath += "/0.jar";
        }
        else{
            jarPath += "/"+codeId+".jar";
        }
        String [] args = {jarPath};

        File dependencies = new File("src/main/resources/dependencies/"+codeId+".txt");

        if(dependencies.exists()){
            boolean isSuccess = dependencies.delete();
        }
        boolean isSuccess= dependencies.createNewFile();

        PrintStream psOld = System.out; // 保存原来的输出路径
        System.setOut(new PrintStream(dependencies));// 设置输出重新定向到文件
        JCallGraph.main(args);
        System.setOut(psOld);
        filterDependencies("src/main/resources/dependencies/"+codeId+".txt",packageStrings);

        return ResponseVO.buildSuccess();
    }

    private void filterDependencies(String fileName,ArrayList<String> packageNames) throws IOException{
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("文件不存在");
            return ;
        }

        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer stringBuffer=new StringBuffer();
        temp=br.readLine();

        while(temp!=null){

            if(temp.substring(0,2).equals("C:")){
                temp="";
            }
            String[] vs = temp.split(" ");

            for(String v : vs){
                boolean valid = false;
                for(String packageName : packageNames){
                    if(v.contains(packageName+".")){
                        valid = true;
                        break;
                    }
                }
                if(!valid){
                    temp="";
                    break;
                }
            }

            if(!temp.equals("")){
                stringBuffer.append(temp);
                stringBuffer.append("\n");
            }

            temp=br.readLine();

        }

        file.delete();
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write(stringBuffer.toString().getBytes());


    }

    private ArrayList<String> getPackages(File file){
        ArrayList<String> res = new ArrayList<String>();

            File[] files = file.listFiles();
            if(files == null || files.length==0){
                return res;
            }
            for(File cur : files){
                if(cur.isDirectory()){
                    ArrayList<String> temp = getPackages(cur);
                    for(String str : temp){
                        res.add(str);
                    }
                }
                else if(cur.isFile()){
                    String curName = cur.getName();
                    if(curName.substring(curName.length()-5,curName.length()).equals(".java")){
                        BufferedReader br = null;
                        try{
                            br = new BufferedReader(new FileReader(cur));
                            String tempStr;
                            while ((tempStr = br.readLine()) != null) {
                               if(tempStr.contains("package")){
                                   String packageString = tempStr.split("package")[1];
                                   packageString = packageString.substring(0,packageString.length()-1);
                                   packageString = packageString.trim();
                                   res.add(packageString);
                                   break;
                               }
                            }
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        return res;
    }

}
