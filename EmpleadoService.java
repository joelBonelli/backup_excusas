package ar.edu.davinci.excuse_system.service;

import ar.edu.davinci.excuse_system.model.DTO.EmpleadoDTO;
import ar.edu.davinci.excuse_system.model.empleado.Empleado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoService {

    public static List<Empleado> empleados = new ArrayList<>();

    public List<Empleado> obtenerEmpleados() {
        return empleados;
    }

    public Empleado crearEmpleado(EmpleadoDTO empleadoDTO) {
        validarDTO(empleadoDTO);
        try{
            boolean legajoExistente = empleados.stream()
                .anyMatch(empleado -> empleado.getLegajo() == empleadoDTO.getLegajo());
            if (legajoExistente) {
                throw new IllegalArgumentException("El legajo " + empleadoDTO.getLegajo() + " ya existe");
            }
            Empleado empleado = new Empleado(
                    empleadoDTO.getNombre().trim(),
                    empleadoDTO.getEmail().trim(),
                    empleadoDTO.getLegajo()
            );

            boolean agregado = empleados.add(empleado);

            if (!agregado) {
                throw new RuntimeException("No se pudo agregar el empleado a la lista");
            }

            return empleado;
        } catch (IllegalArgumentException e) {
            // Re-lanzamos las excepciones de validación
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al crear empleado", e);
        }
    }



    private void validarDTO(EmpleadoDTO empleadoDTO) {
        if (empleadoDTO == null) {
            throw new IllegalArgumentException("Los datos del empleado no pueden ser nulos");
        }

        if (empleadoDTO.getNombre() == null || empleadoDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (empleadoDTO.getEmail() == null || empleadoDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        if (empleadoDTO.getLegajo() <= 0) {
            throw new IllegalArgumentException("El legajo debe ser un número positivo");
        }

        if (!empleadoDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }
    }



    public static List<Empleado> getEmpleados() {
        return empleados;
    }
}
