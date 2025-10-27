package algoritmos;

import modelo.PublicacionModelo;
import java.util.*;

/**
 * Problema de Optimización de Portada.
 * Paradigma: Programación Dinámica - problema de la mochila
 * Objetivo: Seleccionar publicaciones que maximicen el beneficio sin exceder el espacio.
 */
public class OptimizacionPortada {
    
    
    public static ResultadoOptimizacion optimizar(List<PublicacionModelo> publicaciones, int espacioMaximo) {
        /**
         * Optimiza la selección de publicaciones para la portada.
         * recibe una lista de publicaciones y el espacio máximo disponible
         * retorna el beneficio máximo y publicaciones seleccionadas
         */
        
        //cantidad de publicaciones
        int n = publicaciones.size();
        
        int[][] tabla = new int[n + 1][espacioMaximo + 1];
        
        // rellenar la tabla
        for (int i = 1; i <= n; i++) {
            PublicacionModelo pub = publicaciones.get(i - 1);
            int tamano = pub.getTamano();
            int beneficio = pub.getBeneficio();
            
            for (int j = 1; j <= espacioMaximo; j++) {
                if (tamano <= j) {
                    // Elegir el máximo entre tomar o no tomar la publicación
                    tabla[i][j] = Math.max(tabla[i - 1][j], beneficio + tabla[i - 1][j - tamano]);
                } else {
                    tabla[i][j] = tabla[i - 1][j];
                }
            }
        }
        
        // Reconstrucción de la solución
        int beneficioMaximo = tabla[n][espacioMaximo];
        int w = espacioMaximo;
        List<PublicacionModelo> seleccionadas = new ArrayList<>();
        
        for (int i = n; i > 0 && beneficioMaximo > 0; i--) {
            if (beneficioMaximo != tabla[i - 1][w]) {
                PublicacionModelo pub = publicaciones.get(i - 1);
                seleccionadas.add(pub);
                beneficioMaximo -= pub.getBeneficio();
                w -= pub.getTamano();
            }
        }
        
        Collections.reverse(seleccionadas);
        return new ResultadoOptimizacion(tabla[n][espacioMaximo], seleccionadas, espacioMaximo);
    }
    
    /**
     * clase para guardar el resultado de el algortimo principal
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
