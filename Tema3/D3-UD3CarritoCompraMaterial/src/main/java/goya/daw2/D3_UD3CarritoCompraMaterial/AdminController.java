package goya.daw2.D3_UD3CarritoCompraMaterial;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AdminController {

    private final RepositorioStockEnTxt repositorio = new RepositorioStockEnTxt();

    @GetMapping("/admin")
    public String mostrarStock(Model model) {
        Map<String, Integer> stock = repositorio.getAll();
        model.addAttribute("stock", stock);
        return "admin";
    }

    @PostMapping("/admin")
    public String actualizarStock(
            @RequestParam String producto,
            @RequestParam int cantidad,
            Model model) {

        // Actualizamos el stock con la nueva cantidad
        repositorio.modify(producto, cantidad);

        // Volvemos a mostrar el stock actualizado
        Map<String, Integer> stock = repositorio.getAll();
        model.addAttribute("stock", stock);
        model.addAttribute("mensaje", "Stock de " + producto + " actualizado a " + cantidad);

        return "admin";
    }
}
