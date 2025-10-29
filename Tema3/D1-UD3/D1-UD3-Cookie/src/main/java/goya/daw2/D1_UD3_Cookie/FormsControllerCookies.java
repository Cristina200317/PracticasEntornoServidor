package goya.daw2.D1_UD3_Cookie;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FormsControllerCookies {

	static final String[] SIGNOS = { "", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio",
			"Sagitario", "Capricornio", "Acuario", "Piscis" };

	static final String[] AFICCIONES = { "Deportes", "Juerga", "Lectura", "Relaciones sociales" };

	@GetMapping("/cookies")
	String getEtapa1(@CookieValue(value = "nombre", required = false) String nombreCookie, Model model) {
		model.addAttribute("numEtapa", 1);
		if (nombreCookie != null) {
			model.addAttribute("nombre", nombreCookie);
		}
		return "etapa1";
	}

	@PostMapping("/cookies")
	String procesaEtapa(@RequestParam(name = "numEtapa") Integer numEtapa,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "signo", required = false) String signo,
			@RequestParam(name = "aficciones", required = false) String[] aficciones,
			@CookieValue(value = "nombre", required = false) String nombreCookie,
			@CookieValue(value = "signo", required = false) String signoCookie,
			@CookieValue(value = "aficciones", required = false) String aficcionesCookie, HttpServletResponse response,
			Model modelo) {

		modelo.addAttribute("signos", SIGNOS);
		modelo.addAttribute("aficciones", AFICCIONES);

		// Nombre
		if (nombre == null || nombre.isBlank()) {
			nombre = nombreCookie;
		} else {
			Cookie cookieNombre = new Cookie("nombre", nombre);
			cookieNombre.setMaxAge(3600);
			cookieNombre.setHttpOnly(true);
			cookieNombre.setPath("/cookies");
			response.addCookie(cookieNombre);
		}

		// Signo
		if (signo == null) {
			signo = signoCookie;
		} else {
			Cookie cookieSigno = new Cookie("signo", signo);
			cookieSigno.setMaxAge(3600);
			cookieSigno.setHttpOnly(true);
			cookieSigno.setPath("/cookies");
			response.addCookie(cookieSigno);
		}

		// Aficciones
		if (aficciones == null) {
			if (aficcionesCookie != null) {
				aficciones = aficcionesCookie.split(",");
			}
		} else {
			Cookie cookieAficciones = new Cookie("aficciones", String.join(",", aficciones));
			cookieAficciones.setMaxAge(3600);
			cookieAficciones.setHttpOnly(true);
			cookieAficciones.setPath("/cookies");
			response.addCookie(cookieAficciones);
		}

		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("signo", signo);
		modelo.addAttribute("numEtapa", numEtapa);

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
			modelo.addAttribute("errores", errores);
			return "etapa" + numEtapa;
		}

		numEtapa++;
		modelo.addAttribute("numEtapa", numEtapa);

		if (numEtapa == 4) {
			List<String> respuestas = new ArrayList<>();
			respuestas.add(nombre);
			respuestas.add(SIGNOS[Integer.parseInt(signo)]);
			if (aficciones == null)
				aficciones = new String[] {};
			respuestas.add(String.join(", ", aficciones));
			modelo.addAttribute("respuestas", respuestas);
		}
		return "etapa" + numEtapa;
	}
}
