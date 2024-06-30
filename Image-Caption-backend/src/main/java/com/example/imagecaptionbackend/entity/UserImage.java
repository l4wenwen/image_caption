package com.example.imagecaptionbackend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel("用户图像关联实体类")
@Table(name = "user_image")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ApiModelProperty(value = "用户id", required = true, example = "略")
    @Column(name = "userid")
    private Long userId;
    @ApiModelProperty(value = "图像id", required = true, example = "略")
    @Column(name = "imageid")
    private Long imageId;

    public UserImage(Long userId, Long imageId) {
        this.userId = userId;
        this.imageId = imageId;
    }

    public UserImage() {
    }

    public Long getImageId(){
        return imageId;
    }
}
