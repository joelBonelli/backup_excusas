package ar.edu.davinci.excuse_system.controller;

import ar.edu.davinci.excuse_system.model.DTO.EmpleadoDTO;
import ar.edu.davinci.excuse_system.model.empleado.Empleado;
import ar.edu.davinci.excuse_system.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.davinci.excuse_system.service.EmpleadoService.empleados;
@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Empleado>> obtenerEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @PostMapping("/")
    public ResponseEntity<Empleado> agregarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleadoCreado = empleadoService.crearEmpleado(empleadoDTO);
        //String resultado = empleadoService.crearEmpleado(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreado);
    }



}
