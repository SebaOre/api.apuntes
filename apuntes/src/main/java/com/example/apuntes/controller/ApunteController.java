package com.example.apuntes.controller;

import com.example.apuntes.model.Apunte;
import com.example.apuntes.service.ApunteService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apuntes")
@RequiredArgsConstructor
@CrossOrigin("*") // Permite llamadas desde React mientras desarrollas
public class ApunteController {

    private final ApunteService apunteService;

    // Crear apunte
    @PostMapping
    public ResponseEntity<Apunte> crear(@RequestBody Apunte apunte) {
        Apunte creado = apunteService.crear(apunte);
        return ResponseEntity.ok(creado);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Apunte>> obtenerTodos() {
        return ResponseEntity.ok(apunteService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Apunte> obtenerPorId(@PathVariable String id) {
        Apunte apunte = apunteService.obtenerPorId(id);
        return (apunte != null)
                ? ResponseEntity.ok(apunte)
                : ResponseEntity.notFound().build();
    }

    // Buscar por texto (título o contenido)
    @GetMapping("/buscar")
    public ResponseEntity<List<Apunte>> buscarPorTexto(@RequestParam String texto) {
        return ResponseEntity.ok(apunteService.buscarPorTexto(texto));
    }

    // Buscar por ramo
    @GetMapping("/ramo/{ramo}")
    public ResponseEntity<List<Apunte>> buscarPorRamo(@PathVariable String ramo) {
        return ResponseEntity.ok(apunteService.buscarPorRamo(ramo));
    }

    // Buscar por tags: /apuntes/tags?tag=sql&tag=java
    @GetMapping("/tags")
    public ResponseEntity<List<Apunte>> buscarPorTags(@RequestParam List<String> tag) {
        return ResponseEntity.ok(apunteService.buscarPorTags(tag));
    }

    // Actualizar apunte
    @PutMapping("/{id}")
    public ResponseEntity<Apunte> actualizar(
            @PathVariable String id,
            @RequestBody Apunte apunte
    ) {
        Apunte actualizado = apunteService.actualizar(id, apunte);
        return (actualizado != null)
                ? ResponseEntity.ok(actualizado)
                : ResponseEntity.notFound().build();
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        boolean eliminado = apunteService.eliminar(id);
        return (eliminado)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
