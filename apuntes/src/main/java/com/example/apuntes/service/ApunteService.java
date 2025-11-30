package com.example.apuntes.service;

import com.example.apuntes.model.Apunte;
import com.example.apuntes.repository.ApunteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApunteService {

    private final ApunteRepository apunteRepository;

    // Crear un apunte con userId
    public Apunte crear(Apunte apunte) {
        limpiarCampos(apunte);
        apunte.setFechaCreacion(LocalDateTime.now());
        apunte.setFechaActualizacion(LocalDateTime.now());
        return apunteRepository.save(apunte);
    }

    // Obtener todos los apuntes del usuario
    public List<Apunte> listarPorUser(String userId) {
        return apunteRepository.findByUserId(userId);
    }

    // Obtener UN apunte (luego se valida en el controller si pertenece al user)
    public Apunte obtenerPorId(String id) {
        return apunteRepository.findById(id)
                .orElse(null);
    }

    // Buscar por texto dentro de los apuntes del usuario
    public List<Apunte> buscarPorTexto(String userId, String texto) {
        return apunteRepository
                .findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(texto, texto)
                .stream()
                .filter(a -> a.getUserId().equals(userId))
                .toList();
    }

    // Buscar por ramo solo dentro del usuario
    public List<Apunte> buscarPorRamo(String userId, String ramo) {
        return apunteRepository.findByUserId(userId)
                .stream()
                .filter(a -> a.getRamo().equalsIgnoreCase(ramo))
                .toList();
    }

    // Buscar por tags solo del usuario
    public List<Apunte> buscarPorTags(String userId, List<String> tags) {
        return apunteRepository.findByUserId(userId)
                .stream()
                .filter(a -> a.getTags().stream().anyMatch(tags::contains))
                .toList();
    }

    // Actualizar un apunte
    public Apunte actualizar(Apunte original, Apunte nuevo) {

        original.setRamo(nuevo.getRamo());
        original.setTitulo(nuevo.getTitulo());
        original.setContenido(nuevo.getContenido());
        original.setTags(nuevo.getTags());

        limpiarCampos(original);
        original.setFechaActualizacion(LocalDateTime.now());

        return apunteRepository.save(original);
    }

    // Eliminar un apunte
    public boolean eliminar(String id) {
        if (apunteRepository.existsById(id)) {
            apunteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Limpieza de datos
    private void limpiarCampos(Apunte apunte) {
        if (apunte.getRamo() != null)
            apunte.setRamo(apunte.getRamo().trim());

        if (apunte.getTitulo() != null)
            apunte.setTitulo(apunte.getTitulo().trim());

        if (apunte.getContenido() != null)
            apunte.setContenido(apunte.getContenido().trim());

        if (apunte.getTags() != null)
            apunte.setTags(
                    apunte.getTags().stream()
                            .map(tag -> tag.trim().toLowerCase())
                            .toList()
            );
    }
}
