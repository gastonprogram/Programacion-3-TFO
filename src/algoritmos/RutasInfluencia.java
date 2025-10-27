package algoritmos;

import java.util.*;

/**
 * Problema de Rutas de Influencia.
 * Paradigma: Backtracking con poda
 * Objetivo: Encontrar la cadena de influencia más efectiva entre dos usuarios.
 */
public class RutasInfluencia {
    private Map<String, List<String>> grafo;
    private List<String> mejorCamino;
    private double mejorEfectividad;
    
    public RutasInfluencia() {
        this.grafo = new HashMap<>();
        this.mejorCamino = null;
        this.mejorEfectividad = 0.0;
    }
    
    //Añade una conexión entre dos usuarios en el grafo.
    public void agregarConexion(String origen, String destino) {
        grafo.computeIfAbsent(origen, k -> new ArrayList<>()).add(destino);
    }
    
    //busca las cadenas entre usuarios usando backtracking con poda.
    public List<String> buscarCadenas(String origen, String destino) {
        Set<String> visitados = new HashSet<>();
        List<String> caminoActual = new ArrayList<>();
        mejorCamino = null;
        mejorEfectividad = 0.0;
        
        caminoActual.add(origen);
        visitados.add(origen);
        
        backtrack(origen, destino, visitados, caminoActual);
        
        return mejorCamino;
    }
    
    /**
     * Implementa el backtracking con poda para encontrar el camino más efectivo.
     */
    private void backtrack(String actual, String destino, Set<String> visitados, List<String> caminoActual) {
        // Caso base: llegamos al destino
        if (actual.equals(destino)) {
            double efectividadActual = evaluarCamino(caminoActual);
            if (mejorCamino == null || efectividadActual > mejorEfectividad) {
                mejorCamino = new ArrayList<>(caminoActual);
                mejorEfectividad = efectividadActual;
            }
            return;
        }
        
        // verificar si el camino actual puede mejorar
        if (!puedeMejorar(caminoActual, mejorCamino)) {
            return;
        }
        
        // ver vecinos
        List<String> vecinos = grafo.getOrDefault(actual, new ArrayList<>());
        for (String vecino : vecinos) {
            if (!visitados.contains(vecino)) {
                visitados.add(vecino);
                caminoActual.add(vecino);
                
                backtrack(vecino, destino, visitados, caminoActual);
                
                // volver para atras
                visitados.remove(vecino);
                caminoActual.remove(caminoActual.size() - 1);
            }
        }
    }
    
    /**
     * resolver la efectividad de un camino
     */
    private double evaluarCamino(List<String> camino) {
        
        if (camino.size() <= 1) return 0.0;
        
        double efectividad = 100.0; // Base inicial
        
        // Penalización por longitud
        efectividad /= camino.size();
        
        // Bonus por conexiones de nodos intermedios
        for (int i = 1; i < camino.size() - 1; i++) {
            String nodo = camino.get(i);
            int conexiones = grafo.getOrDefault(nodo, new ArrayList<>()).size();
            efectividad += conexiones * 0.5; 
        }
        
        return efectividad;
    }
    
    /**
     * evaluar si puede mejorar el camino
     */
    private boolean puedeMejorar(List<String> caminoActual, List<String> mejorCamino) {
        if (mejorCamino == null) return true;
        
        if (caminoActual.size() > mejorCamino.size() * 1.5) {
            return false;
        }
        
        // estimacion de si hay nodos conectados
        String ultimoNodo = caminoActual.get(caminoActual.size() - 1);
        int conexionesRestantes = grafo.getOrDefault(ultimoNodo, new ArrayList<>()).size();
        return conexionesRestantes > 0;
    }
    
    /**
     * muestra la ruta encontrada
     */
    public static void mostrarRuta(List<String> ruta, Map<String, String> nombresUsuarios) {
        System.out.println("\nRUTA DE INFLUENCIA ENCONTRADA");
        System.out.println("═══════════════════════════════════════");
        
        if (ruta == null) {
            System.out.println("No existe conexión entre los usuarios.");
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