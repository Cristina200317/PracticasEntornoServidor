package goya.daw2.D1_UD4;

public enum Categoria {
	BRONCE, PLATA, ORO;

	public static Categoria fromPuntuacion(int puntuacion) {
		if (puntuacion <= 20)
			return BRONCE;
		else if (puntuacion <= 40)
			return PLATA;
		else
			return ORO;
	}
}
