package goya.daw2.D2_UD3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MiAplicacion {
    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena,
            HttpSession session,
            HttpServletRequest request,
            Model model) {

        // Comprobación de usuarios fijos
        if ((usuario.equals("cris") && contrasena.equals("1234")) ||
            (usuario.equals("user") && contrasena.equals("abcd"))) {

            session.setAttribute("usuario", usuario);
            model.addAttribute("usuario", usuario);

            // Revisar la cabecera Referer 
            String referer = (String) session.getAttribute("referer");
            session.removeAttribute("referer"); // Limpiamos 

            if (referer != null && !referer.contains("/login")) {
                return "redirect:" + referer;
            }

            model.addAttribute("pagina", "inicio");
            return "plantilla";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("usuario", usuario); // mantiene el nombre en el formulario
            return "login";
        }
    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model, HttpServletRequest request) {
        return paginaProtegida("inicio", session, model, request);
    }

    @GetMapping("/pagina1")
    public String pagina1(HttpSession session, Model model, HttpServletRequest request) {
        return paginaProtegida("página 1", session, model, request);
    }

    @GetMapping("/pagina2")
    public String pagina2(HttpSession session, Model model, HttpServletRequest request) {
        return paginaProtegida("página 2", session, model, request);
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Método auxiliar para páginas protegidas
    private String paginaProtegida(String nombrePagina, HttpSession session, Model model, HttpServletRequest request) {
        String usuario = (String) session.getAttribute("usuario");
        if (usuario == null) {
            // Guardamos la página para redirigir del login
            session.setAttribute("referer", request.getRequestURI());
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("pagina", nombrePagina);
        return "plantilla";
    }
}
