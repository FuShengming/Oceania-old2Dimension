package com.old2dimension.OCEANIA.blImpl.UploadBLImplTest;
import static org.mockito.Mockito.*;
import com.old2dimension.OCEANIA.blImpl.UploadBLImpl;
import com.old2dimension.OCEANIA.dao.CodeRepository;
import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UploadConfirmForm;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UploadBLImplTest {

    @Test
    public void uploadJavaTest1(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        String uuid = "-1";
        String content1String = "public class a{}";
        String content2String = "public class b{}";
        MultipartFile multipartFile1 = new MockMultipartFile("file","test/a.java","java",content1String.getBytes());
        MultipartFile multipartFile2 = new MockMultipartFile("file","test/b.java","java",content2String.getBytes());
        MultipartFile[] files = {multipartFile1, multipartFile2};
        uploadBL.uploadCode(uuid,files);
        File testFile = new File("src/main/resources/analyzeCode/src/-1/test/a.java");
        File testDir = new File("src/main/resources/analyzeCode/src/-1");
        Assert.assertTrue(testFile.exists());
        Assert.assertTrue(testDir.exists());
        boolean isSuccess = deleteFile(testDir);
        if(!isSuccess){
            System.out.println("delete fail");
        }
    }

    @Test
    public void uploadJavaTest2(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        String uuid = "-1";
        MultipartFile[] files = {};
        ResponseVO responseVO = uploadBL.uploadCode(uuid, files);
        Assert.assertEquals("EMPTY",responseVO.getMessage());
    }

    @Test
    public void uploadJavaTest3(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        String uuid = "-1";
        ResponseVO responseVO = uploadBL.uploadCode(uuid, null);
        Assert.assertEquals("NULL",responseVO.getMessage());
    }

    @Test
    public void uploadJarTest1(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        String uuid = "-1";
        File file = new File("src/test/java/com/old2dimension/OCEANIA/blImpl/UploadBLImplTest/1.jar");
        FileReader fr = null;
        try{
        fr = new FileReader(file);
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(fr);
        byte[] testContent = bufferedReader.toString().getBytes();
        MultipartFile multipartFile1 = new MockMultipartFile("file","test/a.jar","jar",testContent);
        uploadBL.uploadJar(uuid, multipartFile1);
        File testFile = new File("src/main/resources/jars/-1.jar");
        Assert.assertTrue(testFile.exists());
        boolean isSuccess = deleteFile(testFile);
        if(!isSuccess){
            System.out.println("delete fail");
        }
    }

    @Test
    public void analyzeTest(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        String uuid = "-1";
        File file = new File("src/test/java/com/old2dimension/OCEANIA/blImpl/UploadBLImplTest/1.jar");
        FileReader fr = null;
        try{
            fr = new FileReader(file);
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(fr);
        byte[] testContent = bufferedReader.toString().getBytes();
        File jarFile = new File("src/main/resources/jars/-2.jar");
        if(!jarFile.exists()){
            try{
            boolean isSuccess = jarFile.createNewFile();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
        FileOutputStream out = new FileOutputStream(jarFile);
        out.write(testContent);
        out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        File javaDir = new File("src/main/resources/analyzeCode/src/-2");
        boolean isSuccess = javaDir.mkdirs();

        File javaFile = new File("src/main/resources/analyzeCode/src/-2/a.java");
        if(!javaFile.exists()){
            try{
                String javaContent = "public class a{}";
                isSuccess = javaFile.createNewFile();
                FileOutputStream out = new FileOutputStream(javaFile);
                out.write(javaContent.getBytes());
                out.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        Code code = new Code();
        code.setId(-1);
        code.setIs_default(0);
        CodeRepository codeRepository = mock(CodeRepository.class);
        when(codeRepository.save(any())).thenReturn(code);
        doNothing().when(codeRepository).delete(any());
        when(codeRepository.findCodeById(-1)).thenReturn(code);
        uploadBL.setCodeRepository(codeRepository);
        UploadConfirmForm uploadConfirmForm = new UploadConfirmForm();
        uploadConfirmForm.setUuid("-2");
        uploadConfirmForm.setUserId(-1);
        uploadConfirmForm.setName("-2");
        uploadBL.analyzeJar(-1,"-2");
        File java = new File("src/main/resources/analyzeCode/src/-1/a.java");
        File jar  = new File("src/main/resources/jars/-1.jar");
        File dependency = new File ("src/main/resources/dependencies/-1.txt");
        Assert.assertTrue(jar.exists());
        Assert.assertTrue(java.exists());
        Assert.assertTrue(dependency.exists());
        deleteFile(jar);
        deleteFile(dependency);
        javaDir = new File("src/main/resources/analyzeCode/src/-1");
        deleteFile(javaDir);

    }

    @Test
    public void cancelTest1(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        File file = new File("src/test/java/com/old2dimension/OCEANIA/blImpl/UploadBLImplTest/1.jar");
        FileReader fr = null;
        try{
            fr = new FileReader(file);
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(fr);
        byte[] testContent = bufferedReader.toString().getBytes();
        File jarFile = new File("src/main/resources/jars/-2.jar");
        if(!jarFile.exists()){
            try{
                boolean isSuccess = jarFile.createNewFile();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
            FileOutputStream out = new FileOutputStream(jarFile);
            out.write(testContent);
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        File javaDir = new File("src/main/resources/analyzeCode/src/-2");
        boolean isSuccess = javaDir.mkdirs();

        File javaFile = new File("src/main/resources/analyzeCode/src/-2/a.java");
        if(!javaFile.exists()){
            try{
                String javaContent = "public class a{}";
                isSuccess = javaFile.createNewFile();
                FileOutputStream out = new FileOutputStream(javaFile);
                out.write(javaContent.getBytes());
                out.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        Assert.assertTrue(javaDir.exists());
        Assert.assertTrue(jarFile.exists());
        Assert.assertTrue(jarFile.exists());
        uploadBL.cancel(1,"-2");
        Assert.assertFalse(jarFile.exists());
        Assert.assertFalse(javaDir.exists());
        Assert.assertFalse(javaFile.exists());
    }

    @Test
    public void cancelTest2(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        File javaDir = new File("src/main/resources/analyzeCode/src/-2");
        boolean isSuccess = javaDir.mkdirs();

        File javaFile = new File("src/main/resources/analyzeCode/src/-2/a.java");
        if(!javaFile.exists()){
            try{
                String javaContent = "public class a{}";
                isSuccess = javaFile.createNewFile();
                FileOutputStream out = new FileOutputStream(javaFile);
                out.write(javaContent.getBytes());
                out.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        Assert.assertTrue(javaDir.exists());
        uploadBL.cancel(1,"-2");
        Assert.assertFalse(javaDir.exists());
        Assert.assertFalse(javaFile.exists());
    }

    @Test
    public void cancelTest3(){
        UploadBLImpl uploadBL = new UploadBLImpl();
        File file = new File("src/test/java/com/old2dimension/OCEANIA/blImpl/UploadBLImplTest/1.jar");
        FileReader fr = null;
        try{
            fr = new FileReader(file);
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(fr);
        byte[] testContent = bufferedReader.toString().getBytes();
        File jarFile = new File("src/main/resources/jars/-2.jar");
        if(!jarFile.exists()){
            try{
                boolean isSuccess = jarFile.createNewFile();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
            FileOutputStream out = new FileOutputStream(jarFile);
            out.write(testContent);
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        Assert.assertTrue(jarFile.exists());
        Assert.assertTrue(jarFile.exists());
        uploadBL.cancel(1,"-2");
        Assert.assertFalse(jarFile.exists());
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
            }
            else{
                res = res& deleteFile(cur);
            }
        }
        res = res & file.delete();
        return res;
    }
}
