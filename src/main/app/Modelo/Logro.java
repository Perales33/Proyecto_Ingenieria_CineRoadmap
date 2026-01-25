public class Logro {

    private String nombreLogro;
    private String descripcion;
    private boolean completo;
    private Insignia insignia;
    private Reto reto;
    private ProgresoLogrosInsignias progreso;

    public Logro(String nombre, String descripcion, Insignia ins, Reto reto) {
        this.nombreLogro = nombre;
        this.descripcion = descripcion;
        this.insignia = ins;
        this.reto = reto;
        this.progreso = new ProgresoLogrosInsignias(0, reto.getProgresoObjetivo());
    }

    public void syncConReto() {
        progreso.setActual(reto.getProgresoActual());
        progreso.setObjetivo(reto.getProgresoObjetivo());

        if (reto.getEstado() == Reto.Estado.COMPLETADO) {
            completo = true;
            insignia.desbloquear();
        }
    }

    public int getProgresoActual() { return progreso.getActual(); }
    public int getProgresoObjetivo() { return progreso.getObjetivo(); }
    public boolean getCompleto() { return completo; }

    public Insignia getInsignia() { return insignia; }
    public String getDescripcion() { return descripcion; }
    public String getnombreReto() { return nombreLogro; }
}
