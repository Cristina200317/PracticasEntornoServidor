package goya.daw2.D1_UD5.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import goya.daw2.D1_UD5.model.Pelicula;
import goya.daw2.D1_UD5.repositories.RepositorioPeliculas;

@Service
public class ServicioPeliculas {
	private RepositorioPeliculas repositorioPeliculas;

	public ServicioPeliculas(RepositorioPeliculas repositorioPeliculas) {
		this.repositorioPeliculas = repositorioPeliculas;
	}

	public List<Pelicula> findAll() {
		return (List<Pelicula>) repositorioPeliculas.findAll();
	}

	public Optional<Pelicula> findById(Long id) {
		return repositorioPeliculas.findById(id);
	}

	public boolean existsPelicula(Pelicula pelicula) {
		return repositorioPeliculas.existsPeliculaByNombreAndDirector(pelicula.getNombre(), pelicula.getDirector());
	}

	public Pelicula save(Pelicula pelicula) {
		return repositorioPeliculas.save(pelicula);
	}

	public void delete(Pelicula pelicula) {
		repositorioPeliculas.delete(pelicula);
	}
}
