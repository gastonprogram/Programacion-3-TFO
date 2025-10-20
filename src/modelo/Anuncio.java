package modelo;

/**
 * Clase que representa un anuncio publicitario.
 */
public class Anuncio {
    private String nombre;
    private int costo;
    private int alcance;

    public Anuncio(String nombre, int costo, int alcance) {
        this.nombre = nombre;
        this.costo = costo;
        this.alcance = alcance;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCosto() {
        return costo;
    }

    public int getAlcance() {
        return alcance;
    }

    @Override
    public String toString() {
        return String.format("Anuncio{nombre='%s', costo=%d, alcance=%d}", nombre, costo, alcance);
    }
}
