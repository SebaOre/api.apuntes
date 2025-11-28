package com.example.apuntes.repository;

import com.example.apuntes.model.Ramo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamoRepository extends MongoRepository<Ramo, String> {
}
