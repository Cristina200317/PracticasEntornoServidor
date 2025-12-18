package goya.daw2.D1_UD4;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime fecha;
	private Integer puntuacion;

	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "persona_id")
	private Persona persona;

	public Partida() {
	}

	public Partida(Persona persona, Integer puntuacion) {
		this.persona = persona;
		this.puntuacion = puntuacion;
		this.fecha = LocalDateTime.now();
		this.categoria = Categoria.fromPuntuacion(puntuacion);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", fecha=" + fecha + ", puntuacion=" + puntuacion + ", categoria=" + categoria
				+ ", persona=" + persona + "]";
	}

}
