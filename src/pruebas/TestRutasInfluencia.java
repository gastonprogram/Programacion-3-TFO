package pruebas;

import algoritmos.RutasInfluencia;
import java.util.*;


public class TestRutasInfluencia {
    public static void main(String[] args) {
        RutasInfluencia r1 = new RutasInfluencia();

        r1.agregarConexion("A", "B");
        r1.agregarConexion("B", "D");
        r1.agregarConexion("D", "F");
        r1.agregarConexion("A", "C");
        r1.agregarConexion("C", "E");
        r1.agregarConexion("E", "F");
        r1.agregarConexion("C", "D"); // alternativa intermedia

        Map<String, String> nombres = new HashMap<>();
        nombres.put("A", "Ana");
        nombres.put("B", "Beto");
        nombres.put("C", "Carla");
        nombres.put("D", "Damián");
        nombres.put("E", "Elsa");
        nombres.put("F", "Fede");

        List<String> ruta1 = r1.buscarCadenas("A", "F");
        System.out.println("=== Escenario 1: ruta desde A hasta F ===");
        RutasInfluencia.mostrarRuta(ruta1, nombres);

        RutasInfluencia r2 = new RutasInfluencia();
        r2.agregarConexion("X", "Y");
        r2.agregarConexion("Y", "W");

        Map<String, String> nombres2 = new HashMap<>();
        nombres2.put("X", "Ximena");
        nombres2.put("Y", "Yago");
        nombres2.put("W", "Wanda");
        nombres2.put("Z", "Zoe");

        List<String> ruta2 = r2.buscarCadenas("X", "Z");
        System.out.println("\n=== Escenario 2: ruta desde X hasta Z (inexistente) ===");
        RutasInfluencia.mostrarRuta(ruta2, nombres2);
    }
}
