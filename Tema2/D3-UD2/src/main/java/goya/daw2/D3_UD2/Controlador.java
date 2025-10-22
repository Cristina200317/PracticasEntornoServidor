package goya.daw2.D3_UD2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Controlador {
	@GetMapping("/nombre")
	public String nombre() {
		return "nombre";
	}

	@GetMapping("/url2")
	public String url2(@RequestParam("nombre") String nombre, Model model) {
		model.addAttribute("nombre", nombre);
		return "url2";
	}

	@GetMapping("/url3")
	public String url3(@RequestParam("opcion") String opcion, @RequestParam("nombre") String nombre, Model model) {
		model.addAttribute("opcion", opcion);
		model.addAttribute("nombre", nombre);
		return "url3";
	}

	@GetMapping("/url4")
	public String url4(@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "opcion", required = false) String opcion,
			@RequestParam(name = "opciones", required = false) List<String> opciones,
			@RequestParam(name = "rangoEdad", required = false) String rangoEdad, Model model) {
		model.addAttribute("nombre", nombre);
		model.addAttribute("opcion", opcion);
		model.addAttribute("opciones", opciones);
		model.addAttribute("rangoEdad", rangoEdad);
		return "url4";
	}

	@GetMapping("/url5")
	public String url5(@RequestParam("nombre") String nombre,
	                   @RequestParam("opcion") String opcion,
	                   @RequestParam(name="opciones", required = false) List<String> opciones,
	                   @RequestParam("rangoEdad") String rangoEdad,
	                   Model model) {
	    List<String> datos = new ArrayList<>();
	    datos.add("Nombre: " + nombre);
	    datos.add("Género: " + opcion);

	    if (opciones != null && !opciones.isEmpty()) {
	        for (String idioma : opciones) {
	            datos.add("Idioma: " + idioma);
	        }
	    } else {
	        datos.add("Idiomas seleccionados: No hay idiomas seleccionados");
	    }

	    datos.add("Rango de edad: " + rangoEdad);

	    model.addAttribute("datos", datos);

	    return "url5";
	}
}