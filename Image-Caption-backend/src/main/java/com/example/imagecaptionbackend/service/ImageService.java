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
import java.util.Base64;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserImageRepository userImageRepository;

    public void createImage(Long userId, byte[] data) {
        Image image = imageRepository.save(new Image(data));
        Long imageId = image.getId();
        UserImage userImage = new UserImage(userId, imageId);
        userImageRepository.save(userImage);
    }

    public void deleteImage(String role, Long imageId) {
        if(role.equals("admin"))
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

    public Page<Image> getAllImages(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return imageRepository.findAll(pageable);
    }

    public String captionImage(int mode, Long id) {
        Image image = getImage(id);
        if (image.isCaptioned()) {
            return image.getLabel_1();
        }
        byte[] data = image.getData();
        try {
            String pythonScriptPath = "src/main/python/caption.py";
            String[] cmd = {"python", pythonScriptPath};
            Process p = new ProcessBuilder(cmd).start();
            // 输入图片数据
            PrintWriter stdin = new PrintWriter(new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8));
            stdin.println(Base64.getEncoder().encodeToString(data));
            stdin.close();
            // 读取输出
            BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            String label = stdout.readLine();
            // 设置超时时间
            int delay = 5;
            if (!p.waitFor(delay, java.util.concurrent.TimeUnit.SECONDS)) {
                p.destroy();
                throw new InterruptedException("Timeout");
            }
            // 设置标签
            image.setLabel_1(label);
            imageRepository.save(image);
            return "Hello world";
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Caption failed");
        }
    }
}
