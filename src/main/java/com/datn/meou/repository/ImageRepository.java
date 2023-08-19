package com.datn.meou.repository;

import com.datn.meou.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByName(String name);

    List<Image> findAllByProductItemId(Long id);

}
