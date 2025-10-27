import datos.GestorDatos;
import servicio.*;
import util.DatosIniciales;
import modelo.*;

import java.util.*;

/**
 * Aplicación principal de la Red Social - Sistema Integrado
 * Implementa todos los módulos de análisis y optimización de algoritmos.
 */
public class App {
    // Servicios compartidos
    private static GestorDatos gestorDatos;
    private static ServicioUsuarios servicioUsuarios;
    private static ServicioPublicaciones servicioPublicaciones;
    private static ServicioAnuncios servicioAnuncios;
    private static ServicioInteracciones servicioInteracciones;

    // Módulos de algoritmos
    private static algoritmos.GestionPublicaciones gestionPublicaciones;

    private static Scanner scanner;

    public static void main(String[] args) {
        inicializarSistema();
        mostrarBienvenida();

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    menuGestionUsuarios();
                    break;
                case 2:
                    menuGestionPublicaciones();
                    break;
                case 3:
                    menuGestionAnuncios();
                    break;
                case 4:
                    menuGestionInteracciones();
                    break;
                case 5:
                    ejecutarAsignacionPublicidad();
                    break;
                case 6:
                    ejecutarOptimizacionPortada();
                    break;
                case 7:
                    ejecutarRecomendacionAmigos();
                    break;
                case 8:
                    ejecutarRutasInfluencia();
                    break;
                case 9:
                    ejecutarVisualizacionPublicaciones();
                    break;
                case 10:
                    guardarTodosDatos();
                    break;
                case 0:
                    continuar = false;
                    despedida();
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            if (continuar && opcion != 0) {
                pausar();
            }
        }

        scanner.close();
    }

    private static void inicializarSistema() {
        System.out.println("Iniciando Sistema de Red Social...\n");

        gestorDatos = new GestorDatos();
        servicioUsuarios = new ServicioUsuarios(gestorDatos);
        servicioPublicaciones = new ServicioPublicaciones(gestorDatos);
        servicioAnuncios = new ServicioAnuncios(gestorDatos);
        servicioInteracciones = new ServicioInteracciones();

        gestionPublicaciones = new algoritmos.GestionPublicaciones(servicioPublicaciones);

        scanner = new Scanner(System.in);

        // Inicializar datos de prueba si es la primera vez
        DatosIniciales.inicializarSiEsNecesario(servicioUsuarios, servicioPublicaciones, servicioAnuncios);
    }

    private static void mostrarBienvenida() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE RED SOCIAL - TPO ALGORITMOS               ║");
        System.out.println("║     Análisis y Optimización con Algoritmos Avanzados     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n┌─────────────────── MENU PRINCIPAL ───────────────────┐");
        System.out.println("│                                                      │");
        System.out.println("│     1. Gestión de Usuarios                           │");
        System.out.println("│     2. Gestión de Publicaciones                      │");
        System.out.println("│     3. Gestión de Anuncios                           │");
        System.out.println("│     4. Gestión de Interacciones                      │");
        System.out.println("│                                                      │");
        System.out.println("│  ── Módulos de Algoritmos ──                         │");
        System.out.println("│     5. Asignación de Publicidad (Prog. Dinámica)     │");
        System.out.println("│     6. Optimización de Portada (Prog. Dinámica)      │");
        System.out.println("│     7. Recomendación de Amigos (Dijkstra)            │");
        System.out.println("│     8. Rutas de Influencia (Backtracking)            │");
        System.out.println("│     9. Visualización de Publicaciones (Heap)         │");
        System.out.println("│                                                      │");
        System.out.println("│     10. Guardar Datos                                │");
        System.out.println("│     0. Salir                                         │");
        System.out.println("└──────────────────────────────────────────────────────┘");
        System.out.print("Seleccione una opción: ");
    }

    // ==================== GESTIÓN DE USUARIOS ====================

    private static void menuGestionUsuarios() {
        System.out.println("\n┌─────── GESTIÓN DE USUARIOS ───────┐");
        System.out.println("│ 1. Listar usuarios                │");
        System.out.println("│ 2. Agregar usuario                │");
        System.out.println("│ 3. Agregar amistad                │");
        System.out.println("│ 0. Volver                         │");
        System.out.println("└───────────────────────────────────┘");
        System.out.print("Opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                servicioUsuarios.listarUsuarios();
                break;
            case 2:
                agregarUsuario();
                break;
            case 3:
                agregarAmistad();
                break;
            case 0:
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void agregarUsuario() {
        System.out.print("\nID del usuario: ");
        String id = scanner.nextLine();
        System.out.print("Nombre del usuario: ");
        String nombre = scanner.nextLine();

        servicioUsuarios.agregarUsuario(id, nombre);
    }

    private static void agregarAmistad() {
        System.out.print("\nID del primer usuario: ");
        String id1 = scanner.nextLine();
        System.out.print("ID del segundo usuario: ");
        String id2 = scanner.nextLine();

        servicioUsuarios.agregarAmistad(id1, id2);
    }

    // ==================== GESTIÓN DE PUBLICACIONES ====================

    private static void menuGestionPublicaciones() {
        System.out.println("\n┌─────── GESTION DE PUBLICACIONES ───────┐");
        System.out.println("│ 1. Listar publicaciones                │");
        System.out.println("│ 2. Agregar publicación                 │");
        System.out.println("│ 3. Dar like a publicación              │");
        System.out.println("│ 0. Volver                              │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("Opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                servicioPublicaciones.listarPublicaciones();
                break;
            case 2:
                agregarPublicacion();
                break;
            case 3:
                darLike();
                break;
            case 0:
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void agregarPublicacion() {
        System.out.print("\nID de la publicación: ");
        String id = scanner.nextLine();
        System.out.print("ID del autor: ");
        String autorId = scanner.nextLine();
        System.out.print("Contenido: ");
        String contenido = scanner.nextLine();

        PublicacionModelo pub = new PublicacionModelo(id, autorId, contenido, new Date(), 0);
        servicioPublicaciones.agregarPublicacion(pub);
    }

    private static void darLike() {
        System.out.print("\nID de la publicación: ");
        String id = scanner.nextLine();

        PublicacionModelo pub = servicioPublicaciones.obtenerPublicacion(id);
        if (pub != null) {
            pub.setLikes(pub.getLikes() + 1);
            System.out.println("Like agregado! Total: " + pub.getLikes() + " likes");
        } else {
            System.out.println("Publicación no encontrada.");
        }
    }

    // ==================== GESTIÓN DE ANUNCIOS ====================

    private static void menuGestionAnuncios() {
        System.out.println("\n┌─────── GESTION DE ANUNCIOS ───────┐");
        System.out.println("│ 1. Listar anuncios                │");
        System.out.println("│ 2. Agregar anuncio                │");
        System.out.println("│ 0. Volver                         │");
        System.out.println("└───────────────────────────────────┘");
        System.out.print("Opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                servicioAnuncios.listarAnuncios();
                break;
            case 2:
                agregarAnuncio();
                break;
            case 0:
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void agregarAnuncio() {
        System.out.print("\nNombre del anuncio: ");
        String nombre = scanner.nextLine();
        System.out.print("Costo: ");
        int costo = leerEntero();
        System.out.print("Alcance (usuarios): ");
        int alcance = leerEntero();

        servicioAnuncios.agregarAnuncio(new Anuncio(nombre, costo, alcance));
    }

    // ==================== GESTIÓN DE INTERACCIONES (LIKES) ====================

    private static void menuGestionInteracciones() {
        System.out.println("\n┌─────── GESTION DE INTERACCIONES (LIKES) ───────┐");
        System.out.println("│ 1. Ver interacciones de un usuario             │");
        System.out.println("│ 2. Registrar like a publicación                │");
        System.out.println("│ 3. Ver todas las interacciones                 │");
        System.out.println("│ 4. Analizar afinidad entre usuarios            │");
        System.out.println("│ 0. Volver                                      │");
        System.out.println("└────────────────────────────────────────────────┘");
        System.out.print("Opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                verInteraccionesDeUsuario();
                break;
            case 2:
                registrarLikeAPublicacion();
                break;
            case 3:
                verTodasLasInteracciones();
                break;
            case 4:
                analizarAfinidadEntreUsuarios();
                break;
            case 0:
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void verInteraccionesDeUsuario() {
        if (servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        servicioUsuarios.listarUsuarios();
        System.out.print("\nIngrese ID del usuario: ");
        String usuarioId = scanner.nextLine();

        List<Interaccion> interacciones = servicioInteracciones.getInteraccionesDeUsuario(usuarioId);

        System.out.println("\nLIKES DADOS POR " + usuarioId + ":");
        System.out.println("═══════════════════════════════════════");

        if (interacciones.isEmpty()) {
            System.out.println("Este usuario no ha dado likes aún.");
        } else {
            for (Interaccion interaccion : interacciones) {
                System.out.println("  • Publicación " + interaccion.getPublicacionId() +
                        " de " + interaccion.getAutorPublicacion());
            }
        }
    }

    private static void registrarLikeAPublicacion() {
        if (servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        if (servicioPublicaciones.obtenerTodasLasPublicaciones().isEmpty()) {
            System.out.println("No hay publicaciones disponibles.");
            return;
        }

        servicioUsuarios.listarUsuarios();
        System.out.print("\nUsuario que da el like: ");
        String usuarioId = scanner.nextLine();

        servicioPublicaciones.listarPublicaciones();
        System.out.print("\nID de la publicación: ");
        String publicacionId = scanner.nextLine();

        // Obtener autor de la publicación
        PublicacionModelo publicacion = servicioPublicaciones.obtenerPublicacion(publicacionId);
        if (publicacion != null) {
            servicioInteracciones.registrarLike(usuarioId, publicacionId, publicacion.getAutorId());
        } else {
            System.out.println("Publicación no encontrada.");
        }
    }

    private static void verTodasLasInteracciones() {
        List<Interaccion> todasLasInteracciones = servicioInteracciones.getTodasLasInteracciones();

        System.out.println("\nTODAS LAS INTERACCIONES (LIKES):");
        System.out.println("═══════════════════════════════════════");

        if (todasLasInteracciones.isEmpty()) {
            System.out.println("No hay interacciones registradas.");
        } else {
            for (Interaccion interaccion : todasLasInteracciones) {
                System.out.println("  • " + interaccion.getUsuarioId() +
                        " → like → Pub " + interaccion.getPublicacionId() +
                        " (de " + interaccion.getAutorPublicacion() + ")");
            }
        }
    }

    private static void analizarAfinidadEntreUsuarios() {
        if (servicioUsuarios.obtenerTodosLosUsuarios().size() < 2) {
            System.out.println("Se necesitan al menos 2 usuarios.");
            return;
        }

        servicioUsuarios.listarUsuarios();
        System.out.print("\nPrimer usuario: ");
        String usuario1 = scanner.nextLine();
        System.out.print("Segundo usuario: ");
        String usuario2 = scanner.nextLine();

        double afinidad = servicioInteracciones.calcularAfinidad(usuario1, usuario2);

        System.out.println("\nANALISIS DE AFINIDAD:");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Usuario 1: " + usuario1);
        System.out.println("Usuario 2: " + usuario2);
        System.out.println("Afinidad: " + String.format("%.2f", afinidad));

        if (afinidad >= 3.0) {
            System.out.println("Afinidad MUY ALTA - Excelente compatibilidad");
        } else if (afinidad >= 2.0) {
            System.out.println("Afinidad ALTA - Buena compatibilidad");
        } else if (afinidad >= 1.0) {
            System.out.println("Afinidad MODERADA - Cierta compatibilidad");
        } else {
            System.out.println("Afinidad BAJA - Poca compatibilidad detectada");
        }
    }

    // ==================== MÓDULOS DE ALGORITMOS ====================

    private static void ejecutarAsignacionPublicidad() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  ASIGNACION DE PUBLICIDAD - Programación Dinámica      ║");
        System.out.println("║  Problema de la Mochila 0/1                            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        List<Anuncio> anuncios = servicioAnuncios.obtenerTodosLosAnuncios();

        if (anuncios.isEmpty()) {
            System.out.println("No hay anuncios disponibles.");
            return;
        }

        servicioAnuncios.listarAnuncios();

        System.out.print("\nIngrese el presupuesto disponible: $");
        int presupuesto = leerEntero();

        algoritmos.AsignacionPublicidad.ResultadoAsignacion resultado = algoritmos.AsignacionPublicidad
                .maxAlcance(anuncios, presupuesto);

        resultado.mostrarResultado(presupuesto);
    }

    private static void ejecutarOptimizacionPortada() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  OPTIMIZACION DE PORTADA - Programación Dinámica       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        List<PublicacionModelo> publicaciones = servicioPublicaciones.obtenerTodasLasPublicaciones();

        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones disponibles.");
            return;
        }

        System.out.println("\nPublicaciones disponibles: " + publicaciones.size());
        System.out.print("Ingrese el espacio máximo de la portada: ");
        int espacioMaximo = leerEntero();

        algoritmos.OptimizacionPortada.ResultadoOptimizacion resultado = algoritmos.OptimizacionPortada
                .optimizar(publicaciones, espacioMaximo);

        resultado.mostrarResultado();
    }

    private static void ejecutarRecomendacionAmigos() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║  RECOMENDACION INTELIGENTE DE AMIGOS - Algoritmo de Dijkstra     ║");
        System.out.println("║     Basado en Análisis de Interacciones (Likes) y Afinidad       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");

        if (servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
            System.out.println("No hay usuarios disponibles.");
            return;
        }

        servicioUsuarios.listarUsuarios();

        System.out.print("\nIngrese el ID del usuario para recomendaciones: ");
        String usuarioId = scanner.nextLine();

        Usuario usuario = servicioUsuarios.obtenerUsuario(usuarioId);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        Map<String, List<String>> grafo = servicioUsuarios.obtenerGrafoAmistades();

        // Mostrar recomendaciones inteligentes usando Dijkstra + interacciones
        Map<String, String> nombres = obtenerMapaNombres();
        List<String> recomendaciones = algoritmos.RecomendacionAmigos.recomendarAmigos(grafo, usuarioId,
                servicioInteracciones);
        algoritmos.RecomendacionAmigos.mostrarRecomendaciones(usuario.getNombre(), recomendaciones, nombres);
    }

    private static void ejecutarRutasInfluencia() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  RUTAS DE INFLUENCIA - Backtracking con Poda           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        if (servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
            System.out.println("No hay usuarios disponibles.");
            return;
        }

        servicioUsuarios.listarUsuarios();

        System.out.print("\nID del usuario origen: ");
        String origen = scanner.nextLine();
        System.out.print("ID del usuario destino: ");
        String destino = scanner.nextLine();

        if (servicioUsuarios.obtenerUsuario(origen) == null ||
                servicioUsuarios.obtenerUsuario(destino) == null) {
            System.out.println("Uno o ambos usuarios no existen.");
            return;
        }

        Map<String, List<String>> grafo = servicioUsuarios.obtenerGrafoAmistades();
        
        // crear instancia de RutasInfluencia 
        algoritmos.RutasInfluencia rutasInfluencia = new algoritmos.RutasInfluencia();
        for (Map.Entry<String, List<String>> entry : grafo.entrySet()) {
            for (String destUsuario : entry.getValue()) {
                rutasInfluencia.agregarConexion(entry.getKey(), destUsuario);
            }
        }
        
        // cadena mas efectiva
        List<String> ruta = rutasInfluencia.buscarCadenas(origen, destino);

        Map<String, String> nombres = obtenerMapaNombres();
        algoritmos.RutasInfluencia.mostrarRuta(ruta, nombres);
    }

    private static void ejecutarVisualizacionPublicaciones() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  VISUALIZACION DE PUBLICACIONES                        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        System.out.println("\n1. Vista Cronológica");
        System.out.println("2. Vista por Relevancia (Heap)");
        System.out.print("Seleccione vista: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                gestionPublicaciones.mostrarVistaCronologica();
                break;
            case 2:
                System.out.print("¿Cuántas publicaciones mostrar?: ");
                int top = leerEntero();
                gestionPublicaciones.mostrarVistaPorRelevancia(top);
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
    // ==================== UTILIDADES ====================

    private static void guardarTodosDatos() {
        System.out.println("\nGuardando todos los datos...");
        servicioUsuarios.guardarUsuarios();
        servicioPublicaciones.guardarPublicaciones();
        servicioAnuncios.guardarAnuncios();
        System.out.println("Datos guardados correctamente.");
    }

    private static Map<String, String> obtenerMapaNombres() {
        Map<String, String> nombres = new HashMap<>();
        for (Usuario usuario : servicioUsuarios.obtenerTodosLosUsuarios().values()) {
            nombres.put(usuario.getId(), usuario.getNombre());
        }
        return nombres;
    }

    private static int leerOpcion() {
        try {
            String linea = scanner.nextLine();
            return Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int leerEntero() {
        try {
            String linea = scanner.nextLine();
            return Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Se usará 0.");
            return 0;
        }
    }

    private static void pausar() {
        System.out.print("\n[Presione ENTER para continuar]");
        scanner.nextLine();
    }

    private static void despedida() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  Guardando datos y cerrando el sistema...                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        guardarTodosDatos();
        System.out.println("\n¡Hasta pronto!\n");
    }
}
