package pruebas;

import algoritmos.OptimizacionPortada;
import algoritmos.OptimizacionPortada.ResultadoOptimizacion;
import modelo.PublicacionModelo;
import java.util.*;

public class TestOptimizacionPortada {
    public static void main(String[] args) {
        List<PublicacionModelo> publicaciones = Arrays.asList(
            new PublicacionModelo("P1", "3", "Educativo", new Date(), 50),
            new PublicacionModelo("P2", "4", "Juegos", new Date(), 60),
            new PublicacionModelo("P3", "2", "Entretenimiento", new Date(), 30),
            new PublicacionModelo("P4", "5", "Deportes", new Date(), 90)
        );
        int espacioMaximo = 7;

        ResultadoOptimizacion res = OptimizacionPortada.optimizar(publicaciones, espacioMaximo);
        res.mostrarResultado();
    }
}
