package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadBL {
    public ResponseVO uploadCode(int userId, MultipartFile[] files) throws IOException;
    public ResponseVO analyzeJar(int codeId) throws IOException;
}
