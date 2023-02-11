package com.rememberme.gcs.FileService;

import com.rememberme.gcs.InputFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<InputFile> uploadFiles(MultipartFile[] files);
}