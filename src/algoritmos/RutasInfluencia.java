package algoritmos;

import java.util.*;

/**
 * Módulo de Rutas de Influencia.
 * Paradigma: Búsqueda en Grafos (BFS)
 * Objetivo: Encontrar la cadena de influencia más corta entre dos usuarios.
 */
public class RutasInfluencia {
    
    /**
     * Encuentra la ruta más corta entre dos usuarios usando BFS.
     * @param grafo Grafo de amistades (mapa de usuario -> lista de amigos)
     * @param origen ID del usuario origen
     * @param destino ID del usuario destino
     * @return Lista de IDs que representa la ruta desde origen hasta destino, o null si no existe
     */
    public static List<String> rutaMasCorta(Map<String, List<String>> grafo, String origen, String destino) {
        Queue<List<String>> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        
        // Comenzamos con el nodo origen
        cola.add(Arrays.asList(origen));
        
        while (!cola.isEmpty()) {
            List<String> ruta = cola.poll();
            String ultimo = ruta.get(ruta.size() - 1);
            
            // Si encontramos el destino, devolvemos la ruta completa
            if (ultimo.equals(destino)) {
                return ruta;
            }
            
            // Marcamos el nodo como visitado
            if (!visitados.contains(ultimo)) {
                visitados.add(ultimo);
                
                // Exploramos sus vecinos
                List<String> vecinos = grafo.getOrDefault(ultimo, new ArrayList<>());
                for (String vecino : vecinos) {
                    if (!visitados.contains(vecino)) {
                        List<String> nuevaRuta = new ArrayList<>(ruta);
                        nuevaRuta.add(vecino);
                        cola.add(nuevaRuta);
                    }
                }
            }
        }
        
        // Si no hay conexión posible
        return null;
    }
    
    /**
     * Encuentra todas las rutas posibles entre dos usuarios (limitadas a un largo máximo).
     */
    public static List<List<String>> encontrarTodasLasRutas(Map<String, List<String>> grafo, 
                                                             String origen, 
                                                             String destino, 
                                                             int longitudMaxima) {
        List<List<String>> todasLasRutas = new ArrayList<>();
        List<String> rutaActual = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        
        rutaActual.add(origen);
        visitados.add(origen);
        
        buscarRutasDFS(grafo, origen, destino, rutaActual, visitados, todasLasRutas, longitudMaxima);
        
        return todasLasRutas;
    }
    
    private static void buscarRutasDFS(Map<String, List<String>> grafo,
                                        String actual,
                                        String destino,
                                        List<String> rutaActual,
                                        Set<String> visitados,
                                        List<List<String>> todasLasRutas,
                                        int longitudMaxima) {
        // Si alcanzamos el destino, guardamos la ruta
        if (actual.equals(destino)) {
            todasLasRutas.add(new ArrayList<>(rutaActual));
            return;
        }
        
        // Si la ruta es demasiado larga, terminamos
        if (rutaActual.size() >= longitudMaxima) {
            return;
        }
        
        // Explorar vecinos
        List<String> vecinos = grafo.getOrDefault(actual, new ArrayList<>());
        for (String vecino : vecinos) {
            if (!visitados.contains(vecino)) {
                rutaActual.add(vecino);
                visitados.add(vecino);
                
                buscarRutasDFS(grafo, vecino, destino, rutaActual, visitados, todasLasRutas, longitudMaxima);
                
                // Backtrack
                rutaActual.remove(rutaActual.size() - 1);
                visitados.remove(vecino);
            }
        }
    }
    
    /**
     * Muestra la ruta de influencia encontrada.
     */
    public static void mostrarRuta(List<String> ruta, Map<String, String> nombresUsuarios) {
        System.out.println("\n🔍 RUTA DE INFLUENCIA ENCONTRADA");
        System.out.println("═══════════════════════════════════════");
        
        if (ruta == null) {
            System.out.println("❌ No existe conexión entre los usuarios.");
        } else {
            System.out.println("Longitud de la cadena: " + (ruta.size() - 1) + " saltos");
            System.out.print("Ruta: ");
            
            for (int i = 0; i < ruta.size(); i++) {
                String usuarioId = ruta.get(i);
                String nombre = nombresUsuarios.getOrDefault(usuarioId, usuarioId);
                System.out.print(nombre);
                
                if (i < ruta.size() - 1) {
                    System.out.print(" → ");
                }
            }
            System.out.println();
        }
    }
}
