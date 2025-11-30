package com.example.apuntes.repository;

import com.example.apuntes.model.Apunte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApunteRepository extends MongoRepository<Apunte, String> {

    // Buscar por texto (título o contenido)
    List<Apunte> findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(
            String titulo, String contenido
    );

    // Buscar por ramo
    List<Apunte> findByRamoIgnoreCase(String ramo);

    // Buscar por tags
    List<Apunte> findByTagsInIgnoreCase(List<String> tags);

    List<Apunte> findByUserId(String userId);
    List<Apunte> findByUserIdAndTituloContainingIgnoreCase(String userId, String titulo);
    List<Apunte> findByUserIdAndContenidoContainingIgnoreCase(String userId, String contenido);
    List<Apunte> findByUserIdAndRamoIgnoreCase(String userId, String ramo);
    List<Apunte> findByUserIdAndTagsInIgnoreCase(String userId, List<String> tags);


}
