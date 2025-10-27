package algoritmos;

import servicio.ServicioInteracciones;
import java.util.*;

/**
 * Problema de Recomendación de Amigos.
 * Paradigma: Algoritmo de Dijkstra con pesos de aristas basados en interacciones
 * Objetivo: Encontrar usuarios con menor distancia 
 * 
 * Logica:
 * - Las distancias están ponderadas por interacciones (likes)
 * - Se garantiza el camino más directo usando Dijkstra con cola de prioridad
 */
public class RecomendacionAmigos {

    /**
     * clase que representa una arista
     */
    public static class Arista {
        String destino;
        double peso; // cuanto menos peso tenga, mayor afinidad

        public Arista(String destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    public static Map<String, Double> distanciasDesde(Map<String, List<String>> grafo, String origen,
            ServicioInteracciones servicioInteracciones) {
        /**
         * Dijkstra - Encuentra la ruta de menor costo entre usuarios.
         * 
         * recibe el grafo de amistades, el usuario origen y el servicio de interacciones (clase que calcula afinidad)
         * devuelve un mapa con las distancias mínimas desde el origen a todos los demás usuarios
         */
        


        // convertir el grafo
        Map<String, List<Arista>> grafoPonderado = construirGrafoPonderado(grafo, servicioInteracciones);

        // inicializar dijkstra
        Map<String, Double> distancias = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<String> colaPrioridad = new PriorityQueue<>(
                Comparator.comparing(distancias::get, Comparator.nullsLast(Double::compareTo)));

        // todas las distancias son "infinito" excepto la del origen
        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(origen, 0.0);
        colaPrioridad.add(origen);

        // algoritmo dijkstra
        while (!colaPrioridad.isEmpty()) {
            String actual = colaPrioridad.poll();

            if (visitados.contains(actual)) {
                continue; // Ya procesado con distancia óptima
            }

            visitados.add(actual); // optimo

            // relaxation
            List<Arista> aristas = grafoPonderado.getOrDefault(actual, new ArrayList<>());
            for (Arista arista : aristas) {
                String vecino = arista.destino;
                double nuevaDistancia = distancias.get(actual) + arista.peso;

                // actualizar si es menor la distancia
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    colaPrioridad.add(vecino);
                }
            }
        }

        return distancias;
    }

    private static Map<String, List<Arista>> construirGrafoPonderado(Map<String, List<String>> grafo,
            ServicioInteracciones servicioInteracciones) {

        /**
         * Se construye el grafo ponderado que se basa en las interacciones de los usuarios
         * 
         * FACTORES CONSIDERADOS:
         * - Likes directos entre usuarios
         * - Gustos similares (likes a los mismos autores)
         */
        Map<String, List<Arista>> grafoPonderado = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : grafo.entrySet()) {
            String usuario = entry.getKey();
            List<String> amigos = entry.getValue();
            List<Arista> aristas = new ArrayList<>();

            for (String amigo : amigos) {
                // calcula el peso basado en interacciones
                double pesoArista = calcularDistanciaSocial(usuario, amigo, servicioInteracciones);
                aristas.add(new Arista(amigo, pesoArista));
            }

            grafoPonderado.put(usuario, aristas);
        }

        return grafoPonderado;
    }

    
    private static double calcularDistanciaSocial(String usuario1, String usuario2,
            ServicioInteracciones servicioInteracciones) {

        /**
         * Determina qué tan "cercanos" están dos
         * usuarios.
         */
        // peso base
        double pesoBase = 1.0;

        // calcula
        double afinidad = servicioInteracciones.calcularAfinidad(usuario1, usuario2);

        // para la formula
        double factorReduccion = 0.3;
        double distanciaFinal = pesoBase - (afinidad * factorReduccion);

        return Math.max(0.1, distanciaFinal);
    }

    
    public static List<String> recomendarAmigos(Map<String, List<String>> grafo, String usuarioId,
            ServicioInteracciones servicioInteracciones) {

        /**
         * Algoritmo que utiliza todos los metodos de la clase para recomendar amigos
         * recibe el grafo de amistades, el usuario para quien se hacen las recomendaciones y el servicio de interacciones
         * devuelve una lista de los usuarios recomendados
         */        

        // usar dijkstra
        Map<String, Double> distancias = distanciasDesde(grafo, usuarioId, servicioInteracciones);

        // tener los amigos directos para no contarlos
        List<String> amigosDirectos = grafo.getOrDefault(usuarioId, new ArrayList<>());

        List<Map.Entry<String, Double>> candidatos = new ArrayList<>();

        for (Map.Entry<String, Double> entry : distancias.entrySet()) {
            String usuario = entry.getKey();
            double distancia = entry.getValue();

            // filtramos:
            // - No recomendarse a sí mismo
            // - No recomendar amigos existentes
            // - solo usuarios a distancias razonables (1 a 4)
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
     * metodo para mostrar toda la informacion de las recomendaciones obtenidas
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
                        indicador = "-";
                        razon = " (afinidad detectada)";
                        break;
                }

                System.out.println("  " + indicador + " " + nombre + " (ID: " + recomendado + ")" + razon);
            }
        }
    }
}
