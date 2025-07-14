package ar.edu.davinci.excuse_system.controller;


import ar.edu.davinci.excuse_system.model.DTO.EncargadoDTO;
import ar.edu.davinci.excuse_system.model.empleado.Empleado;
import ar.edu.davinci.excuse_system.model.empleado.encargado.Encargado;
import ar.edu.davinci.excuse_system.model.empleado.encargado.LineaDeEncargados;
import ar.edu.davinci.excuse_system.model.empleado.encargado.tiposDeEncargado.enums.TipoEncargado;
import ar.edu.davinci.excuse_system.service.EncargadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/encargados")
public class EncargadoController {

    private final EncargadoService encargadoService;

    @Autowired
    public EncargadoController(EncargadoService encargadoService) {
        this.encargadoService = encargadoService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Encargado>> obtenerEncargados() {
        List<Encargado> encargados = encargadoService.obtenerEncargados();
        return ResponseEntity.ok(encargados);
    }

    @PostMapping("/")
    public ResponseEntity<Encargado> agregarEncargado(@RequestBody EncargadoDTO encargadoDTO) {
        Encargado encargadoCreado = encargadoService.crearEncargado(encargadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(encargadoCreado);
    }


    @PutMapping("/modo/{legajo}")
    public ResponseEntity<String> cambiarModoEncargado(@PathVariable Integer legajo, @RequestParam String modo){
        encargadoService.cambiarModoEncargado(legajo, modo);
        return ResponseEntity.ok("Modo cambiado exitosamente a: " + modo);
    }


}
