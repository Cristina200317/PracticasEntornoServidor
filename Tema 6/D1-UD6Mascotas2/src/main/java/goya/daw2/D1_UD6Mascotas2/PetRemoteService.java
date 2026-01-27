package goya.daw2.D1_UD6Mascotas2;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PetRemoteService {

    private final WebClient client;

    public PetRemoteService(WebClient.Builder builder) {
        this.client = builder
                .baseUrl("https://petstore-demo-endpoint.execute-api.com")
                .build();
    }

    public Pet getPetById(Long id) {
        return client.get()
                .uri("/petstore/pets/{id}", id)
                .retrieve()
                .bodyToMono(Pet.class)
                .block();
    }

    public List<Pet> getAllPets() {
        Pet[] pets = client.get()
                .uri("/petstore/pets")
                .retrieve()
                .bodyToMono(Pet[].class)
                .block();

        return Arrays.asList(pets);
    }
}
