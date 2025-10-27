package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario de la red social.
 * Contiene información básica y sus conexiones sociales.
 */
public class Usuario {
    private String id;
    private String nombre;
    private List<String> amigos;

    // Constructor
    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.amigos = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getAmigos() {
        return amigos;
    }

    // Método para agregar un amigo
    public void agregarAmigo(String amigoId) {
        if (!amigos.contains(amigoId)) {
            amigos.add(amigoId);
        }
    }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', nombre='" + nombre + "', amigos=" + amigos.size() + "}";
    }
}
