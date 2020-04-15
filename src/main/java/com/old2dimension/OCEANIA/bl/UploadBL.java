package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UploadConfirmForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadBL {
    public ResponseVO uploadCode(String uuid, MultipartFile[] files);

    public ResponseVO analyzeJar(int userId, String uuid);

    public ResponseVO uploadJar(String uuid, MultipartFile file);

    public ResponseVO cancel(int userId, String uuid);
}
