package modelo;

import java.util.Date;

/**
 * Clase unificada que representa una publicación en la red social.
 * Contiene todos los atributos necesarios para los diferentes módulos.
 */
public class PublicacionModelo implements Comparable<PublicacionModelo> {
    private String id;
    private String autorId;
    private String contenido;
    private Date fecha;
    private int likes;
    private int tamano; // Para optimización de portada
    private int beneficio; // Para optimización de portada

    public PublicacionModelo(String id, String autorId, String contenido, Date fecha, int likes) {
        this.id = id;
        this.autorId = autorId;
        this.contenido = contenido;
        this.fecha = fecha;
        this.likes = likes;
        this.tamano = calcularTamano(contenido);
        this.beneficio = calcularBeneficio();
    }

    // Constructor con tamaño y beneficio personalizados
    public PublicacionModelo(String id, String autorId, String contenido, Date fecha, int likes, int tamano,
            int beneficio) {
        this.id = id;
        this.autorId = autorId;
        this.contenido = contenido;
        this.fecha = fecha;
        this.likes = likes;
        this.tamano = tamano;
        this.beneficio = beneficio;
    }

    /**
     * Calcula la relevancia ponderando likes y antigüedad.
     */
    public double getRelevancia() {
        long antiguedadDias = (new Date().getTime() - fecha.getTime()) / (1000 * 60 * 60 * 24);
        return likes - antiguedadDias * 0.5;
    }

    private int calcularTamano(String contenido) {
        // Tamaño basado en la longitud del contenido (normalizado)
        return Math.max(1, contenido.length() / 50);
    }

    private int calcularBeneficio() {
        // Beneficio basado en likes y relevancia
        return (int) (likes + getRelevancia() * 0.5);
    }

    @Override
    public int compareTo(PublicacionModelo otra) {
        // Orden descendente por relevancia
        return Double.compare(otra.getRelevancia(), this.getRelevancia());
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getAutorId() {
        return autorId;
    }

    public String getContenido() {
        return contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getLikes() {
        return likes;
    }

    public int getTamano() {
        return tamano;
    }

    public int getBeneficio() {
        return beneficio;
    }

    public void setLikes(int likes) {
        this.likes = likes;
        this.beneficio = calcularBeneficio(); // Recalcular beneficio
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public void setBeneficio(int beneficio) {
        this.beneficio = beneficio;
    }

    @Override
    public String toString() {
        return String.format("Post[%s] - Autor: %s | Likes: %d | Relevancia: %.2f | Tamaño: %d | Beneficio: %d",
                id, autorId, likes, getRelevancia(), tamano, beneficio);
    }
}
