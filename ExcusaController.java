package ar.edu.davinci.excuse_system.controller;

import ar.edu.davinci.excuse_system.model.DTO.ExcusaDTO;
import ar.edu.davinci.excuse_system.model.excusa.Excusa;
import ar.edu.davinci.excuse_system.service.ExcusaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excusas")
public class ExcusaController {

    private ExcusaService excusaService;

    @Autowired
    public ExcusaController(ExcusaService excusaService) {
        this.excusaService = excusaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Excusa>> obtenerExcusas() {
        List<Excusa> excusas = excusaService.obtenerExcusas();
        return ResponseEntity.ok(excusas);
    }


    @GetMapping("/{legajo}")
    public ResponseEntity<List<Excusa>> obtenerExcusasPorLegajo(@PathVariable Integer legajo) {
        List<Excusa> excusas = excusaService.obtenerExcusasPorLegajo(legajo);
        return ResponseEntity.ok(excusas);
    }

    @GetMapping("/busqueda")
    public ResponseEntity<List<Excusa>> obtenerExcusasPorFiltro(
            @RequestParam Integer legajo,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date desde,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date hasta) {
        List<Excusa> excusasFiltradas = excusaService.obtenerExcusasPorFiltro(legajo, desde, hasta);
        return ResponseEntity.ok(excusasFiltradas);
    }

    @GetMapping("/rechazadas")
    public ResponseEntity<List<Excusa>> obtenerExcusasRechazadas() {
        List<Excusa> excusasRechazadas = excusaService.obtenerExcusasRechazadas();
        return ResponseEntity.ok(excusasRechazadas);
    }

    @PostMapping("/")
    public ResponseEntity<?> crearExcusa(@RequestBody ExcusaDTO excusaDTO){
        Excusa excusa = excusaService.crearExcusa(excusaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(excusa);
    }


    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarExcusa(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaLimite) {
        int cantidadEliminadas = excusaService.eliminarExcusasAnteriores(fechaLimite);
        return ResponseEntity.ok("Se eliminaron " + cantidadEliminadas + " excusas anteriores a la fecha límite.");
    }


}
