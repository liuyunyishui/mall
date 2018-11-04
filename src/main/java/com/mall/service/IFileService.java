package com.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ly on 2018/3/23.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
