package servicio;

import modelo.Usuario;
import datos.GestorDatos;
import java.util.*;

/**
 * Servicio que gestiona usuarios y sus relaciones.
 */
public class ServicioUsuarios {
    private Map<String, Usuario> usuarios;
    private GestorDatos gestorDatos;
    
    public ServicioUsuarios(GestorDatos gestorDatos) {
        this.gestorDatos = gestorDatos;
        this.usuarios = new HashMap<>();
        cargarUsuarios();
    }
    
    private void cargarUsuarios() {
        List<Usuario> listaUsuarios = gestorDatos.cargarUsuarios();
        for (Usuario usuario : listaUsuarios) {
            usuarios.put(usuario.getId(), usuario);
        }
    }
    
    public void guardarUsuarios() {
        gestorDatos.guardarUsuarios(new ArrayList<>(usuarios.values()));
    }
    
    public void agregarUsuario(String id, String nombre) {
        if (!usuarios.containsKey(id)) {
            usuarios.put(id, new Usuario(id, nombre));
            System.out.println("✅ Usuario agregado: " + nombre);
        } else {
            System.out.println("⚠️ El usuario con ID '" + id + "' ya existe.");
        }
    }
    
    public void agregarAmistad(String idUsuario1, String idUsuario2) {
        Usuario u1 = usuarios.get(idUsuario1);
        Usuario u2 = usuarios.get(idUsuario2);
        
        if (u1 == null || u2 == null) {
            System.out.println("❌ Uno o ambos usuarios no existen.");
            return;
        }
        
        u1.agregarAmigo(idUsuario2);
        u2.agregarAmigo(idUsuario1);
        System.out.println("✅ Amistad creada entre " + u1.getNombre() + " y " + u2.getNombre());
    }
    
    public Usuario obtenerUsuario(String id) {
        return usuarios.get(id);
    }
    
    public Map<String, Usuario> obtenerTodosLosUsuarios() {
        return usuarios;
    }
    
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        
        System.out.println("\nLISTA DE USUARIOS:");
        System.out.println("═══════════════════════════════════════");
        for (Usuario usuario : usuarios.values()) {
            System.out.println(usuario);
        }
    }
    
    /**
     * Construye el grafo de amistades para algoritmos de grafos.
     */
    public Map<String, List<String>> obtenerGrafoAmistades() {
        Map<String, List<String>> grafo = new HashMap<>();
        for (Usuario usuario : usuarios.values()) {
            grafo.put(usuario.getId(), new ArrayList<>(usuario.getAmigos()));
        }
        return grafo;
    }
}
