package modelo;

/**
 * Modelo de Interacción (Like) entre usuario y publicación.
 * Representa cuando un usuario da like a una publicación.
 */
public class Interaccion {
    private String usuarioId;
    private String publicacionId;
    private String autorPublicacion;
    private long timestamp;

    public Interaccion() {
    }

    //Constructor
    public Interaccion(String usuarioId, String publicacionId, String autorPublicacion) {
        this.usuarioId = usuarioId;
        this.publicacionId = publicacionId;
        this.autorPublicacion = autorPublicacion;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters y Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(String publicacionId) {
        this.publicacionId = publicacionId;
    }

    public String getAutorPublicacion() {
        return autorPublicacion;
    }

    public void setAutorPublicacion(String autorPublicacion) {
        this.autorPublicacion = autorPublicacion;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Interaccion{usuarioId='" + usuarioId + "', publicacionId='" + publicacionId +
                "', autorPublicacion='" + autorPublicacion + "'}";
    }
}