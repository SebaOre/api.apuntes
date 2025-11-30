package com.example.apuntes.repository;

import com.example.apuntes.model.Ramo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamoRepository extends MongoRepository<Ramo, String> {

    List<Ramo> findByUserId(String userId);
}
