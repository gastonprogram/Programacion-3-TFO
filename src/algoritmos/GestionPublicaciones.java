package algoritmos;

import modelo.PublicacionModelo;
import servicio.ServicioPublicaciones;
import java.util.*;

/**
 * Módulo de Gestión de Publicaciones.
 * Proporciona vistas cronológicas y por relevancia.
 */
public class GestionPublicaciones {
    private ServicioPublicaciones servicioPublicaciones;

    public GestionPublicaciones(ServicioPublicaciones servicioPublicaciones) {
        this.servicioPublicaciones = servicioPublicaciones;
    }

    /**
     * Muestra las publicaciones en orden cronológico (más recientes primero).
     */
    public void mostrarVistaCronologica() {
        List<PublicacionModelo> cronologica = servicioPublicaciones.obtenerVistaCronologica();

        System.out.println("\nVISTA CRONOLÓGICA DE PUBLICACIONES");
        System.out.println("═══════════════════════════════════════════════════════");

        if (cronologica.isEmpty()) {
            System.out.println("No hay publicaciones disponibles.");
            return;
        }

        for (PublicacionModelo pub : cronologica) {
            System.out.println("  • " + pub.getId() +
                    " | Fecha: " + pub.getFecha() +
                    " | Likes: " + pub.getLikes());
        }
    }

    /**
     * Muestra las publicaciones más relevantes usando un Heap (PriorityQueue).
     */
    public void mostrarVistaPorRelevancia(int top) {
        List<PublicacionModelo> topRelevantes = servicioPublicaciones.obtenerTopPorRelevancia(top);

        System.out.println("\nTOP " + top + " PUBLICACIONES POR RELEVANCIA");
        System.out.println("═══════════════════════════════════════════════════════");

        if (topRelevantes.isEmpty()) {
            System.out.println("No hay publicaciones disponibles.");
            return;
        }

        int posicion = 1;
        for (PublicacionModelo pub : topRelevantes) {
            System.out.println(posicion + ". " + pub.getId() +
                    " | Likes: " + pub.getLikes() +
                    " | Relevancia: " + String.format("%.2f", pub.getRelevancia()));
            posicion++;
        }
    }
}
