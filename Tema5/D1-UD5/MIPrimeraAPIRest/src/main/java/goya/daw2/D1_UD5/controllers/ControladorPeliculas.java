package goya.daw2.D1_UD5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import goya.daw2.D1_UD5.model.Pelicula;
import goya.daw2.D1_UD5.services.ServicioPeliculas;

@RestController
@RequestMapping("/peliculas")
public class ControladorPeliculas {

	private ServicioPeliculas servicioPeliculas;

	public ControladorPeliculas(ServicioPeliculas servicioPeliculas) {
		this.servicioPeliculas = servicioPeliculas;
	}

	// GET ALL
	@GetMapping
	public ResponseEntity<List<Pelicula>> listAll() {
		return ResponseEntity.ok(servicioPeliculas.findAll());
	}

	// GET BY ID
	@GetMapping("/{id}")
	public ResponseEntity<Pelicula> listOne(@PathVariable Long id) {
		Optional<Pelicula> peliculaOpt = servicioPeliculas.findById(id);

		if (peliculaOpt.isPresent()) {
			return ResponseEntity.ok(peliculaOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	// POST (no permitir duplicados)
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pelicula pelicula) {

		if (servicioPeliculas.existsPelicula(pelicula)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("La película ya existe");
		}

		Pelicula creada = servicioPeliculas.save(pelicula);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}

	// PUT (no permitir actualizar si no existe)
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@PathVariable Long id,
			@RequestBody Pelicula pelicula) {

		Optional<Pelicula> peliculaOpt = servicioPeliculas.findById(id);

		if (!peliculaOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		pelicula.setId(id);
		Pelicula actualizada = servicioPeliculas.save(pelicula);
		return ResponseEntity.ok(actualizada);
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		Optional<Pelicula> peliculaOpt = servicioPeliculas.findById(id);

		if (!peliculaOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		servicioPeliculas.delete(peliculaOpt.get());
		return ResponseEntity.noContent().build();
	}
}
