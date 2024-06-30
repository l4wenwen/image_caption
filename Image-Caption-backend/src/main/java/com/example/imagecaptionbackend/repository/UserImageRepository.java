package com.example.imagecaptionbackend.repository;

import com.example.imagecaptionbackend.entity.Image;
import com.example.imagecaptionbackend.entity.UserImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Long> {
    @Query("SELECT i FROM Image i JOIN UserImage ui ON i.id = ui.imageId WHERE ui.userId = :userId")
    Page<Image> findByUserId(Long userId, Pageable pageable);

    void deleteByImageId(Long imageId);
}
