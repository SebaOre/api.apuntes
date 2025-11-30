package com.example.apuntes.service;

import com.example.apuntes.model.Ramo;
import com.example.apuntes.repository.RamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamoService {

    private final RamoRepository ramoRepository;

    public RamoService(RamoRepository ramoRepository) {
        this.ramoRepository = ramoRepository;
    }

    public List<Ramo> listarPorUser(String userId) {
        return ramoRepository.findByUserId(userId);
    }

    public Ramo crear(Ramo ramo) {
        return ramoRepository.save(ramo);
    }

    public Ramo obtenerPorId(String id) {
        return ramoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ramo no encontrado"));
    }

    public void eliminar(String id) {
        ramoRepository.deleteById(id);
    }

    public Ramo actualizar(String id, Ramo datos) {
        Ramo existente = obtenerPorId(id);

        existente.setNombre(datos.getNombre());
        existente.setColor(datos.getColor());

        return ramoRepository.save(existente);
    }
}

