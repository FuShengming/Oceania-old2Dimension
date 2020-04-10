package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.vo.ResponseVO;
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
    @RequestMapping("/code")
    public ResponseVO codeUpload(@RequestParam("codeId") int codeId,
                                 @RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null) return new ResponseVO().buildFailure("NULL");
        if (files.length == 0) return new ResponseVO().buildFailure("EMPTY");
        for (MultipartFile multipartFile : files) {
            System.out.println("---------------------");
            System.out.println(multipartFile.getOriginalFilename());
            System.out.println(multipartFile.getName());
            System.out.println(multipartFile.getContentType());
            String content = new BufferedReader(new InputStreamReader(multipartFile.getInputStream())).lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(content);
            System.out.println("---------------------");
//            byte[] bytes = multipartFile.getBytes();
//            File file = new File("");
//            System.out.println(Arrays.toString(bytes));
        }
        return new ResponseVO().buildSuccess();
    }
}
