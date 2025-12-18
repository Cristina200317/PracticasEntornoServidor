package goya.daw2.D1_UD4;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class FormsControllerSession {

    private final RepositorioPersona repositorio;
    private final RepositorioPartida repositorioPartida;

    public FormsControllerSession(RepositorioPersona repositorio, RepositorioPartida repositorioPartida) {
        this.repositorio = repositorio;
        this.repositorioPartida = repositorioPartida;
    }

    static final String[] SIGNOS = { "", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio",
            "Sagitario", "Capricornio", "Acuario", "Piscis" };
    static final String[] AFICCIONES = { "Deportes", "Juerga", "Lectura", "Relaciones sociales" };
    static final int[] PUNTUACION = { 10, 20, 30, 40, 50 };

    @GetMapping("/sesiones")
    public String getEtapa1(HttpSession session, Model model) {
        model.addAttribute("numEtapa", 1);
        Object objNombre = session.getAttribute("nombre");
        if (objNombre != null) {
            model.addAttribute("nombre", objNombre.toString());
        }
        return "etapa1";
    }

    // POST para procesar cada etapa
    @PostMapping("/sesiones")
    public String procesaEtapa(@RequestParam(name = "numEtapa") Integer numEtapa,
                               @RequestParam(name = "nombre", required = false) String nombre,
                               @RequestParam(name = "signo", required = false) String signo,
                               @RequestParam(name = "aficciones", required = false) String[] aficciones,
                               HttpSession session, Model model) {

        model.addAttribute("signos", SIGNOS);
        model.addAttribute("aficciones", AFICCIONES);

        // Recuperar datos de sesión si no vienen en POST
        if (nombre == null || nombre.isBlank()) {
            Object obj = session.getAttribute("nombre");
            if (obj != null) nombre = obj.toString();
        } else session.setAttribute("nombre", nombre);

        if (signo == null) {
            Object obj = session.getAttribute("signo");
            if (obj != null) signo = obj.toString();
        } else session.setAttribute("signo", signo);

        if (aficciones == null) {
            Object obj = session.getAttribute("aficciones");
            if (obj != null && obj instanceof String[]) aficciones = (String[]) obj;
        } else session.setAttribute("aficciones", aficciones);

        model.addAttribute("nombre", nombre);
        model.addAttribute("signo", signo);
        model.addAttribute("signoSeleccionado", signo);
        model.addAttribute("aficcionesSeleccionadas", aficciones != null ? List.of(aficciones) : new ArrayList<>());
        model.addAttribute("numEtapa", numEtapa);

        // Validaciones
        String errores = "";
        if (numEtapa == 1 && (nombre == null || nombre.isBlank())) errores = "Debes poner un nombre no vacío";
        else if (numEtapa == 1 && (nombre.length() < 3 || nombre.length() > 10))
            errores = "La longitud del nombre debe estar entre 3 y 10";
        if (numEtapa == 2 && (signo == null || signo.equals("0"))) errores = "Debes seleccionar un signo";
        if (numEtapa == 3 && (aficciones == null || aficciones.length == 0))
            errores = "Debes elegir al menos una aficción";

        if (!errores.isBlank()) {
            model.addAttribute("errores", errores);
            return "etapa" + numEtapa;
        }

        // Avanzar etapa
        numEtapa++;
        model.addAttribute("numEtapa", numEtapa);

        if (numEtapa == 4) {
            int signoIndex = 0;
            try {
                signoIndex = Integer.parseInt(signo);
            } catch (NumberFormatException e) {
                signoIndex = 0;
            }

            // Calcular puntuación
            int puntuacion = 0;
            if (aficciones != null) {
                for (String afic : aficciones) {
                    for (int i = 0; i < AFICCIONES.length && i < PUNTUACION.length; i++) {
                        if (AFICCIONES[i].equals(afic)) puntuacion += PUNTUACION[i];
                    }
                }
            }

            // Crear persona nueva (siempre nueva) y guardar
            Persona persona = new Persona(nombre, puntuacion); // guardamos la puntuación real
            repositorio.save(persona);

            // Crear partida (resultado) y guardar
            Partida partida = new Partida(persona, puntuacion);
            persona.addPartida(partida);
            repositorioPartida.save(partida);

            // Preparar respuestas para mostrar
            List<String> respuestas = new ArrayList<>();
            respuestas.add(nombre);
            if (signoIndex > 0 && signoIndex < SIGNOS.length) respuestas.add(SIGNOS[signoIndex]);

            String mensajePuntuacion = switch (puntuacion) {
                case 10 -> "¡10 puntos! Eres de plata...";
                case 20 -> "¡20 puntos! Eres de bronce...";
                case 30 -> "¡30 puntos! Eres de oro...";
                case 40 -> "¡40 puntos! Diamante nivel experto...";
                case 50 -> "¡50 puntos! Leyenda del Quizz!";
                default -> "Tu puntuación: " + puntuacion;
            };
            respuestas.add(mensajePuntuacion);

            if (aficciones != null) respuestas.add(String.join(", ", aficciones));

            model.addAttribute("respuestas", respuestas);
        }

        return "etapa" + numEtapa;
    }
}
