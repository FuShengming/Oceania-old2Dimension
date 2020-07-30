package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.UploadBL;
import com.old2dimension.OCEANIA.vo.GroupIdAndUuidForm;
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
                                 @RequestParam("uuid") String uuid,
                                 @RequestParam("file") MultipartFile[] files) throws IOException {
        return uploadBL.uploadCode(uuid, files);
    }

    @RequestMapping("/jar")
    public ResponseVO jarUpload(@RequestParam("userId") int userId,
                                @RequestParam("uuid") String uuid,
                                @RequestParam("file") MultipartFile file) throws IOException {

        return uploadBL.uploadJar(uuid,file);
    }

    @RequestMapping(value = "/group/analyze", method = RequestMethod.POST)
    public ResponseVO groupAnalyzeJar(@RequestBody GroupIdAndUuidForm groupIdAndUuidForm
                                      ) throws IOException, InterruptedException {

        return uploadBL.groupAnalyzeJar(groupIdAndUuidForm.getGroupId(),groupIdAndUuidForm.getUuid());
    }

    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public ResponseVO analyzeJar(@RequestBody UniqueIdentificationVO ui) throws IOException, InterruptedException {
        return uploadBL.analyzeJar(ui.getUserId(),ui.getUuid());
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseVO cancel(@RequestBody UniqueIdentificationVO ui) throws IOException, InterruptedException {
        return uploadBL.cancel(ui.getUserId(), ui.getUuid());
    }
}
