package ar.edu.davinci.excuse_system.service;

import ar.edu.davinci.excuse_system.model.DTO.EmpleadoDTO;
import ar.edu.davinci.excuse_system.model.DTO.EncargadoDTO;
import ar.edu.davinci.excuse_system.model.empleado.encargado.Encargado;
import ar.edu.davinci.excuse_system.model.empleado.encargado.tiposDeEncargado.enums.TipoEncargado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EncargadoService {
    List<Encargado> encargados = new ArrayList<>();

    public List<Encargado> obtenerEncargados() {
        return encargados;
    }

    public Encargado crearEncargado(EncargadoDTO encargadoDTO) {
        validarDTO(encargadoDTO);
        try {
            boolean legajoExistente = encargados.stream()
                .anyMatch(encargado -> encargado.getLegajo() == encargadoDTO.getLegajo());
            if (legajoExistente) {
                throw new IllegalArgumentException("El legajo " + encargadoDTO.getLegajo() + " ya existe");
            }
            TipoEncargado tipo = TipoEncargado.valueOf(encargadoDTO.getTipoDeEncargado().toUpperCase());
            Encargado encargado = tipo.crearEncargado(
                    encargadoDTO.getNombre(),
                    encargadoDTO.getEmail(),
                    encargadoDTO.getLegajo(),
                    null
        );
            boolean agregado = encargados.add(encargado);

            if (!agregado) {
                throw new RuntimeException("No se pudo agregar el encargado a la lista");
            }

            return encargado;
        } catch (IllegalArgumentException e) {
            // Re-lanzamos las excepciones de validación
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al crear encargado", e);
        }
    }

    public void cambiarModoEncargado(Integer legajo, String modo) {
        if (legajo == null || legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser un número positivo");
        }

        if (modo == null || modo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modo no puede estar vacío");
        }

        try {
            Encargado encargadoEncontrado = encargados.stream()
                    .filter(encargado -> encargado.getLegajo() == legajo)
                    .findFirst()
                    .orElse(null);

            if (encargadoEncontrado == null) {
                throw new IllegalArgumentException("Encargado no encontrado con legajo: " + legajo);
            }

            encargadoEncontrado.cambiarModoDeResolver(modo);
        } catch (IllegalArgumentException e) {
            // Re-lanzamos las excepciones de validación
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al cambiar modo del encargado", e);
        }

    }




    private void validarDTO(EncargadoDTO encargadoDTO) {
        if (encargadoDTO == null) {
            throw new IllegalArgumentException("Los datos del empleado no pueden ser nulos");
        }

        if (encargadoDTO.getNombre() == null || encargadoDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (encargadoDTO.getEmail() == null || encargadoDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        if (encargadoDTO.getLegajo() <= 0) {
            throw new IllegalArgumentException("El legajo debe ser un número positivo");
        }

        if (!encargadoDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }
    }


}
