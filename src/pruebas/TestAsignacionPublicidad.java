package pruebas;

import algoritmos.AsignacionPublicidad;
import algoritmos.AsignacionPublicidad.ResultadoAsignacion;
import modelo.Anuncio;
import java.util.*;

public class TestAsignacionPublicidad {
    public static void main(String[] args) {
        List<Anuncio> anuncios = Arrays.asList(
            new Anuncio("Video Corto", 4, 5000),   // nombre, costo, alcance
            new Anuncio("Banner Home", 6, 6200),
            new Anuncio("Stories IG", 5, 5200),
            new Anuncio("Pre-Roll", 3, 3000)
        );
        int presupuesto = 10;

        ResultadoAsignacion res = AsignacionPublicidad.maxAlcance(anuncios, presupuesto);
        res.mostrarResultado(presupuesto);
    }
}
