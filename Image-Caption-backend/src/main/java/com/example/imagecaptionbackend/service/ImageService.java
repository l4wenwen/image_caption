package com.example.imagecaptionbackend.service;

import com.example.imagecaptionbackend.entity.Image;
import com.example.imagecaptionbackend.entity.UserImage;
import com.example.imagecaptionbackend.repository.ImageRepository;
import com.example.imagecaptionbackend.repository.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserImageRepository userImageRepository;

    public Long createImage(Long userId, byte[] data) {
        Image image = imageRepository.save(new Image(data));
        Long imageId = image.getId();
        UserImage userImage = new UserImage(userId, imageId);
        userImageRepository.save(userImage);
        return imageId;
    }

    public void deleteImage(String role, Long imageId) {
        if (role.equals("admin"))
            imageRepository.deleteById(imageId);
        else {
            userImageRepository.deleteByImageId(imageId);
        }
    }

    public Image getImage(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            throw new IllegalArgumentException("Image does not exist");
        }
        return image;
    }

    public Page<Image> getUserImages(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userImageRepository.findByUserId(userId, pageable);
    }

    public Page<Image> getAllImages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return imageRepository.findAll(pageable);
    }

    public String captionImage(int mode, Long id) {
        Image image = getImage(id);
        if (image.isCaptioned()) {
            return image.getLabel_1();
        }

        try {
            // 输入图片数据
            System.out.println(id);
            Files.write(Paths.get("src\\main\\resources\\image_caption\\img.txt"), image.getData());
            ProcessBuilder pb = new ProcessBuilder("python", "src/main/resources/convert.py");
            Process procConvert = pb.start();
            int exitcode = procConvert.waitFor();
            System.out.println("ok convert " + exitcode);
            
            pb = new ProcessBuilder("D:\\App\\anaconda\\envs\\ICAP\\python.exe",
                    "D:\\Projects\\ICAP\\Image-Caption-backend\\src\\main\\resources\\image_caption\\main.py");
            pb = pb.directory(
                    new File("D:\\Projects\\ICAP\\Image-Caption-backend\\src\\main\\resources\\image_caption"));
            Process p = pb.start();
            // 等待程序结束
            exitcode = p.waitFor();
            System.out.println("caption status: " + exitcode);  
            // 读取输出
            String label = Files.readString(Paths.get("src\\main\\resources\\image_caption\\output.txt"),
                    StandardCharsets.UTF_8);
            // 设置超时时间
            // int delay = 5;
            // if (!p.waitFor(delay, java.util.concurrent.TimeUnit.SECONDS)) {
            // p.destroy();
            // throw new InterruptedException("Timeout");
            // }
            // 设置标签
            String[] words = label.split(" ");
            StringBuilder sb = new StringBuilder();
            // 删除首末单词
            for (int i = 1; i < words.length - 1; i++) {
                sb.append(words[i]);
                sb.append(" ");
            }
            label = sb.toString();
            image.setLabel_1(label);
            imageRepository.save(image);
            System.out.println(label);
            return label;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Caption failed");
        }
    }
}
