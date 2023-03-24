package co.edu.uniandes.dse.gym.exceptions;

public final class ErrorMessage {
	public static final String SERVICIO_NOT_FOUND = "No se encontró el servicio";
	public static final String SEDE_NOT_FOUND = "No se encontró la sede";
	public static final String RESTRICCION_NOT_FOUND = "No se encontro la restricción";
	public static final String RESENIA_NOT_FOUND = "No se encontro la resenia";
	public static final String PLAN_ENTRENAMIENTO_NOT_FOUND = "";
	public static final String ENTRENADOR_NOT_FOUND = "No se encontro el entrenador";
    public static final String DEPORTOLOGO_NOT_FOUND = "No se encontro el deportologo";
    public static final String CONVENIO_NOT_FOUND = "";
    public static final String ATLETA_NOT_FOUND = "No se encontro el atleta";
    public static final String ACTIVIDAD_NOT_FOUND = "No se encontro la actividad";
	


	private ErrorMessage() {
		throw new IllegalStateException("Utility class");
	}
}