package com.example.apuntes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "apuntes")
public class Apunte {

    @Id
    private String id;

    private String ramo;

    private String titulo;

    private String contenido;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private List<String> tags;

    private String userId;

}
