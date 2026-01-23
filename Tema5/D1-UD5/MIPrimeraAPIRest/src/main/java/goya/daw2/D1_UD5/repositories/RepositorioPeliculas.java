package goya.daw2.D1_UD5.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import goya.daw2.D1_UD5.model.Pelicula;

@Repository
public interface RepositorioPeliculas extends CrudRepository<Pelicula, Long> {
	boolean existsPeliculaByNombreAndDirector(String nombre, String director);
}
