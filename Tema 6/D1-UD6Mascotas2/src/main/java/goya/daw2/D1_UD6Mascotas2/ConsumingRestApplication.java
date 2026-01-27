package goya.daw2.D1_UD6Mascotas2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ConsumingRestApplication {

  private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ConsumingRestApplication.class, args);
  }
  
  @Bean
  public WebClient.Builder webClientBuilder() {
      return WebClient.builder();
  }

  @Bean
  @Profile("!test")
  public ApplicationRunner run(WebClient.Builder builder) {
	  WebClient webClient = builder.baseUrl("http://petstore-demo-endpoint.execute-api.com").build();
    return args -> {
      Pet pet = webClient
          .get().
          uri("/petstore/pets/{id}",1)
          .retrieve()
          .bodyToMono(Pet.class)
          .block();
      log.info(pet.toString());
    };
  }
}