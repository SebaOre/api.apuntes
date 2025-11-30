package com.example.apuntes.controller;

import com.example.apuntes.model.Ramo;
import com.example.apuntes.service.RamoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.apuntes.config.JwtUtil;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/ramos")
@CrossOrigin(origins = "*")
public class RamoController {

    private final RamoService ramoService;
    private final JwtUtil jwtUtil;

    public RamoController(RamoService ramoService, JwtUtil jwtUtil) {
        this.ramoService = ramoService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Ramo> listar(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        return ramoService.listarPorUser(userId);
    }

    @PostMapping
    public Ramo crear(@RequestBody Ramo ramo, HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        // ASIGNAR USUARIO
        ramo.setUserId(userId);

        // COLOR RANDOM SI NO VIENE
        if (ramo.getColor() == null || ramo.getColor().isBlank()) {
            String[] colores = { "#4e73df", "#1cc88a", "#36b9cc", "#f6c23e", "#e74a3b", "#858796" };
            ramo.setColor(colores[new Random().nextInt(colores.length)]);
        }

        return ramoService.crear(ramo);
    }

    @PutMapping("/{id}")
    public Ramo actualizar(
            @PathVariable String id,
            @RequestBody Ramo body,
            HttpServletRequest request) {

        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        Ramo original = ramoService.obtenerPorId(id);

        if (!original.getUserId().equals(userId)) {
            throw new RuntimeException("No tienes permisos para editar este ramo");
        }

        return ramoService.actualizar(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            HttpServletRequest request) {

        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        Ramo original = ramoService.obtenerPorId(id);

        if (!original.getUserId().equals(userId)) {
            throw new RuntimeException("No tienes permisos para eliminar este ramo");
        }

        ramoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
