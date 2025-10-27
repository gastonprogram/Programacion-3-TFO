package pruebas;

import java.util.*;
import algoritmos.RecomendacionAmigos;
import servicio.ServicioInteracciones;
import servicio.ServicioUsuarios;
import modelo.Usuario;
import datos.GestorDatos;

public class TestRecomendacionAmigos {
    public static void main(String[] args) {
        try {
            GestorDatos gestorDatos = new GestorDatos();
            ServicioUsuarios servicioUsuarios = new ServicioUsuarios(gestorDatos);
            ServicioInteracciones servicioInteracciones = new ServicioInteracciones();
            
            String usuarioId = "U003";
            
            Usuario usuario = servicioUsuarios.obtenerUsuario(usuarioId);
            if (usuario == null) {
                System.err.println("ERROR: Usuario con ID '" + usuarioId + "' no encontrado");
                return;
            }
            
            Map<String, List<String>> grafo = servicioUsuarios.obtenerGrafoAmistades();
            
            Map<String, String> nombres = obtenerMapaNombres(servicioUsuarios);
            
            List<String> recomendaciones = RecomendacionAmigos.recomendarAmigos(
                grafo, 
                usuarioId, 
                servicioInteracciones
            );
            
            RecomendacionAmigos.mostrarRecomendaciones(usuario.getNombre(), recomendaciones, nombres);
            
            if (recomendaciones == null || recomendaciones.isEmpty()) {
                System.out.println("\nNOTA: No hay recomendaciones disponibles para este usuario");
            } else {
                System.out.println("\nOK: TestRecomendacionAmigos ejecutado exitosamente");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR en TestRecomendacionAmigos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, String> obtenerMapaNombres(ServicioUsuarios servicioUsuarios) {
        Map<String, String> nombres = new HashMap<>();
        Map<String, Usuario> todosUsuarios = servicioUsuarios.obtenerTodosLosUsuarios();
        
        for (Usuario u : todosUsuarios.values()) {
            nombres.put(u.getId(), u.getNombre());
        }
        
        return nombres;
    }
}