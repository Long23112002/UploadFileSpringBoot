package com.example.uploadimage.controler;

import com.example.uploadimage.model.Img;
import com.example.uploadimage.repository.ImgRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
public class ImageControler {
    private ImgRepository imgRepository;

    static Path fileImgFound;

    @Autowired
    public void setImgRepo(ImgRepository imgRepository) {
        this.imgRepository = imgRepository;
    }

    private static final String UPLOAD_DIR = "C:\\Users\\long0\\IdeaProjects\\UploadImage\\target\\";

    @GetMapping("/admin")
    public String index (){
        return "index";
    }

    @PostMapping("/upload")
    public String updateImg(@RequestParam("file")  MultipartFile file , Model model) throws IOException {
        if(file.isEmpty()){
            model.addAttribute("message", "Please select a file to upload.");
            return "index";
        }

        Path imgFor = Paths.get("img");
        if (!Files.exists(imgFor)){
            Files.createDirectories(imgFor);
        }

        String code = RandomStringUtils.randomAlphanumeric(10);

        try (InputStream inputStream = file.getInputStream()) {
            Path fileSave = imgFor.resolve(code + "-" + Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(inputStream, fileSave, StandardCopyOption.REPLACE_EXISTING);
            Img img = new Img();
            img.setCodeImg(code);
            imgRepository.save(img);
        }catch (Exception e){
            System.out.println("false");
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping(value = "/{code}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public String getImg(@PathVariable String code, Model model){
        Path imgFor = Paths.get("C:\\Users\\long0\\IdeaProjects\\UploadImage\\src\\main\\resources\\static\\img");

        try {
            Files.list(imgFor).forEach(file -> {
                if (file.getFileName().toString().startsWith(code)){
                    fileImgFound = file;
                }
            });

            if (fileImgFound != null){
                Resource urlImg = new UrlResource(fileImgFound.getFileName().toUri());
                System.out.println(urlImg);
                model.addAttribute("img", fileImgFound.getFileName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "test";
    }



}
