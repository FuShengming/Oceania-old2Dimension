package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.UploadBL;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    UploadBL uploadBL;
    @RequestMapping("/code")
    public ResponseVO codeUpload(@RequestParam("userId") int userId,
                                 @RequestParam("file") MultipartFile[] files) throws IOException {
       return uploadBL.uploadCode(userId,files);
    }

    @RequestMapping("/analyze")
    public ResponseVO analyzeJar(@RequestParam("codeId") int codeId) throws IOException{
       return uploadBL.analyzeJar(codeId);
    }
}
