package ar.edu.davinci.excuse_system.service;

import ar.edu.davinci.excuse_system.model.empleado.prontuario.Prontuario;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProntuarioService {

    List<Prontuario> prontuarios = new ArrayList<>();

    public List<Prontuario> obtenerProntuarios() {
        return prontuarios;
    }
}
