package goya.daw2.D3_UD3CarritoCompraMaterial;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class CarritoController {

    private final RepositorioStockEnTxt repositorio = new RepositorioStockEnTxt();

    @GetMapping("/carrito")
    public String mostrarCarrito(Model model, HttpSession session) {
        // obtenemos todo el stock de golpe
        Map<String, Integer> stock = repositorio.getAll();
        model.addAttribute("stock", stock);

        // saludo con el nombre guardado en sesión
        String nombre = (String) session.getAttribute("nombreUsuario");
        model.addAttribute("nombreUsuario", nombre);

     // inicializamos cantidades y total
        model.addAttribute("lapices", session.getAttribute("lapices") != null ? session.getAttribute("lapices") : 0);
        model.addAttribute("cuadernos", session.getAttribute("cuadernos") != null ? session.getAttribute("cuadernos") : 0);
        model.addAttribute("boligrafos", session.getAttribute("boligrafos") != null ? session.getAttribute("boligrafos") : 0);
        model.addAttribute("total", session.getAttribute("total") != null ? session.getAttribute("total") : 0.0);

        return "carrito"; 
    }
    
    @PostMapping("/carrito")
    public String actualizarCarrito(
            @RequestParam("lapices") int lapices,
            @RequestParam("cuadernos") int cuadernos,
            @RequestParam("boligrafos") int boligrafos,
            Model model,
            HttpSession session) {

    	 Map<String, Integer> stock = repositorio.getAll();

         int cantidadLapices = Math.min(lapices, stock.get("lapices"));
         int cantidadCuadernos = Math.min(cuadernos, stock.get("cuadernos"));
         int cantidadBoligrafos = Math.min(boligrafos, stock.get("boligrafos"));

         double total = cantidadLapices * 0.5 + cantidadCuadernos * 2.0 + cantidadBoligrafos * 1.0;

         // guardar en sesión
         session.setAttribute("lapices", cantidadLapices);
         session.setAttribute("cuadernos", cantidadCuadernos);
         session.setAttribute("boligrafos", cantidadBoligrafos);
         session.setAttribute("total", total);
         
        // saludo
        String nombre = (String) session.getAttribute("nombreUsuario");
        model.addAttribute("nombreUsuario", nombre);

        // Pasar datos al modelo
        model.addAttribute("stock", stock);
        model.addAttribute("lapices", cantidadLapices);
        model.addAttribute("cuadernos", cantidadCuadernos);
        model.addAttribute("boligrafos", cantidadBoligrafos);
        model.addAttribute("total", total);

        return "carrito";
    }
    
    @PostMapping("/finalizar")
    public String finalizarCompra(HttpSession session, Model model) {
        int lapices = (int) session.getAttribute("lapices");
        int cuadernos = (int) session.getAttribute("cuadernos");
        int boligrafos = (int) session.getAttribute("boligrafos");

        Map<String, Integer> stock = repositorio.getAll();

        if (lapices <= stock.get("lapices") &&
            cuadernos <= stock.get("cuadernos") &&
            boligrafos <= stock.get("boligrafos")) {

            repositorio.modify("lapices", stock.get("lapices") - lapices);
            repositorio.modify("cuadernos", stock.get("cuadernos") - cuadernos);
            repositorio.modify("boligrafos", stock.get("boligrafos") - boligrafos);

            String nombre = (String) session.getAttribute("nombreUsuario");

            // Escribir en log
            escribirLog(nombre, lapices, cuadernos, boligrafos);

            // Pasar datos a la vista de confirmación
            model.addAttribute("nombreUsuario", nombre);
            model.addAttribute("lapices", lapices);
            model.addAttribute("cuadernos", cuadernos);
            model.addAttribute("boligrafos", boligrafos);
            model.addAttribute("total", lapices * 0.5 + cuadernos * 2.0 + boligrafos * 1.0);

            // Reset carrito en sesión
            session.setAttribute("lapices", 0);
            session.setAttribute("cuadernos", 0);
            session.setAttribute("boligrafos", 0);
            session.setAttribute("total", 0.0);

            return "confirmacion"; 
        } else {
            model.addAttribute("mensaje", "No se puede realizar la compra por falta de stock.");
            model.addAttribute("stock", stock);

            String nombre = (String) session.getAttribute("nombreUsuario");
            model.addAttribute("nombreUsuario", nombre);

            return "carrito"; 
        }
    }

    // Método auxiliar para escribir en el fichero de log
    private void escribirLog(String nombre, int lapices, int cuadernos, int boligrafos) {
        try (PrintWriter out = new PrintWriter(new FileWriter("compras.log", true))) {
            String fechaHora = java.time.LocalDateTime.now().toString();
            out.printf("%s | Usuario: %s | Lápices: %d | Cuadernos: %d | Bolígrafos: %d%n",
                       fechaHora, nombre, lapices, cuadernos, boligrafos);
        } catch (IOException e) {
            System.err.println("Error escribiendo en el log de compras");
        }
    }
}