package servicio;

import modelo.PublicacionModelo;
import datos.GestorDatos;
import java.util.*;

/**
 * Servicio que gestiona las publicaciones de la red social.
 */
public class ServicioPublicaciones {
    private List<PublicacionModelo> publicaciones;
    private GestorDatos gestorDatos;

    public ServicioPublicaciones(GestorDatos gestorDatos) {
        this.gestorDatos = gestorDatos;
        this.publicaciones = gestorDatos.cargarPublicaciones();
    }

    public void guardarPublicaciones() {
        gestorDatos.guardarPublicaciones(publicaciones);
    }

    public void agregarPublicacion(PublicacionModelo publicacion) {
        publicaciones.add(publicacion);
        System.out.println("Publicación agregada: " + publicacion.getId());
    }

    public List<PublicacionModelo> obtenerTodasLasPublicaciones() {
        return new ArrayList<>(publicaciones);
    }

    public PublicacionModelo obtenerPublicacion(String id) {
        return publicaciones.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Vista cronológica: ordena por fecha.
     */
    public List<PublicacionModelo> obtenerVistaCronologica() {
        List<PublicacionModelo> cronologica = new ArrayList<>(publicaciones);
        cronologica.sort(Comparator.comparing(PublicacionModelo::getFecha).reversed());
        return cronologica;
    }

    /**
     * Vista por relevancia usando Heap (PriorityQueue).
     */
    public List<PublicacionModelo> obtenerTopPorRelevancia(int top) {
        // heap costo: extraer: O(log n), insertar: O(log n), construir: O(n), total O(n
        // + k log n)
        PriorityQueue<PublicacionModelo> heap = new PriorityQueue<>(publicaciones);
        List<PublicacionModelo> topRelevantes = new ArrayList<>();

        for (int i = 0; i < top && !heap.isEmpty(); i++) {
            topRelevantes.add(heap.poll());
        }

        return topRelevantes;
    }

    public void listarPublicaciones() {
        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones disponibles.");
            return;
        }

        System.out.println("\nLISTA DE PUBLICACIONES:");
        System.out.println("═══════════════════════════════════════════════════════════════");
        for (PublicacionModelo pub : publicaciones) {
            System.out.println(pub);
        }
    }
}
