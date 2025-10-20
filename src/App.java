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
                    ejecutarAsignacionPublicidad();
                    break;
                case 5:
                    ejecutarOptimizacionPortada();
                    break;
                case 6:
                    ejecutarRecomendacionAmigos();
                    break;
                case 7:
                    ejecutarRutasInfluencia();
                    break;
                case 8:
                    ejecutarVisualizacionPublicaciones();
                    break;
                case 9:
                    guardarTodosDatos();
                    break;
                case 0:
                    continuar = false;
                    despedida();
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }

            if (continuar && opcion != 0) {
                pausar();
            }
        }

        scanner.close();
    }

    private static void inicializarSistema() {
        System.out.println("🚀 Iniciando Sistema de Red Social...\n");

        gestorDatos = new GestorDatos();
        servicioUsuarios = new ServicioUsuarios(gestorDatos);
        servicioPublicaciones = new ServicioPublicaciones(gestorDatos);
        servicioAnuncios = new ServicioAnuncios(gestorDatos);

        gestionPublicaciones = new algoritmos.GestionPublicaciones(servicioPublicaciones);

        scanner = new Scanner(System.in);

        // Inicializar datos de prueba si es la primera vez
        DatosIniciales.inicializarSiEsNecesario(servicioUsuarios, servicioPublicaciones, servicioAnuncios);
    }

    private static void mostrarBienvenida() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE RED SOCIAL - TPO ALGORITMOS              ║");
        System.out.println("║     Análisis y Optimización con Algoritmos Avanzados    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n┌─────────────────── MENÚ PRINCIPAL ───────────────────┐");
        System.out.println("│                                                      │");
        System.out.println("│  1️⃣  Gestión de Usuarios                            │");
        System.out.println("│  2️⃣  Gestión de Publicaciones                       │");
        System.out.println("│  3️⃣  Gestión de Anuncios                            │");
        System.out.println("│                                                      │");
        System.out.println("│  ── Módulos de Algoritmos ──                        │");
        System.out.println("│  4️⃣  Asignación de Publicidad (Prog. Dinámica)      │");
        System.out.println("│  5️⃣  Optimización de Portada (Prog. Dinámica)       │");
        System.out.println("│  6️⃣  Recomendación de Amigos (BFS)                  │");
        System.out.println("│  7️⃣  Rutas de Influencia (BFS)                      │");
        System.out.println("│  8️⃣  Visualización de Publicaciones (Heap)          │");
        System.out.println("│                                                      │");
        System.out.println("│  9️⃣  Guardar Datos                                  │");
        System.out.println("│  0️⃣  Salir                                          │");
        System.out.println("│                                                      │");
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
                System.out.println("❌ Opción inválida.");
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
        System.out.println("\n┌─────── GESTIÓN DE PUBLICACIONES ───────┐");
        System.out.println("│ 1. Listar publicaciones               │");
        System.out.println("│ 2. Agregar publicación                │");
        System.out.println("│ 3. Dar like a publicación             │");
        System.out.println("│ 0. Volver                             │");
        System.out.println("└───────────────────────────────────────┘");
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
                System.out.println("❌ Opción inválida.");
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
            System.out.println("👍 Like agregado! Total: " + pub.getLikes() + " likes");
        } else {
            System.out.println("❌ Publicación no encontrada.");
        }
    }

    // ==================== GESTIÓN DE ANUNCIOS ====================

    private static void menuGestionAnuncios() {
        System.out.println("\n┌─────── GESTIÓN DE ANUNCIOS ───────┐");
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
                System.out.println("❌ Opción inválida.");
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

    // ==================== MÓDULOS DE ALGORITMOS ====================

    private static void ejecutarAsignacionPublicidad() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  ASIGNACIÓN DE PUBLICIDAD - Programación Dinámica     ║");
        System.out.println("║  Problema de la Mochila 0/1                           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        List<Anuncio> anuncios = servicioAnuncios.obtenerTodosLosAnuncios();

        if (anuncios.isEmpty()) {
            System.out.println("❌ No hay anuncios disponibles.");
            return;
        }

        servicioAnuncios.listarAnuncios();

        System.out.print("\n💰 Ingrese el presupuesto disponible: $");
        int presupuesto = leerEntero();

        algoritmos.AsignacionPublicidad.ResultadoAsignacion resultado = algoritmos.AsignacionPublicidad
                .maxAlcance(anuncios, presupuesto);

        resultado.mostrarResultado(presupuesto);
    }

    private static void ejecutarOptimizacionPortada() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  OPTIMIZACIÓN DE PORTADA - Programación Dinámica      ║");
        System.out.println("║  Problema de la Mochila 0/1                           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        List<PublicacionModelo> publicaciones = servicioPublicaciones.obtenerTodasLasPublicaciones();

        if (publicaciones.isEmpty()) {
            System.out.println("❌ No hay publicaciones disponibles.");
            return;
        }

        System.out.println("\nPublicaciones disponibles: " + publicaciones.size());
        System.out.print("📐 Ingrese el espacio máximo de la portada: ");
        int espacioMaximo = leerEntero();

        algoritmos.OptimizacionPortada.ResultadoOptimizacion resultado = algoritmos.OptimizacionPortada
                .optimizar(publicaciones, espacioMaximo);

        resultado.mostrarResultado();
    }

    private static void ejecutarRecomendacionAmigos() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  RECOMENDACIÓN DE AMIGOS - Búsqueda en Grafos (BFS)  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        if (servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
            System.out.println("❌ No hay usuarios disponibles.");
            return;
        }

        servicioUsuarios.listarUsuarios();

        System.out.print("\n🔍 Ingrese el ID del usuario: ");
        String usuarioId = scanner.nextLine();

        Usuario usuario = servicioUsuarios.obtenerUsuario(usuarioId);
        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado.");
            return;
        }

        Map<String, List<String>> grafo = servicioUsuarios.obtenerGrafoAmistades();

        // Mostrar distancias
        Map<String, Integer> distancias = algoritmos.RecomendacionAmigos.distanciasDesde(grafo, usuarioId);
        Map<String, String> nombres = obtenerMapaNombres();

        algoritmos.RecomendacionAmigos.mostrarDistancias(usuario.getNombre(), distancias, nombres);

        // Mostrar recomendaciones
        List<String> recomendaciones = algoritmos.RecomendacionAmigos.recomendarAmigos(grafo, usuarioId);
        algoritmos.RecomendacionAmigos.mostrarRecomendaciones(usuario.getNombre(), recomendaciones, nombres);
    }

    private static void ejecutarRutasInfluencia() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  RUTAS DE INFLUENCIA - Búsqueda en Grafos (BFS)      ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        if (servicioUsuarios.obtenerTodosLosUsuarios().isEmpty()) {
            System.out.println("❌ No hay usuarios disponibles.");
            return;
        }

        servicioUsuarios.listarUsuarios();

        System.out.print("\n🎯 ID del usuario origen: ");
        String origen = scanner.nextLine();
        System.out.print("🎯 ID del usuario destino: ");
        String destino = scanner.nextLine();

        if (servicioUsuarios.obtenerUsuario(origen) == null ||
                servicioUsuarios.obtenerUsuario(destino) == null) {
            System.out.println("❌ Uno o ambos usuarios no existen.");
            return;
        }

        Map<String, List<String>> grafo = servicioUsuarios.obtenerGrafoAmistades();
        List<String> ruta = algoritmos.RutasInfluencia.rutaMasCorta(grafo, origen, destino);

        Map<String, String> nombres = obtenerMapaNombres();
        algoritmos.RutasInfluencia.mostrarRuta(ruta, nombres);
    }

    private static void ejecutarVisualizacionPublicaciones() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  VISUALIZACIÓN DE PUBLICACIONES                       ║");
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
                System.out.println("❌ Opción inválida.");
        }
    }

    // ==================== UTILIDADES ====================

    private static void guardarTodosDatos() {
        System.out.println("\n💾 Guardando todos los datos...");
        servicioUsuarios.guardarUsuarios();
        servicioPublicaciones.guardarPublicaciones();
        servicioAnuncios.guardarAnuncios();
        System.out.println("✅ Datos guardados correctamente.");
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
            System.out.println("⚠️ Entrada inválida. Se usará 0.");
            return 0;
        }
    }

    private static void pausar() {
        System.out.print("\n[Presione ENTER para continuar]");
        scanner.nextLine();
    }

    private static void despedida() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  Guardando datos y cerrando el sistema...               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        guardarTodosDatos();
        System.out.println("\n👋 ¡Hasta pronto!\n");
    }
}
