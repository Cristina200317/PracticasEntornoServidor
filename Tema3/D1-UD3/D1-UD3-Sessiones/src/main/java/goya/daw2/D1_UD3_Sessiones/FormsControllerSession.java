package goya.daw2.D1_UD3_Sessiones;

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

	static final String[] SIGNOS = { "", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio",
			"Sagitario", "Capricornio", "Acuario", "Piscis" };
	static final String[] AFICCIONES = { "Deportes", "Juerga", "Lectura", "Relaciones sociales" };

	@GetMapping("/sesiones")
	public String getEtapa1(HttpSession session, Model model) {
		model.addAttribute("numEtapa", 1);
		Object objNombre = session.getAttribute("nombre");
		if (objNombre != null) {
			model.addAttribute("nombre", objNombre.toString());
		}
		return "etapa1";
	}

	@PostMapping("/sesiones")
	public String procesaEtapa(@RequestParam(name = "numEtapa") Integer numEtapa,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "signo", required = false) String signo,
			@RequestParam(name = "aficciones", required = false) String[] aficciones, HttpSession session,
			Model model) {

		model.addAttribute("signos", SIGNOS);
		model.addAttribute("aficciones", AFICCIONES);

		// Nombre
		if (nombre == null || nombre.isBlank()) {
			Object obj = session.getAttribute("nombre");
			if (obj != null) {
				nombre = obj.toString();
			}
		} else {
			session.setAttribute("nombre", nombre);
		}

		// Signo
		if (signo == null) {
			Object obj = session.getAttribute("signo");
			if (obj != null) {
				signo = obj.toString();
			}
		} else {
			session.setAttribute("signo", signo);
		}

		// Aficciones
		if (aficciones == null) {
			Object obj = session.getAttribute("aficciones");
			if (obj != null && obj instanceof String[]) {
				aficciones = (String[]) obj;
			}
		} else {
			session.setAttribute("aficciones", aficciones);
		}

		model.addAttribute("nombre", nombre);
		model.addAttribute("signo", signo);
		model.addAttribute("numEtapa", numEtapa);

		// Validaciones
		String errores = "";
		if (numEtapa == 1 && (nombre == null || nombre.isBlank())) {
			errores = "Debes poner un nombre no vacío";
		} else if (numEtapa == 1 && (nombre.length() < 3 || nombre.length() > 10)) {
			errores = "La longitud del nombre debe estar entre 3 y 10";
		}
		if (numEtapa == 2 && (signo == null || signo.equals("0"))) {
			errores = "Debes seleccionar un signo";
		}
		if (numEtapa == 3 && (aficciones == null || aficciones.length == 0)) {
			errores = "Debes elegir al menos una aficción, no seas soso/a";
		}

		if (!errores.isBlank()) {
			model.addAttribute("errores", errores);
			return "etapa" + numEtapa;
		}

		// Avanzar etapa
		numEtapa++;
		model.addAttribute("numEtapa", numEtapa);

		if (numEtapa == 4) {
			List<String> respuestas = new ArrayList<>();
			respuestas.add(nombre);
			respuestas.add(SIGNOS[Integer.parseInt(signo)]);
			if (aficciones == null) {
				aficciones = new String[0];
			}
			respuestas.add(String.join(", ", aficciones));

			model.addAttribute("respuestas", respuestas);
		}
		return "etapa" + numEtapa;
	}
}
