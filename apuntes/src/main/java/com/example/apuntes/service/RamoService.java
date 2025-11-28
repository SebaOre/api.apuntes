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

    public List<Ramo> listar() {
        return ramoRepository.findAll();
    }

    public Ramo crear(Ramo ramo) {
        return ramoRepository.save(ramo);
    }

    public void eliminar(String id) {
        ramoRepository.deleteById(id);
    }

    public Ramo actualizar(String id, Ramo datos) {
        Ramo existente = ramoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ramo no encontrado"));

        existente.setNombre(datos.getNombre());
        existente.setColor(datos.getColor());

        return ramoRepository.save(existente);
    }
}
