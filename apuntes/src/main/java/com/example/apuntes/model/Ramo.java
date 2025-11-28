package com.example.apuntes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "ramos")

public class Ramo {

    @Id
    private String id;

    private String nombre;
    private String color;

    public Ramo() {}

    public Ramo(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }
}

