package goya.daw2.D4_UD2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FormularioController {

	 // Página1 con formularios y enlace de prueba
	@GetMapping("/")
	public String index(Model modelo) {
		String caracteres = "ñ, á, é, í, ó, ú, /, \\, ', \", <b>html</b>";
		String caracteresCodificado = URLEncoder.encode(caracteres, StandardCharsets.UTF_8);

		modelo.addAttribute("caracteres", caracteres);
		modelo.addAttribute("caracteresCodificado", caracteresCodificado);
		return "pagina1";
	}

	// Procesa formulario POST
	@PostMapping("/formPost")
	public String procesaPost(@RequestParam("nombre") String nombre, @RequestParam("edad") String edad,
			@RequestParam("texto") String texto, Model modelo) {

		modelo.addAttribute("metodo", "POST");
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("texto", texto);
		return "resultado";
	}

	// Procesa formulario GET / enlace
	@GetMapping("/formGet")
	public String procesaGet(@RequestParam("nombre") String nombre, @RequestParam("edad") String edad,
			@RequestParam("texto") String texto, Model modelo) {

		modelo.addAttribute("metodo", "GET");
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("edad", edad);
		modelo.addAttribute("texto", texto);
		return "resultado";
	}
}
