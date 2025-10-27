package servicio;

import modelo.*;
import datos.GestorDatos;
import java.util.*;

/**
 * Servicio para gestionar interacciones (likes) entre usuarios y publicaciones.
 * Este servicio es clave para el algoritmo de recomendación basado en afinidad.
 */
public class ServicioInteracciones {
    private List<Interaccion> interacciones;
    private GestorDatos gestorDatos;

    //Constructor
    public ServicioInteracciones() {
        this.gestorDatos = new GestorDatos();
        this.interacciones = gestorDatos.cargarInteracciones();

        // Si no hay datos, generar algunas interacciones de ejemplo
        if (this.interacciones.isEmpty()) {
            generarInteraccionesEjemplo();
        }
    }

    /**
     * Registra un like de un usuario a una publicación.
     */
    public void registrarLike(String usuarioId, String publicacionId, String autorPublicacion) {
        // Verificar que no exista ya esta interacción
        boolean yaExiste = interacciones.stream()
                .anyMatch(i -> i.getUsuarioId().equals(usuarioId) &&
                        i.getPublicacionId().equals(publicacionId));

        if (!yaExiste) {
            Interaccion nuevaInteraccion = new Interaccion(usuarioId, publicacionId, autorPublicacion);
            interacciones.add(nuevaInteraccion);
            gestorDatos.guardarInteracciones(interacciones);
            System.out.println(
                    usuarioId + " le dio like a la publicación " + publicacionId + " de " + autorPublicacion);
        } else {
            System.out.println("El usuario " + usuarioId + " ya le dio like a esta publicación.");
        }
    }

    /**
     * Obtiene todas las interacciones de un usuario (qué publicaciones le
     * gustaron).
     */
    public List<Interaccion> getInteraccionesDeUsuario(String usuarioId) {
        return interacciones.stream()
                .filter(i -> i.getUsuarioId().equals(usuarioId))
                .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
    }

    /**
     * Obtiene cuántos likes recibió un autor de otros usuarios.
     */
    public Map<String, Integer> getLikesRecibidosPorAutor(String autorId) {
        Map<String, Integer> likesRecibidos = new HashMap<>();

        for (Interaccion interaccion : interacciones) {
            if (interaccion.getAutorPublicacion().equals(autorId)) {
                String usuarioQueGusto = interaccion.getUsuarioId();
                likesRecibidos.put(usuarioQueGusto,
                        likesRecibidos.getOrDefault(usuarioQueGusto, 0) + 1);
            }
        }

        return likesRecibidos;
    }

    /**
     * Calcula la afinidad entre dos usuarios basada en sus interacciones.
     * Mayor afinidad = menor distancia en el algoritmo de Dijkstra.
     */
    public double calcularAfinidad(String usuario1, String usuario2) {
        // Caso 1: Likes que usuario1 dio a publicaciones de usuario2
        long likesDirectos = interacciones.stream()
                .filter(i -> i.getUsuarioId().equals(usuario1) &&
                        i.getAutorPublicacion().equals(usuario2))
                .count();

        // Caso 2: Likes que usuario2 dio a publicaciones de usuario1
        long likesReversos = interacciones.stream()
                .filter(i -> i.getUsuarioId().equals(usuario2) &&
                        i.getAutorPublicacion().equals(usuario1))
                .count();

        // Caso 3: Interacciones mutuas (ambos gustan contenido similar)
        Set<String> autoresQueGustaUsuario1 = interacciones.stream()
                .filter(i -> i.getUsuarioId().equals(usuario1))
                .map(Interaccion::getAutorPublicacion)
                .collect(HashSet::new, (set, item) -> set.add(item), (set1, set2) -> set1.addAll(set2));

        Set<String> autoresQueGustaUsuario2 = interacciones.stream()
                .filter(i -> i.getUsuarioId().equals(usuario2))
                .map(Interaccion::getAutorPublicacion)
                .collect(HashSet::new, (set, item) -> set.add(item), (set1, set2) -> set1.addAll(set2));

        // Calcular intersección (autores que les gustan a ambos)
        long gustosComunes = autoresQueGustaUsuario1.stream()
                .filter(autoresQueGustaUsuario2::contains)
                .count();

        // Fórmula de afinidad: más interacciones = más afinidad
        double afinidad = (likesDirectos * 2.0) + (likesReversos * 2.0) + (gustosComunes * 1.0);

        return afinidad;
    }

    /**
     * Obtiene todas las interacciones.
     */
    public List<Interaccion> getTodasLasInteracciones() {
        return new ArrayList<>(interacciones);
    }

    /**
     * Genera algunas interacciones de ejemplo para demostrar el algoritmo.
     */
    private void generarInteraccionesEjemplo() {
        // Simulación de interacciones
        interacciones.add(new Interaccion("U001", "P001", "U002"));
        interacciones.add(new Interaccion("U001", "P003", "U003"));
        interacciones.add(new Interaccion("U002", "P002", "U003"));
        interacciones.add(new Interaccion("U002", "P004", "U004"));
        interacciones.add(new Interaccion("U003", "P001", "U002"));
        interacciones.add(new Interaccion("U003", "P005", "U005"));
        interacciones.add(new Interaccion("U004", "P003", "U003"));
        interacciones.add(new Interaccion("U005", "P002", "U003"));
        interacciones.add(new Interaccion("U005", "P001", "U002"));

        gestorDatos.guardarInteracciones(interacciones);
        System.out.println("Generadas " + interacciones.size() + " interacciones de ejemplo para el algoritmo.");
    }
}