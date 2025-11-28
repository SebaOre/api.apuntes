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

    // Crear un apunte
    public Apunte crear(Apunte apunte) {
        limpiarCampos(apunte);
        apunte.setFechaCreacion(LocalDateTime.now());
        apunte.setFechaActualizacion(LocalDateTime.now());
        return apunteRepository.save(apunte);
    }

    // Obtener todos
    public List<Apunte> obtenerTodos() {
        return apunteRepository.findAll();
    }

    // Obtener uno
    public Apunte obtenerPorId(String id) {
        return apunteRepository.findById(id)
                .orElse(null);
    }

    // Buscar por texto en título o contenido
    public List<Apunte> buscarPorTexto(String texto) {
        return apunteRepository
                .findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(texto, texto);
    }

    // Buscar por ramo
    public List<Apunte> buscarPorRamo(String ramo) {
        return apunteRepository.findByRamoIgnoreCase(ramo);
    }

    // Buscar por tags
    public List<Apunte> buscarPorTags(List<String> tags) {
        return apunteRepository.findByTagsInIgnoreCase(tags);
    }

    // Actualizar
    public Apunte actualizar(String id, Apunte nuevo) {
        return apunteRepository.findById(id).map(apunte -> {

            apunte.setRamo(nuevo.getRamo());
            apunte.setTitulo(nuevo.getTitulo());
            apunte.setContenido(nuevo.getContenido());
            apunte.setTags(nuevo.getTags());

            limpiarCampos(apunte);
            apunte.setFechaActualizacion(LocalDateTime.now());

            return apunteRepository.save(apunte);
        }).orElse(null);
    }

    // Eliminar
    public boolean eliminar(String id) {
        if (apunteRepository.existsById(id)) {
            apunteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // -------------------------------------
    // MÉTODOS INTERNOS (limpieza)
    // -------------------------------------

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
