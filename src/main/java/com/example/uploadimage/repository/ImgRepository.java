package com.example.uploadimage.repository;

import com.example.uploadimage.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<Img,Integer> {
}
