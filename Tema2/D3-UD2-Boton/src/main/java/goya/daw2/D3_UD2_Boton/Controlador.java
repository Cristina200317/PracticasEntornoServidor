package goya.daw2.D3_UD2_Boton;

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
        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.trim().equals("")) {
            errores.add("El nombre es obligatorio");
            model.addAttribute("errores", errores);
            return "nombre";
        }
        model.addAttribute("nombre", nombre);
        return "url2";
    }

    @GetMapping("/url3")
    public String url3(@RequestParam("nombre") String nombre,
                       @RequestParam("opcion") String opcion,
                       Model model) {

        List<String> errores = new ArrayList<>();
        if (opcion == null || opcion.trim().equals("")) {
            errores.add("El género es obligatorio");
        }
        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("nombre", nombre);
            return "url2";
        }
        model.addAttribute("nombre", nombre);
        model.addAttribute("opcion", opcion);
        return "url3";
    }

    @GetMapping("/url4")
    public String url4(@RequestParam("nombre") String nombre,
                       @RequestParam("opcion") String opcion,
                       @RequestParam(value = "opciones", required = false) List<String> opciones,
                       @RequestParam(value = "rangoEdad", required = false) String rangoEdad,
                       Model model) {

    	List<String> errores = new ArrayList<>();
    	if (rangoEdad == null || rangoEdad.trim().equals("")) {
    	    errores.add("El rango de edad es obligatorio");
    	}


        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("nombre", nombre);
            model.addAttribute("opcion", opcion);
            model.addAttribute("opciones", opciones);
            model.addAttribute("rangoEdad", rangoEdad);
            return "url4";
        }

        model.addAttribute("nombre", nombre);
        model.addAttribute("opcion", opcion);
        model.addAttribute("opciones", opciones);
        model.addAttribute("rangoEdad", rangoEdad);

        return "url4"; // mostrar el formulario de selección de idiomas y rango de edad
    }

    @GetMapping("/url5")
    public String url5(@RequestParam("nombre") String nombre,
                       @RequestParam("opcion") String opcion,
                       @RequestParam(value="opciones", required=false) List<String> opciones,
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
            datos.add("Idiomas seleccionados: Ninguno");
        }

        datos.add("Rango de edad: " + rangoEdad);

        model.addAttribute("datos", datos);
        model.addAttribute("nombre", nombre);
        model.addAttribute("opcion", opcion);
        model.addAttribute("opciones", opciones);
        model.addAttribute("rangoEdad", rangoEdad);

        return "url5";
    }
}
