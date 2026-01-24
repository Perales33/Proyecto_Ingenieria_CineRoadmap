package main.app.Modelo;

public class ProgresoLogrosInsignias {

    private int actual;
    private int objetivo;

    public ProgresoLogrosInsignias(int actual, int objetivo) {
        this.actual = actual;
        this.objetivo = objetivo;
    }

    // GETTERS
    public int getActual() {
        return actual;
    }

    public int getObjetivo() {
        return objetivo;
    }

    // SETTERS
    public void setActual(int actual) {
        this.actual = actual;
    }

    public void setObjetivo(int objetivo) {
        this.objetivo = objetivo;
    }

    // Métodos útiles
    public int getPercent() {
        if (objetivo == 0) return 0;
        return (int) ((actual * 100.0) / objetivo);
    }

    public String getText() {
        return actual + "/" + objetivo;
    }
}

