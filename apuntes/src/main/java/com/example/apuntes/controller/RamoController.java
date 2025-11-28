package com.example.apuntes.controller;

import com.example.apuntes.model.Ramo;
import com.example.apuntes.service.RamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/ramos")
@CrossOrigin(origins = "*")
public class RamoController {

    private final RamoService ramoService;

    public RamoController(RamoService ramoService) {
        this.ramoService = ramoService;
    }

    @GetMapping
    public List<Ramo> listar() {
        return ramoService.listar();
    }

    @PostMapping
    public Ramo crear(@RequestBody Ramo ramo) {
        if (ramo.getColor() == null || ramo.getColor().isBlank()) {
            String[] colores = { "#4e73df", "#1cc88a", "#36b9cc", "#f6c23e", "#e74a3b", "#858796" };
            ramo.setColor(colores[new Random().nextInt(colores.length)]);
        }
        return ramoService.crear(ramo);
    }

    @PutMapping("/{id}")
    public Ramo actualizar(@PathVariable String id, @RequestBody Ramo body) {
        return ramoService.actualizar(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        ramoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
