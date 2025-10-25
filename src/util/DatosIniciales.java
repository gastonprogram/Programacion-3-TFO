package util;

import modelo.*;
import servicio.*;
import java.util.Date;

/**
 * Clase utilitaria para inicializar datos de prueba en el sistema.
 */
public class DatosIniciales {

        public static void inicializarSiEsNecesario(ServicioUsuarios servicioUsuarios,
                        ServicioPublicaciones servicioPublicaciones,
                        ServicioAnuncios servicioAnuncios) {

                // Verificar si ya hay datos
                if (!servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
                        System.out.println("ℹ️ Datos existentes encontrados. No se inicializan datos de prueba.");
                        return;
                }

                System.out.println("🔧 Inicializando datos de prueba...");

                // Crear usuarios
                servicioUsuarios.agregarUsuario("U001", "Ana García");
                servicioUsuarios.agregarUsuario("U002", "Beto Fernández");
                servicioUsuarios.agregarUsuario("U003", "Carla López");
                servicioUsuarios.agregarUsuario("U004", "Diego Martínez");
                servicioUsuarios.agregarUsuario("U005", "Eva Rodríguez");
                servicioUsuarios.agregarUsuario("U006", "Franco Silva");
                servicioUsuarios.agregarUsuario("U007", "Gisela Torres");
                servicioUsuarios.agregarUsuario("U008", "Hugo Vargas");

                // Crear amistades (red conectada)
                servicioUsuarios.agregarAmistad("U001", "U002"); // Ana - Beto
                servicioUsuarios.agregarAmistad("U001", "U003"); // Ana - Carla
                servicioUsuarios.agregarAmistad("U002", "U004"); // Beto - Diego
                servicioUsuarios.agregarAmistad("U002", "U005"); // Beto - Eva
                servicioUsuarios.agregarAmistad("U003", "U006"); // Carla - Franco
                servicioUsuarios.agregarAmistad("U004", "U007"); // Diego - Gisela
                servicioUsuarios.agregarAmistad("U005", "U006"); // Eva - Franco
                servicioUsuarios.agregarAmistad("U006", "U008"); // Franco - Hugo
                servicioUsuarios.agregarAmistad("U007", "U008"); // Gisela - Hugo

                // Crear publicaciones con diferentes fechas y características
                long ahora = System.currentTimeMillis();

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P001", "U001", "Mi primera publicación en la red!",
                                                new Date(ahora - 5_000_000), 120, 3, 150));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P002", "U002", "Compartiendo conocimiento sobre programación",
                                                new Date(ahora - 8_000_000), 250, 4, 300));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P003", "U003", "Foto del día 📸",
                                                new Date(ahora - 2_000_000), 80, 2, 100));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P004", "U004", "Reflexión del fin de semana",
                                                new Date(ahora - 10_000_000), 350, 5, 400));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P005", "U005", "Nuevo artículo en mi blog",
                                                new Date(ahora - 1_000_000), 45, 1, 60));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P006", "U006", "Tutorial de algoritmos",
                                                new Date(ahora - 6_000_000), 180, 3, 220));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P007", "U007", "Evento próximo en la universidad",
                                                new Date(ahora - 3_000_000), 95, 2, 120));

                servicioPublicaciones.agregarPublicacion(
                                new PublicacionModelo("P008", "U008", "Logro desbloqueado! 🎯",
                                                new Date(ahora - 4_000_000), 200, 3, 250));

                // Crear anuncios publicitarios
                servicioAnuncios.agregarAnuncio(new Anuncio("Banner Principal", 5, 100));
                servicioAnuncios.agregarAnuncio(new Anuncio("Video Promocional", 8, 180));
                servicioAnuncios.agregarAnuncio(new Anuncio("Pop-up Interactivo", 3, 60));
                servicioAnuncios.agregarAnuncio(new Anuncio("Story Patrocinada", 4, 90));
                servicioAnuncios.agregarAnuncio(new Anuncio("Banner Lateral", 2, 40));
                servicioAnuncios.agregarAnuncio(new Anuncio("Video Corto", 6, 130));

                // Guardar todos los datos
                servicioUsuarios.guardarUsuarios();
                servicioPublicaciones.guardarPublicaciones();
                servicioAnuncios.guardarAnuncios();

                System.out.println("✅ Datos de prueba inicializados correctamente.");
                System.out.println("   - " + servicioUsuarios.obtenerTodosLosUsuarios().size() + " usuarios creados");
                System.out.println(
                                "   - " + servicioPublicaciones.obtenerTodasLasPublicaciones().size()
                                                + " publicaciones creadas");
                System.out.println("   - " + servicioAnuncios.obtenerTodosLosAnuncios().size() + " anuncios creados");
        }
}
