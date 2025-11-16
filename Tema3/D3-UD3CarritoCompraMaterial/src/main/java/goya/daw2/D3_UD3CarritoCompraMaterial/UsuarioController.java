package goya.daw2.D3_UD3CarritoCompraMaterial;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @GetMapping("/")
    public String pedirNombre() {
        return "usuario"; 
    }

    @PostMapping("/guardarNombre")
    public String guardarNombre(@RequestParam("nombre") String nombre, HttpSession session) {
        session.setAttribute("nombreUsuario", nombre);
        return "redirect:/carrito";
    }
}
