package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.UploadBL;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UniqueIdentificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    UploadBL uploadBL;

    @RequestMapping("/code")
    public ResponseVO codeUpload(@RequestParam("userId") int userId,
                                 @RequestParam("file") MultipartFile[] files) throws IOException {
        return uploadBL.uploadCode(userId, files);
    }

    @RequestMapping("/jar")
    public ResponseVO codeUpload(@RequestParam("userId") int userId,
                                 @RequestParam("uuid") String uuid,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(userId);
        System.out.println(uuid);
        System.out.println(file.getOriginalFilename());
        return ResponseVO.buildSuccess();
    }


    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public ResponseVO analyzeJar(@RequestBody UniqueIdentificationVO ui) throws IOException, InterruptedException {
        System.out.println(ui.getUserId());
        System.out.println(ui.getUuid());
        Thread.sleep(2000);
        return ResponseVO.buildSuccess();
//        return uploadBL.analyzeJar(codeId);
    }
}