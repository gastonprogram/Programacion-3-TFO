package algoritmos;

import servicio.ServicioInteracciones;
import java.util.*;

/**
 * Módulo de Recomendación de Amigos.
 * Paradigma: Algoritmo de Dijkstra con pesos basados en interacciones
 * Objetivo: Encontrar usuarios con menor "distancia social" considerando
 * afinidad por likes.
 * 
 * CONSIGNA IMPLEMENTADA:
 * - Las distancias están ponderadas por interacciones (likes)
 * - Se garantiza el camino más directo usando Dijkstra con cola de prioridad
 * - Complejidad: O((V + E) log V) eficiente para miles de usuarios
 * - Recomendación basada en menor distancia social ponderada
 */
public class RecomendacionAmigos {

    /**
     * Clase para representar una arista con peso en el grafo social.
     */
    public static class Arista {
        String destino;
        double peso; // Menor peso = mayor afinidad = mejor recomendación

        public Arista(String destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    /**
     * ALGORITMO DE DIJKSTRA - Encuentra la ruta de menor costo entre usuarios.
     * 
     * GARANTÍA: En cada paso se elige el camino más directo usando cola de
     * prioridad.
     * COMPLEJIDAD: O((V + E) log V) - eficiente para miles de usuarios.
     * 
     * @param grafo                 Grafo de amistades (usuario -> lista de amigos)
     * @param origen                ID del usuario origen
     * @param servicioInteracciones Servicio para calcular afinidad basada en likes
     * @return Mapa con las distancias mínimas ponderadas desde el origen
     */
    public static Map<String, Double> distanciasDesde(Map<String, List<String>> grafo, String origen,
            ServicioInteracciones servicioInteracciones) {
        // 1. Convertir grafo simple a grafo ponderado por interacciones
        Map<String, List<Arista>> grafoPonderado = construirGrafoPonderado(grafo, servicioInteracciones);

        // 2. Inicializar estructuras de Dijkstra
        Map<String, Double> distancias = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<String> colaPrioridad = new PriorityQueue<>(
                Comparator.comparing(distancias::get, Comparator.nullsLast(Double::compareTo)));

        // 3. Inicializar todas las distancias como infinito, excepto el origen
        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(origen, 0.0);
        colaPrioridad.add(origen);

        // 4. ALGORITMO DE DIJKSTRA - Garantía de camino óptimo
        while (!colaPrioridad.isEmpty()) {
            String actual = colaPrioridad.poll();

            if (visitados.contains(actual)) {
                continue; // Ya procesado con distancia óptima
            }

            visitados.add(actual); // Marcar como óptimo

            // 5. Relajación de aristas: revisar todos los vecinos
            List<Arista> aristas = grafoPonderado.getOrDefault(actual, new ArrayList<>());
            for (Arista arista : aristas) {
                String vecino = arista.destino;
                double nuevaDistancia = distancias.get(actual) + arista.peso;

                // Si encontramos un camino más corto, actualizar
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    colaPrioridad.add(vecino);
                }
            }
        }

        return distancias;
    }

    /**
     * CONSTRUCCIÓN DEL GRAFO PONDERADO - El corazón del algoritmo de afinidad.
     * 
     * ALGORITMO DE DISTANCIA:
     * - Peso base: 1.0 (conexión estándar)
     * - Reducción por afinidad: -0.3 por cada interacción positiva
     * - Mayor afinidad = menor peso = mejor recomendación
     * 
     * FACTORES CONSIDERADOS:
     * - Likes directos entre usuarios
     * - Gustos similares (likes a los mismos autores)
     * - Reciprocidad en las interacciones
     */
    private static Map<String, List<Arista>> construirGrafoPonderado(Map<String, List<String>> grafo,
            ServicioInteracciones servicioInteracciones) {
        Map<String, List<Arista>> grafoPonderado = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : grafo.entrySet()) {
            String usuario = entry.getKey();
            List<String> amigos = entry.getValue();
            List<Arista> aristas = new ArrayList<>();

            for (String amigo : amigos) {
                // CALCULAR PESO DE LA ARISTA basado en interacciones
                double pesoArista = calcularDistanciaSocial(usuario, amigo, servicioInteracciones);
                aristas.add(new Arista(amigo, pesoArista));
            }

            grafoPonderado.put(usuario, aristas);
        }

        return grafoPonderado;
    }

    /**
     * ALGORITMO DE DISTANCIA SOCIAL - Determina qué tan "cercanos" están dos
     * usuarios.
     * 
     * FÓRMULA:
     * distancia = peso_base - (afinidad * factor_reducción)
     * 
     * MENOR DISTANCIA = MAYOR AFINIDAD = MEJOR RECOMENDACIÓN
     * 
     * @param usuario1              ID del primer usuario
     * @param usuario2              ID del segundo usuario (amigo directo)
     * @param servicioInteracciones Servicio para obtener datos de likes
     * @return Distancia social ponderada (menor = más cercanos)
     */
    private static double calcularDistanciaSocial(String usuario1, String usuario2,
            ServicioInteracciones servicioInteracciones) {
        // Peso base para cualquier amistad
        double pesoBase = 1.0;

        // Calcular afinidad basada en interacciones (likes)
        double afinidad = servicioInteracciones.calcularAfinidad(usuario1, usuario2);

        // Factor de reducción: cada punto de afinidad reduce 0.3 la distancia
        double factorReduccion = 0.3;

        // Distancia final: menor afinidad = mayor distancia
        double distanciaFinal = pesoBase - (afinidad * factorReduccion);

        // Asegurar que la distancia sea siempre positiva y significativa
        return Math.max(0.1, distanciaFinal);
    }

    /**
     * RECOMENDACIÓN INTELIGENTE usando Dijkstra y análisis de interacciones.
     * 
     * CRITERIO: Usuarios con menor distancia social ponderada son mejores
     * candidatos.
     * FILTROS: Excluye amigos directos y al usuario mismo.
     * ORDENAMIENTO: Por distancia ascendente (más cercanos primero).
     * 
     * @param grafo                 Grafo de amistades
     * @param usuarioId             Usuario para quien recomendar
     * @param servicioInteracciones Servicio de interacciones para cálculo de
     *                              afinidad
     * @return Lista de usuarios recomendados ordenada por afinidad
     */
    public static List<String> recomendarAmigos(Map<String, List<String>> grafo, String usuarioId,
            ServicioInteracciones servicioInteracciones) {
        // 1. Calcular distancias usando Dijkstra con pesos por interacciones
        Map<String, Double> distancias = distanciasDesde(grafo, usuarioId, servicioInteracciones);

        // 2. Obtener amigos directos para filtrarlos
        List<String> amigosDirectos = grafo.getOrDefault(usuarioId, new ArrayList<>());

        // 3. Crear lista de candidatos con sus distancias
        List<Map.Entry<String, Double>> candidatos = new ArrayList<>();

        for (Map.Entry<String, Double> entry : distancias.entrySet()) {
            String usuario = entry.getKey();
            double distancia = entry.getValue();

            // FILTROS DE RECOMENDACIÓN:
            // - No recomendarse a sí mismo
            // - No recomendar amigos directos existentes
            // - Solo usuarios alcanzables con distancia razonable
            if (!usuario.equals(usuarioId) &&
                    !amigosDirectos.contains(usuario) &&
                    distancia > 0 && distancia <= 4.0) {
                candidatos.add(entry);
            }
        }

        // 4. Ordenar por distancia: menor distancia = mejor recomendación
        candidatos.sort(Map.Entry.comparingByValue());

        // 5. Retornar mejores candidatos (máximo 5)
        List<String> recomendaciones = new ArrayList<>();
        int limite = Math.min(5, candidatos.size());

        for (int i = 0; i < limite; i++) {
            recomendaciones.add(candidatos.get(i).getKey());
        }

        return recomendaciones;
    }

    /**
     * Muestra recomendaciones inteligentes basadas en Dijkstra e interacciones.
     */
    public static void mostrarRecomendaciones(String usuarioId, List<String> recomendaciones,
            Map<String, String> nombresUsuarios) {
        System.out.println("\nRECOMENDACIONES INTELIGENTES PARA: " + usuarioId);
        System.out.println("═══════════════════════════════════════════════════════════════");

        if (recomendaciones.isEmpty()) {
            System.out.println("No hay recomendaciones disponibles en este momento.");
            System.out.println("\nPosibles razones:");
            System.out.println("   • Ya eres amigo de todos los usuarios con alta afinidad");
            System.out.println("   • La red de amistades está muy fragmentada");
            System.out.println("   • Pocas interacciones (likes) registradas en el sistema");
        } else {
            System.out.println("USUARIOS RECOMENDADOS (ordenados por afinidad de interacciones):");

            for (int i = 0; i < recomendaciones.size(); i++) {
                String recomendado = recomendaciones.get(i);
                String nombre = nombresUsuarios.getOrDefault(recomendado, recomendado);
                String indicador = "";
                String razon = "";

                // Agregar indicadores de calidad basados en posición
                switch (i) {
                    case 0:
                        indicador = "1";
                        razon = " (máxima afinidad por likes)";
                        break;
                    case 1:
                        indicador = "2";
                        razon = " (alta afinidad)";
                        break;
                    case 2:
                        indicador = "3";
                        razon = " (buena afinidad)";
                        break;
                    default:
                        indicador = "~";
                        razon = " (afinidad detectada)";
                        break;
                }

                System.out.println("  " + indicador + " " + nombre + " (ID: " + recomendado + ")" + razon);
            }
        }
    }
}
