package servicio;

import modelo.Anuncio;
import datos.GestorDatos;
import java.util.*;

/**
 * Servicio que gestiona los anuncios publicitarios.
 */
public class ServicioAnuncios {
    private List<Anuncio> anuncios;
    private GestorDatos gestorDatos;
    
    public ServicioAnuncios(GestorDatos gestorDatos) {
        this.gestorDatos = gestorDatos;
        this.anuncios = gestorDatos.cargarAnuncios();
    }
    
    public void guardarAnuncios() {
        gestorDatos.guardarAnuncios(anuncios);
    }
    
    public void agregarAnuncio(Anuncio anuncio) {
        anuncios.add(anuncio);
        System.out.println("✅ Anuncio agregado: " + anuncio.getNombre());
    }
    
    public List<Anuncio> obtenerTodosLosAnuncios() {
        return new ArrayList<>(anuncios);
    }
    
    public void listarAnuncios() {
        if (anuncios.isEmpty()) {
            System.out.println("No hay anuncios disponibles.");
            return;
        }
        
        System.out.println("\n📢 LISTA DE ANUNCIOS:");
        System.out.println("═══════════════════════════════════════");
        for (Anuncio anuncio : anuncios) {
            System.out.println(anuncio);
        }
    }
}
