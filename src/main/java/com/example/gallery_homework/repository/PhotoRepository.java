package com.example.gallery_homework.repository;

import com.example.gallery_homework.models.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long>{
}
