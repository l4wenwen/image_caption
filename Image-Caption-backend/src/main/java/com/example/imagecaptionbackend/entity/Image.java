package com.example.imagecaptionbackend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel("图像实体类")
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Lob
    @ApiModelProperty(value = "图像数据", required = true, example = "略")
    @Column(name = "data")
    private byte[] data;
    @ApiModelProperty(value = "标签", required = false, example = "略")
    @Column(name = "label1")
    private String label_1;

    public Image(byte[] data) {
        this.data = data;
    }

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public String getLabel_1() {
        return label_1;
    }

    public void setLabel_1(String label) {
        this.label_1 = label;
    }

    public boolean isCaptioned() {
        return label_1 != null && !label_1.isEmpty();
    }
}
