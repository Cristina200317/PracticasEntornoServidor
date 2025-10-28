package goya.daw2.Idioma;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class ControladorSaludo {

    private final MessageSource mensajes;

    public ControladorSaludo(MessageSource mensajes) {
        this.mensajes = mensajes;
    }

    @GetMapping("/saludo")
    public String mostrarSaludo(Locale locale, Model model) {
        String mensaje = mensajes.getMessage("saludo", null, locale);
        model.addAttribute("mensajeSaludo", mensaje);
        return "saludo";
    }
}
