package com.example.apuntes.controller;

import com.example.apuntes.model.Apunte;
import com.example.apuntes.service.ApunteService;
import com.example.apuntes.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/apuntes")
@CrossOrigin(origins = "*")
public class ApunteController {

    private final ApunteService apunteService;
    private final JwtUtil jwtUtil;

    public ApunteController(ApunteService apunteService, JwtUtil jwtUtil) {
        this.apunteService = apunteService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Apunte> listar(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        return apunteService.listarPorUser(userId);
    }

    @GetMapping("/{id}")
    public Apunte obtener(@PathVariable String id, HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        Apunte apunte = apunteService.obtenerPorId(id);

        if (!apunte.getUserId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para ver este apunte");
        }

        return apunte;
    }

    @PostMapping
    public Apunte crear(@RequestBody Apunte apunte, HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        apunte.setUserId(userId);
        apunte.setFechaCreacion(LocalDateTime.now());
        apunte.setFechaActualizacion(LocalDateTime.now());

        return apunteService.crear(apunte);
    }

    @PutMapping("/{id}")
    public Apunte actualizar(@PathVariable String id,
                             @RequestBody Apunte body,
                             HttpServletRequest request) {

        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        Apunte original = apunteService.obtenerPorId(id);

        if (!original.getUserId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para editar este apunte");
        }

        original.setTitulo(body.getTitulo());
        original.setContenido(body.getContenido());
        original.setRamo(body.getRamo());
        original.setTags(body.getTags());
        original.setFechaActualizacion(LocalDateTime.now());

        return apunteService.crear(original);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id,
                                         HttpServletRequest request) {

        String token = jwtUtil.extractToken(request);
        String userId = jwtUtil.getUserIdFromToken(token);

        Apunte original = apunteService.obtenerPorId(id);

        if (!original.getUserId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para eliminar este apunte");
        }

        apunteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
