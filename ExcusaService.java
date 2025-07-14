package ar.edu.davinci.excuse_system.service;

import ar.edu.davinci.excuse_system.model.DTO.ExcusaDTO;
import ar.edu.davinci.excuse_system.model.empleado.Empleado;
import ar.edu.davinci.excuse_system.model.excusa.Excusa;
import ar.edu.davinci.excuse_system.model.excusa.enums.Estado;
import ar.edu.davinci.excuse_system.model.excusa.tipoDeExcusa.TipoDeExcusa;
import ar.edu.davinci.excuse_system.model.excusa.tipoDeExcusa.enums.TipoExcusa;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.davinci.excuse_system.service.EmpleadoService.getEmpleados;

@Service
public class ExcusaService {
    private List<Excusa> excusas = new ArrayList<>();
    List<Empleado> empleados = getEmpleados();

    public List<Excusa> obtenerExcusas() {
        return excusas;
    }

    public List<Excusa> obtenerExcusasPorLegajo(Integer legajo) {
        validarLegajo(legajo);
        return excusas.stream()
                .filter(excusa -> excusa.obtenerLagajoEmpleado() == legajo)
                .toList();
    }

    public List<Excusa> obtenerExcusasPorFiltro(Integer legajo, Date desde, Date hasta) {
        validarLegajo(legajo);
        validarFechas(desde, hasta);

        return excusas.stream()
                .filter(excusa -> excusa.obtenerLagajoEmpleado() == legajo
                        && !excusa.getFecha().before(desde)
                        && !excusa.getFecha().after(hasta))
                .collect(Collectors.toList());
    }

    public List<Excusa> obtenerExcusasRechazadas() {
        return excusas.stream()
                .filter(excusa -> excusa.getEstado() == Estado.RECHAZADA)
                .collect(Collectors.toList());
    }

    public Excusa crearExcusa(ExcusaDTO excusaDTO) {
        validarExcusaDTO(excusaDTO);
        Empleado empleadoEncontrado = empleados.stream()
                .filter(empleado -> empleado.getLegajo() == excusaDTO.getLegajo())
                .findFirst()
                .orElse(null);

        if (empleadoEncontrado == null) {
            throw new IllegalArgumentException("Empleado con legajo " + excusaDTO.getLegajo() + " no encontrado");
        }

        TipoDeExcusa tipoDeExcusa;
        try {
            TipoExcusa tipoExcusaEnum = TipoExcusa.valueOf(excusaDTO.getTipoDeExcusa().toUpperCase());
            tipoDeExcusa = tipoExcusaEnum.crearTipo();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de excusa inválido: " + excusaDTO.getTipoDeExcusa());
        }

        Excusa excusa = new Excusa(empleadoEncontrado, tipoDeExcusa, excusaDTO.getFecha(), Estado.PENDIENTE, excusaDTO.getMensaje());
        excusas.add(excusa);
        return excusa;

    }


    public int eliminarExcusasAnteriores(Date fechaLimite) {
        if (fechaLimite == null) {
            throw new IllegalArgumentException("La fecha límite no puede ser nula");
        }

        int cantidadInicial = excusas.size();
        excusas = excusas.stream()
                .filter(excusa -> !excusa.getFecha().before(fechaLimite))
                .collect(Collectors.toList());
        return cantidadInicial - excusas.size();
    }

    private void validarLegajo(Integer legajo) {
        if (legajo == null || legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser un número positivo");
        }
    }

    private void validarFechas(Date desde, Date hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas 'desde' y 'hasta' no pueden ser nulas");
        }
        if (desde.after(hasta)) {
            throw new IllegalArgumentException("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
    }

    private void validarExcusaDTO(ExcusaDTO excusaDTO) {
        if (excusaDTO == null) {
            throw new IllegalArgumentException("Los datos de la excusa no pueden ser nulos");
        }
        if (excusaDTO.getLegajo() == null || excusaDTO.getLegajo() <= 0) {
            throw new IllegalArgumentException("El legajo debe ser un número positivo");
        }
        if (excusaDTO.getTipoDeExcusa() == null || excusaDTO.getTipoDeExcusa().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de excusa no puede estar vacío");
        }
        if (excusaDTO.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        if (excusaDTO.getMensaje() == null || excusaDTO.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
    }

    private TipoDeExcusa crearTipoDeExcusa(String tipoDeExcusaStr) {
        try {
            TipoExcusa tipoDeExcusaEnum = TipoExcusa.valueOf(tipoDeExcusaStr.toUpperCase());
            return tipoDeExcusaEnum.crearTipo();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de excusa no válido: " + tipoDeExcusaStr);
        }
    }

}
