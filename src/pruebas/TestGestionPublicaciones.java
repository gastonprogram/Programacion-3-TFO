package pruebas;

import servicio.ServicioPublicaciones;
import modelo.PublicacionModelo;
import datos.GestorDatos;
import java.util.*;

public class TestGestionPublicaciones {
    public static void main(String[] args) {
        // GestorDatos "fake" que devuelve una lista inicial
        GestorDatos gestor = new GestorDatos() {
            @Override public List<PublicacionModelo> cargarPublicaciones() {
                return new ArrayList<>(Arrays.asList(
                    new PublicacionModelo("P1", "3", "Educativo", new Date(), 50),
                    new PublicacionModelo("P2", "4", "Juegos", new Date(), 60),
                    new PublicacionModelo("P3", "2", "Entretenimiento", new Date(), 30),
                    new PublicacionModelo("P4", "5", "Deportes", new Date(), 90)
                ));
            }
            @Override public void guardarPublicaciones(List<PublicacionModelo> pubs) {
                System.out.println("Guardadas " + pubs.size() + " publicaciones.");
            }
        };

        ServicioPublicaciones servicio = new ServicioPublicaciones(gestor);

        // Agregar una más
        servicio.agregarPublicacion(new PublicacionModelo(
            "P4", "5", "Deportes", new Date(), 90
        ));

        // Vista cronológica
        System.out.println("\n--- Vista cronológica ---");
        for (var p : servicio.obtenerVistaCronologica()) {
            System.out.println(p.getId() + " | " + p.getFecha());
        }

        // Top por relevancia (k=2)
        System.out.println("\n--- Top por relevancia (2) ---");
        for (var p : servicio.obtenerTopPorRelevancia(2)) {
            System.out.println(p.getId());
        }

        // Guardar (simulado)
        servicio.guardarPublicaciones();
    }
}

