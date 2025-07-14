package ar.edu.davinci.excuse_system.controller;

import ar.edu.davinci.excuse_system.model.empleado.prontuario.Prontuario;
import ar.edu.davinci.excuse_system.service.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    @Autowired
    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Prontuario>> obtenerProntuarios() {
        List<Prontuario> prontuarios = prontuarioService.obtenerProntuarios();
        return ResponseEntity.ok(prontuarios);
    }
}
