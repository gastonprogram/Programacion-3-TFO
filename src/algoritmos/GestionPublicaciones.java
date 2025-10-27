package algoritmos;

import modelo.PublicacionModelo;
import servicio.ServicioPublicaciones;
import java.util.*;

/**
 * Problema de Gestión de Publicaciones.
 * Da la posibilidad de ver publicaciones por dos diferentes tipos de orden:
 * cronológico y por relevancia.
 */
public class GestionPublicaciones {
    private ServicioPublicaciones servicioPublicaciones;

    public GestionPublicaciones(ServicioPublicaciones servicioPublicaciones) {
        this.servicioPublicaciones = servicioPublicaciones;
    }

    /**
     * muestra las publicaciones en orden cronológico (mas nuevas primero).
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
     * Muestra las publicaciones más relevantes usando un heap (cola de prioridad).
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
