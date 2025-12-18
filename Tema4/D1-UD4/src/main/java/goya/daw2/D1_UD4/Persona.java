package goya.daw2.D1_UD4;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private Integer puntuacion;
	
	@OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
	private List<Partida> partidas = new ArrayList<>();

	public List<Partida> getPartidas() {
	    return partidas;
	}

	public void addPartida(Partida partida) {
	    partidas.add(partida);
	    partida.setPersona(this);
	}

	public Persona() {
	}

	public Persona(String nombre, Integer puntuacion) {
		this.nombre = nombre;
		this.puntuacion = puntuacion;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", puntuacion=" + puntuacion + "]";
	}
}
