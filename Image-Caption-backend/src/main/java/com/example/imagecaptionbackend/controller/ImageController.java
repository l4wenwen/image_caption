package com.example.imagecaptionbackend.controller;

import com.example.imagecaptionbackend.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "图像管理")
@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @ApiParam(value = "图像数据", required = true, example = "略") @RequestParam byte[] data,
            @ApiIgnore HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            imageService.createImage(userId, data);
            return ResponseEntity.ok("Upload successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteImage(
            @ApiParam(value = "图像id", required = true, example = "略") @RequestParam Long id,
            @ApiIgnore HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            imageService.deleteImage(role, id);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/one")
    public ResponseEntity<?> getImage(@ApiParam(value = "图像id", required = true, example = "略") Long id) {
        try {
            return ResponseEntity.ok(imageService.getImage(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserImages(
            @ApiParam(value = "查询页数", required = true, example = "1") int page,
            @ApiParam(value = "页面大小", required = true, example = "10") int size,
            @ApiIgnore HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            return ResponseEntity.ok(imageService.getUserImages(userId, page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllImages(
            @ApiParam(value = "用户id", required = true, example = "略") Long userId,
            @ApiParam(value = "查询页数", required = true, example = "1") int page,
            @ApiParam(value = "页面大小", required = true, example = "10") int size,
            @ApiIgnore HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        } else if (!role.equals("admin")) {
            return ResponseEntity.badRequest().body("You are not an admin");
        }
        try {
            return ResponseEntity.ok(imageService.getUserImages(userId, page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllImages(
            @ApiParam(value = "查询页数", required = true, example = "1") int page,
            @ApiParam(value = "页面大小", required = true, example = "10") int size) {
        try {
            return ResponseEntity.ok(imageService.getAllImages(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/caption")
    public ResponseEntity<?> captionImage(
            @ApiParam(value = "描述模式", required = true, example = "1") @RequestParam int mode,
            @ApiParam(value = "图像id", required = true, example = "略") @RequestParam Long id) {
        try {
            String label = imageService.captionImage(mode, id);
            return ResponseEntity.ok(label);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
