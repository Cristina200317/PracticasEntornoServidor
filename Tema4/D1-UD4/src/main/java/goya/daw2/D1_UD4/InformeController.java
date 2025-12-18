package goya.daw2.D1_UD4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/informe")
public class InformeController {

    private final RepositorioPartida repositorioPartida;

    public InformeController(RepositorioPartida repositorioPartida) {
        this.repositorioPartida = repositorioPartida;
    }

    // Mostrar todas las partidas ordenadas de mayor a menor
    @GetMapping
    public String mostrarInforme(Model model) {
        List<Partida> partidas = repositorioPartida.findAll();

        List<Partida> ordenadas = new ArrayList<>();
        while (!partidas.isEmpty()) {
            Partida max = partidas.get(0);
            for (Partida p : partidas) {
                if (p.getPuntuacion() > max.getPuntuacion()) {
                    max = p;
                }
            }
            ordenadas.add(max);
            partidas.remove(max);
        }

        model.addAttribute("partidas", ordenadas);
        return "informe";
    }

    // Mostrar solo las 5 puntuaciones mayores
    @GetMapping("/top5")
    public String top5(Model model) {
        List<Partida> partidas = repositorioPartida.findAll();
        List<Partida> top = new ArrayList<>();

        // Ordenar de mayor a menor 
        while (!partidas.isEmpty()) {
            Partida max = partidas.get(0);
            for (Partida p : partidas) {
                if (p.getPuntuacion() > max.getPuntuacion()) {
                    max = p;
                }
            }
            top.add(max);
            partidas.remove(max);
        }

        // Solo 5
        List<Partida> top5 = new ArrayList<>();
        for (int i = 0; i < 5 && i < top.size(); i++) {
            top5.add(top.get(i));
        }

        model.addAttribute("partidas", top5);
        return "informe";
    }

    // Mostrar partidas con puntuación mayor que un valor
    @GetMapping("/filtrar")
    public String filtrarPorPuntuacion(@RequestParam("valor") int valor, Model model) {
        List<Partida> partidas = repositorioPartida.findAll();
        List<Partida> filtradas = new ArrayList<>();

        for (Partida p : partidas) {
            if (p.getPuntuacion() > valor) {
                filtradas.add(p);
            }
        }

        // Ordenar de mayor a menor
        List<Partida> ordenadas = new ArrayList<>();
        while (!filtradas.isEmpty()) {
            Partida max = filtradas.get(0);
            for (Partida p : filtradas) {
                if (p.getPuntuacion() > max.getPuntuacion()) {
                    max = p;
                }
            }
            ordenadas.add(max);
            filtradas.remove(max);
        }

        model.addAttribute("partidas", ordenadas);
        return "informe";
    }

    @GetMapping("/borrar")
    public String borrar(@RequestParam("id") int id) {
        repositorioPartida.deleteById(id);
        return "redirect:/informe";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam("id") int id, @RequestParam("puntuacion") int puntuacion) {
        Partida partida = repositorioPartida.findById(id).orElse(null);
        if (partida != null) {
            partida.setPuntuacion(puntuacion);
            // Actualizar categoría según nueva puntuación
            partida.setCategoria(Categoria.fromPuntuacion(puntuacion));
            repositorioPartida.save(partida);
        }
        return "redirect:/informe";
    }
}
