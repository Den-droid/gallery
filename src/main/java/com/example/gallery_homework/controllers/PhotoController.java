package com.example.gallery_homework.controllers;

import com.example.gallery_homework.models.Photo;
import com.example.gallery_homework.repository.PhotoRepository;
import com.example.gallery_homework.services.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Controller
public class PhotoController {
    private final StorageService storageService;
    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoController(StorageService service, PhotoRepository repository){
        this.photoRepository = repository;
        this.storageService = service;
    }

    @GetMapping("/photo/add-photo")
    public String AddPhotoPage(Model model){
        model.addAttribute("pageTitle", "Add Photo");
        return "add-photo";
    }

    @PostMapping("/photo/add-photo")
    public String UploadPage(@RequestParam(name = "name") MultipartFile file, Model model){
        Photo photo;
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            try {
                String name = UUID.randomUUID().toString()+"." +
                        FilenameUtils.getExtension(file.getOriginalFilename());

                byte[] bytes = file.getBytes();

                Path f = storageService.load("");
                String rootPath= f.toUri().getPath();
                System.out.println("---------"+rootPath);
                File dir = new File(rootPath + File.separator );
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                photo = new Photo();
                photo.setName(name);
                photoRepository.save(photo);
                model.addAttribute("result", "Photo adding succeed");
                return "add-photo";

            } catch (Exception e) {
                model.addAttribute("result", "Photo adding error. Try to upload another photo.");
                return "add-photo";
            }
        }

        model.addAttribute("result", "Photo adding error. Try to upload another photo.");
        return "add-photo";
    }
}
