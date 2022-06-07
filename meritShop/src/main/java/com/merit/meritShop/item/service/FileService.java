package com.merit.meritShop.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    private final String [] accessList = {".jpg",".jpeg",".JPG",".JPEG",".bmp",".BMP",".GIF",".gif",".png",".PNG"};

    public String upload(String uploadPath,String fileName,byte[] fileData) throws IOException {

        boolean atoken=false;

        UUID uuid = UUID.randomUUID();

        String extension = fileName.substring(fileName.lastIndexOf("."));
        for (String ex:accessList) {
            if(ex.equals(extension)){atoken=true;}
            System.out.println(extension);
        }
        if (atoken!=true){
            throw new RuntimeException("can't upload this file");
        }

        String savedFileName = uuid.toString()+extension;

        String fileUploadPath = uploadPath +"/"+savedFileName;
        System.out.println(fileUploadPath);
        FileOutputStream fos = new FileOutputStream(fileUploadPath);
        fos.write(fileData);
        fos.close();


        return savedFileName;
    }

    public void deleteFile(String filePath){

        File deleteFile = new File(filePath);

        try{
            if(deleteFile.exists()){
                deleteFile.delete();
                log.info("파일을  삭제 했습니다.");
            }else {
                log.info("해당 파일이 존재 하지 않습니다.");
            }

        }catch (RuntimeException e){
            System.out.println(e.toString());
        }

    }
}
