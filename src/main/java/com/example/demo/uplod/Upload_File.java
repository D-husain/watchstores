package com.example.demo.uplod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Upload_File {

	public Upload_File() throws IOException {

	}

	public boolean uploadFile(MultipartFile file, String uploadDirectory) {
	    boolean isUploaded = false;

	    try {
	        Path uploadPath = Paths.get(uploadDirectory);
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        Path filePath = uploadPath.resolve(file.getOriginalFilename());
	        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	        isUploaded = true;

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return isUploaded;
	}

}

