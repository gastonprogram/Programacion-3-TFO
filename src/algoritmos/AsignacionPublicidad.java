package algoritmos;

import modelo.Anuncio;
import java.util.*;

/**
 * Módulo de Asignación de Publicidad.
 * Paradigma: Programación Dinámica (Problema de la Mochila 0/1)
 * Objetivo: Maximizar el alcance publicitario con un presupuesto limitado.
 */
public class AsignacionPublicidad {

    /**
     * Resuelve el problema de maximizar el alcance con un presupuesto dado.
     * 
     * @param anuncios    Lista de anuncios disponibles
     * @param presupuesto Presupuesto disponible
     * @return Máximo alcance posible y lista de anuncios seleccionados
     */
    public static ResultadoAsignacion maxAlcance(List<Anuncio> anuncios, int presupuesto) {
        int n = anuncios.size();
        int[][] dp = new int[n + 1][presupuesto + 1];

        // dp[i][j] = máximo alcance usando los primeros i anuncios con presupuesto j
        for (int i = 1; i <= n; i++) {
            Anuncio anuncio = anuncios.get(i - 1);
            for (int j = 0; j <= presupuesto; j++) {
                if (anuncio.getCosto() <= j) {
                    dp[i][j] = Math.max(
                            anuncio.getAlcance() + dp[i - 1][j - anuncio.getCosto()],
                            dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Reconstruir la solución
        List<Anuncio> seleccionados = new ArrayList<>();
        int alcanceMaximo = dp[n][presupuesto];
        int w = presupuesto;

        for (int i = n; i > 0 && alcanceMaximo > 0; i--) {
            if (alcanceMaximo != dp[i - 1][w]) {
                Anuncio anuncio = anuncios.get(i - 1);
                seleccionados.add(anuncio);
                alcanceMaximo -= anuncio.getAlcance();
                w -= anuncio.getCosto();
            }
        }

        Collections.reverse(seleccionados);
        return new ResultadoAsignacion(dp[n][presupuesto], seleccionados);
    }

    /**
     * Clase para encapsular el resultado del algoritmo.
     */
    public static class ResultadoAsignacion {
        private int alcanceMaximo;
        private List<Anuncio> anunciosSeleccionados;

        public ResultadoAsignacion(int alcanceMaximo, List<Anuncio> anunciosSeleccionados) {
            this.alcanceMaximo = alcanceMaximo;
            this.anunciosSeleccionados = anunciosSeleccionados;
        }

        public int getAlcanceMaximo() {
            return alcanceMaximo;
        }

        public List<Anuncio> getAnunciosSeleccionados() {
            return anunciosSeleccionados;
        }

        public void mostrarResultado(int presupuesto) {
            System.out.println("\n💰 RESULTADO DE ASIGNACIÓN DE PUBLICIDAD");
            System.out.println("═══════════════════════════════════════════════");
            System.out.println("Presupuesto disponible: $" + presupuesto);
            System.out.println("Alcance máximo obtenido: " + alcanceMaximo + " usuarios");
            System.out.println("\n📢 Anuncios seleccionados:");

            int costoTotal = 0;
            for (Anuncio anuncio : anunciosSeleccionados) {
                System.out.println("  • " + anuncio.getNombre() +
                        " (Costo: $" + anuncio.getCosto() +
                        ", Alcance: " + anuncio.getAlcance() + ")");
                costoTotal += anuncio.getCosto();
            }
            System.out.println("\nCosto total utilizado: $" + costoTotal);
            System.out.println("Presupuesto restante: $" + (presupuesto - costoTotal));
        }
    }
}
