package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.GroupCodeBL;
import com.old2dimension.OCEANIA.bl.UploadBL;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.dao.GroupCodeRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.GroupCode;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UploadConfirmForm;
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
    @Autowired
    GroupCodeRepository groupCodeRepository;

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public ResponseVO uploadCode(String uuid, MultipartFile[] files) {
        System.out.println("uuid:" + uuid);
        if (files == null) return ResponseVO.buildFailure("NULL");
        if (files.length == 0) return ResponseVO.buildFailure("EMPTY");


        for (MultipartFile multipartFile : files) {

            //--------------创建目录和文件-------------
            String fileFullName = multipartFile.getOriginalFilename();
            if (fileFullName == null) {
                return ResponseVO.buildFailure("file error");
            }
            String dirName = "src/main/resources/analyzeCode/src/" + uuid + "/" + fileFullName.substring(0, fileFullName.lastIndexOf("/"));
            String fileName = fileFullName.substring(fileFullName.lastIndexOf("/") + 1);
            fileFullName = dirName + "/" + fileName;
            File dir = new File(dirName);
            if (!dir.exists()) {
                boolean isSuccess = dir.mkdirs();
                if (!isSuccess) {
                    System.out.println(dirName + " mkdir fail");
                    return ResponseVO.buildFailure("mkdirs fails");
                }
            }

            File javaFile = new File(fileFullName);
            if (!javaFile.exists()) {
                boolean isSuccess = true;
                try {
                    isSuccess = javaFile.createNewFile();
                    if (!isSuccess) {
                        deleteFile(new File("src/main/resources/analyzeCode/src/" + uuid));
                        return ResponseVO.buildFailure("create file fails");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    deleteFile(new File("src/main/resources/analyzeCode/src/" + uuid));
                    return ResponseVO.buildFailure("create file exception");
                }
            } else {
                System.out.println("java file has existed");
            }
            //----------------------------------
            try {
                InputStreamReader isr = new InputStreamReader(multipartFile.getInputStream());
                String content = new BufferedReader(isr).lines().collect(Collectors.joining(System.lineSeparator()));
                isr.close();
                FileOutputStream out = new FileOutputStream(javaFile);
                out.write(content.getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                deleteFile(new File("src/main/resources/analyzeCode/src/" + uuid));
                return ResponseVO.buildFailure("write file error");
            }
        }

        return ResponseVO.buildSuccess("files upload successfully");
    }

    public ResponseVO uploadJar(String uuid, MultipartFile file) {
        System.out.println("jar:uuid:" + uuid);
        if (file == null) return ResponseVO.buildFailure("NULL");


        //--------------创建目录和文件-------------
        String fileFullName = file.getOriginalFilename();
        if (fileFullName == null) {
            return ResponseVO.buildFailure("file error");
        }
        String dirName = "src/main/resources/jars";
        String fileName = uuid + ".jar";
        fileFullName = dirName + "/" + fileName;
        File dir = new File(dirName);
        if (!dir.exists()) {
            boolean isSuccess = dir.mkdirs();
            if (!isSuccess) {
                System.out.println(dirName);
                return ResponseVO.buildFailure("mkdirs fails");
            }
        }

        File jarFile = new File(fileFullName);
        if (!jarFile.exists()) {
            try {
                boolean isSuccess = jarFile.createNewFile();
                if (!isSuccess) {
                    System.out.println("create file fails");
                    deleteFile(jarFile);
                    return ResponseVO.buildFailure("create file fails");
                }
            } catch (IOException e) {
                e.printStackTrace();
                deleteFile(jarFile);
                return ResponseVO.buildFailure("create file error");
            }

        }

        //System.out.println("ass:"+jarFile.getAbsolutePath());
        //----------------------------------
        try {
            byte[] content = file.getBytes();
            FileOutputStream out = new FileOutputStream(jarFile);
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            deleteFile(jarFile);
            return ResponseVO.buildFailure("write jar file exception");
        }

        return ResponseVO.buildSuccess("upload jar successfully");
    }

    private ResponseVO uploadConfirm(UploadConfirmForm uploadConfirm) {
        File javaDir = new File("src/main/resources/analyzeCode/src/" + uploadConfirm.getUuid());
        if (!javaDir.exists()) {
            System.out.println("path does not exist:" + javaDir.getAbsolutePath());
            return ResponseVO.buildFailure("can not find java files");
        }
        File jarFile = new File("src/main/resources/jars/" + uploadConfirm.getUuid() + ".jar");
        if (!jarFile.exists()) {
            return ResponseVO.buildFailure("can not find jar file");
        }


        Code code = new Code();
        code.setName(uploadConfirm.getName());
        code.setUserId(uploadConfirm.getUserId());
        code.setIs_default(0);
        code = codeRepository.save(code);

        File renameJavaDir = new File("src/main/resources/analyzeCode/src/" + code.getId());
        File renameJarFile = new File("src/main/resources/jars/" + code.getId() + ".jar");
        boolean isSuccess = javaDir.renameTo(renameJavaDir);
        if (!isSuccess) {
            System.out.println("java files rename fail");
            codeRepository.delete(code);
            return ResponseVO.buildFailure("java files rename fail");
        }
        isSuccess = jarFile.renameTo(renameJarFile);
        if (!isSuccess) {
            System.out.println("jar file rename fail");
            codeRepository.delete(code);
            return ResponseVO.buildFailure("java files rename fail");
        }
        return ResponseVO.buildSuccess(code);
    }

    @Override
    public ResponseVO groupAnalyzeJar(int groupId, String uuid) {
        ResponseVO res = analyzeJar(1,uuid);
        if(!res.isSuccess()){
            return res;
        }
        Code code = (Code)res.getContent();
        GroupCode groupCode = new GroupCode(groupId,code.getId());
        groupCode = groupCodeRepository.save(groupCode);
        if(groupCode.getId()==0){
            return ResponseVO.buildFailure("save group code data failed.");
        }
        return ResponseVO.buildSuccess(groupCode);
    }

    public ResponseVO analyzeJar(int userId, String uuid) {
        UploadConfirmForm uploadConfirmForm = new UploadConfirmForm();
        uploadConfirmForm.setName(uuid);
        uploadConfirmForm.setUserId(userId);
        uploadConfirmForm.setUuid(uuid);
        ResponseVO responseVO = uploadConfirm(uploadConfirmForm);
        if (!responseVO.isSuccess()) {
            return responseVO;
        }
        int codeId = ((Code) responseVO.getContent()).getId();
        File basicDir = new File("src/main/resources/analyzeCode/src/" + codeId);
        if (!basicDir.exists()) {
            return ResponseVO.buildFailure("dictionary does not exist,please upload code");
        }
        ArrayList<String> packageStrings = getPackages(basicDir);

        Code code = codeRepository.findCodeById(codeId);
        if (code == null) {
            return ResponseVO.buildFailure("no such code");
        }
        String jarPath = "src/main/resources/jars";
        if (code.getIs_default() == 1) {
            jarPath += "/0.jar";
        } else {
            jarPath += "/" + codeId + ".jar";
        }
        String[] args = {jarPath};

        File dependencies = new File("src/main/resources/dependencies/" + codeId + ".txt");

        if (dependencies.exists()) {
            boolean isSuccess = dependencies.delete();
        }

        PrintStream psOld = System.out; // 保存原来的输出路径
        PrintStream ps = null;
        try {
            boolean isSuccess = dependencies.createNewFile();
            if (!isSuccess) {
                return ResponseVO.buildFailure("create dependencies file fail");
            }
            ps = new PrintStream(dependencies);
            System.setOut(ps);// 设置输出重新定向到文件
        } catch (IOException e) {
            e.printStackTrace();
            boolean isSuccess = dependencies.delete();
            return ResponseVO.buildFailure("dependencies file creating exception");
        }

        try {
            JCallGraph.main(args);
        } catch (Exception e) {
            boolean isSuccess = dependencies.delete();
            e.printStackTrace();
            return ResponseVO.buildFailure("Call-Graph error");
        }
        ps.close();
        System.setOut(psOld);
        try {
            filterDependencies("src/main/resources/dependencies/" + codeId + ".txt", packageStrings);
        } catch (IOException e) {
            boolean isSuccess = dependencies.delete();
            e.printStackTrace();
            return ResponseVO.buildFailure("filter dependencies exception");
        }

        return ResponseVO.buildSuccess(code);
    }

    private void filterDependencies(String fileName, ArrayList<String> packageNames) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }

        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        StringBuffer stringBuffer = new StringBuffer();
        temp = br.readLine();

        while (temp != null) {

            if (temp.substring(0, 2).equals("C:")) {
                temp = "";
            }
            String[] vs = temp.split(" ");

            for (String v : vs) {
                boolean valid = false;
                for (String packageName : packageNames) {
                    if (v.contains(packageName + ".")) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    temp = "";
                    break;
                }
            }

            if (!temp.equals("")) {
                stringBuffer.append(temp);
                stringBuffer.append("\n");
            }

            temp = br.readLine();

        }

        file.delete();
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write(stringBuffer.toString().getBytes());
        out.close();
        br.close();
    }

    private ArrayList<String> getPackages(File file) {
        ArrayList<String> res = new ArrayList<String>();

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return res;
        }
        for (File cur : files) {
            if (cur.isDirectory()) {
                ArrayList<String> temp = getPackages(cur);
                for (String str : temp) {
                    res.add(str);
                }
            } else if (cur.isFile()) {
                String curName = cur.getName();
                if (curName.substring(curName.length() - 5, curName.length()).equals(".java")) {
                    BufferedReader br = null;
                    try {
                        FileReader fr = new FileReader(cur);
                        br = new BufferedReader(fr);
                        String tempStr;
                        while ((tempStr = br.readLine()) != null) {
                            if (tempStr.contains("package")) {
                                String packageString = tempStr.split("package")[1];
                                packageString = packageString.substring(0, packageString.length() - 1);
                                packageString = packageString.trim();
                                res.add(packageString);
                                break;
                            }
                        }
                        fr.close();
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return res;
    }

    public ResponseVO cancel(int userId, String uuid) {
        File javaDir = new File("src/main/resources/analyzeCode/src/" + uuid);
        File jarFile = new File("src/main/resources/jars/" + uuid + ".jar");
        if (javaDir.exists()) {
            boolean isSuccess = deleteFile(javaDir);
            if (!isSuccess) {
                return ResponseVO.buildFailure("cancel delete java files fail");
            }
        }
        if (jarFile.exists()) {
            boolean isSuccess = deleteFile(jarFile);
            if (!isSuccess) {
                return ResponseVO.buildFailure("cancel delete jar file fail");
            }
        }

        return ResponseVO.buildSuccess("cancel successfully");
    }

    private boolean deleteFile(File file) {
        boolean res = true;
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


