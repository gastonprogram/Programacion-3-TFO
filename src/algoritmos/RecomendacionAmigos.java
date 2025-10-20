package algoritmos;

import java.util.*;

/**
 * Módulo de Recomendación de Amigos.
 * Paradigma: Búsqueda en Grafos (BFS)
 * Objetivo: Calcular distancias entre usuarios en la red social.
 */
public class RecomendacionAmigos {
    
    /**
     * Calcula las distancias desde un usuario origen a todos los demás usando BFS.
     * @param grafo Grafo de amistades (mapa de usuario -> lista de amigos)
     * @param origen ID del usuario origen
     * @return Mapa con las distancias desde el origen a cada usuario
     */
    public static Map<String, Integer> distanciasDesde(Map<String, List<String>> grafo, String origen) {
        Map<String, Integer> distancias = new HashMap<>();
        Queue<String> cola = new LinkedList<>();
        
        cola.add(origen);
        distancias.put(origen, 0);
        
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            List<String> vecinos = grafo.getOrDefault(actual, new ArrayList<>());
            
            for (String vecino : vecinos) {
                if (!distancias.containsKey(vecino)) {
                    distancias.put(vecino, distancias.get(actual) + 1);
                    cola.add(vecino);
                }
            }
        }
        
        return distancias;
    }
    
    /**
     * Recomienda amigos basándose en la distancia en la red.
     * Usuarios a distancia 2 son "amigos de amigos" - candidatos ideales.
     */
    public static List<String> recomendarAmigos(Map<String, List<String>> grafo, String usuarioId) {
        Map<String, Integer> distancias = distanciasDesde(grafo, usuarioId);
        List<String> recomendaciones = new ArrayList<>();
        
        // Buscar usuarios a distancia 2 (amigos de amigos)
        for (Map.Entry<String, Integer> entry : distancias.entrySet()) {
            if (entry.getValue() == 2) {
                recomendaciones.add(entry.getKey());
            }
        }
        
        return recomendaciones;
    }
    
    /**
     * Muestra las distancias desde un usuario a todos los demás.
     */
    public static void mostrarDistancias(String origen, Map<String, Integer> distancias, Map<String, String> nombresUsuarios) {
        System.out.println("\n🔗 DISTANCIAS DESDE: " + origen);
        System.out.println("═══════════════════════════════════════");
        
        // Ordenar por distancia
        List<Map.Entry<String, Integer>> ordenadas = new ArrayList<>(distancias.entrySet());
        ordenadas.sort(Map.Entry.comparingByValue());
        
        for (Map.Entry<String, Integer> entry : ordenadas) {
            String usuarioId = entry.getKey();
            int distancia = entry.getValue();
            String nombre = nombresUsuarios.getOrDefault(usuarioId, usuarioId);
            
            if (distancia == 0) {
                System.out.println("  • " + nombre + " (tú)");
            } else if (distancia == 1) {
                System.out.println("  • " + nombre + " - " + distancia + " salto (amigo directo)");
            } else {
                System.out.println("  • " + nombre + " - " + distancia + " saltos");
            }
        }
    }
    
    /**
     * Muestra recomendaciones de amistad.
     */
    public static void mostrarRecomendaciones(String usuarioId, List<String> recomendaciones, Map<String, String> nombresUsuarios) {
        System.out.println("\n💡 RECOMENDACIONES DE AMISTAD PARA: " + usuarioId);
        System.out.println("═══════════════════════════════════════");
        
        if (recomendaciones.isEmpty()) {
            System.out.println("No hay recomendaciones disponibles en este momento.");
        } else {
            System.out.println("Usuarios que podrías conocer (amigos de amigos):");
            for (String recomendado : recomendaciones) {
                String nombre = nombresUsuarios.getOrDefault(recomendado, recomendado);
                System.out.println("  • " + nombre);
            }
        }
    }
}
