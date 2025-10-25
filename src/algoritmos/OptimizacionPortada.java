package algoritmos;

import modelo.PublicacionModelo;
import java.util.*;

/**
 * Módulo de Optimización de Portada.
 * Paradigma: Programación Dinámica (Problema de la Mochila 0/1)
 * Objetivo: Seleccionar publicaciones que maximicen el beneficio sin exceder el espacio.
 */
public class OptimizacionPortada {
    
    /**
     * Optimiza la selección de publicaciones para la portada.
     * @param publicaciones Lista de publicaciones disponibles
     * @param espacioMaximo Espacio máximo disponible en la portada
     * @return Resultado con beneficio máximo y publicaciones seleccionadas
     */
    public static ResultadoOptimizacion optimizar(List<PublicacionModelo> publicaciones, int espacioMaximo) {
        int n = publicaciones.size();
        
        // dp[i][j] -> máximo beneficio con las primeras i publicaciones y j unidades de espacio
        int[][] dp = new int[n + 1][espacioMaximo + 1];
        
        // Rellenamos la tabla dinámica
        for (int i = 1; i <= n; i++) {
            PublicacionModelo pub = publicaciones.get(i - 1);
            int tamano = pub.getTamano();
            int beneficio = pub.getBeneficio();
            
            for (int j = 1; j <= espacioMaximo; j++) {
                if (tamano <= j) {
                    // Elegir el máximo entre tomar o no tomar la publicación
                    dp[i][j] = Math.max(dp[i - 1][j], beneficio + dp[i - 1][j - tamano]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        
        // Reconstrucción de la solución
        int beneficioMaximo = dp[n][espacioMaximo];
        int w = espacioMaximo;
        List<PublicacionModelo> seleccionadas = new ArrayList<>();
        
        for (int i = n; i > 0 && beneficioMaximo > 0; i--) {
            if (beneficioMaximo != dp[i - 1][w]) {
                PublicacionModelo pub = publicaciones.get(i - 1);
                seleccionadas.add(pub);
                beneficioMaximo -= pub.getBeneficio();
                w -= pub.getTamano();
            }
        }
        
        Collections.reverse(seleccionadas);
        return new ResultadoOptimizacion(dp[n][espacioMaximo], seleccionadas, espacioMaximo);
    }
    
    /**
     * Clase para encapsular el resultado del algoritmo.
     */
    public static class ResultadoOptimizacion {
        private int beneficioMaximo;
        private List<PublicacionModelo> publicacionesSeleccionadas;
        private int espacioMaximo;
        
        public ResultadoOptimizacion(int beneficioMaximo, List<PublicacionModelo> publicacionesSeleccionadas, int espacioMaximo) {
            this.beneficioMaximo = beneficioMaximo;
            this.publicacionesSeleccionadas = publicacionesSeleccionadas;
            this.espacioMaximo = espacioMaximo;
        }
        
        public int getBeneficioMaximo() { return beneficioMaximo; }
        public List<PublicacionModelo> getPublicacionesSeleccionadas() { return publicacionesSeleccionadas; }
        
        public void mostrarResultado() {
            System.out.println("\nRESULTADO DE OPTIMIZACIÓN DE PORTADA");
            System.out.println("═══════════════════════════════════════════════════");
            System.out.println("Espacio máximo disponible: " + espacioMaximo + " unidades");
            System.out.println("Beneficio máximo alcanzado: " + beneficioMaximo);
            System.out.println("\nPublicaciones seleccionadas para la portada:");
            
            int espacioUtilizado = 0;
            for (PublicacionModelo pub : publicacionesSeleccionadas) {
                System.out.println("  • " + pub.getId() + 
                                   " | Tamaño: " + pub.getTamano() + 
                                   " | Beneficio: " + pub.getBeneficio() +
                                   " | Likes: " + pub.getLikes());
                espacioUtilizado += pub.getTamano();
            }
            System.out.println("\nEspacio total utilizado: " + espacioUtilizado + " unidades");
            System.out.println("Espacio restante: " + (espacioMaximo - espacioUtilizado) + " unidades");
        }
    }
}
