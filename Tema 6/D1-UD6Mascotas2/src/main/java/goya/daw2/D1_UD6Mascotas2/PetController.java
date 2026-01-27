package goya.daw2.D1_UD6Mascotas2;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetRemoteService petRemoteService;

    public PetController(PetRemoteService petRemoteService) {
        this.petRemoteService = petRemoteService;
    }

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable Long id) {
        return petRemoteService.getPetById(id);
    }

    @GetMapping
    public List<Pet> getPets() {
        return petRemoteService.getAllPets();
    }
}
