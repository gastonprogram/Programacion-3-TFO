package datos;

import modelo.*;
import java.io.*;
import java.util.*;

/**
 * Clase que gestiona la persistencia de datos en archivos de texto plano (CSV).
 * Mucho más simple que la serialización - solo lee y escribe líneas de texto.
 */
public class GestorDatos {
    private static final String DIRECTORIO_DATOS = "datos";
    private static final String ARCHIVO_USUARIOS = DIRECTORIO_DATOS + "/usuarios.txt";
    private static final String ARCHIVO_AMISTADES = DIRECTORIO_DATOS + "/amistades.txt";
    private static final String ARCHIVO_PUBLICACIONES = DIRECTORIO_DATOS + "/publicaciones.txt";
    private static final String ARCHIVO_ANUNCIOS = DIRECTORIO_DATOS + "/anuncios.txt";
    private static final String ARCHIVO_INTERACCIONES = DIRECTORIO_DATOS + "/interacciones.txt";

    public GestorDatos() {
        crearDirectorioSiNoExiste();
    }

    private void crearDirectorioSiNoExiste() {
        File directorio = new File(DIRECTORIO_DATOS);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    // ==================== USUARIOS ====================

    /**
     * Guarda usuarios en formato CSV: id,nombre
     * Ejemplo: U001,Ana García
     */
    public void guardarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Usuario usuario : usuarios) {
                // Formato: id,nombre
                pw.println(usuario.getId() + "," + usuario.getNombre());
            }

            // Guardar amistades en archivo separado
            try (PrintWriter pwAmigos = new PrintWriter(new FileWriter(ARCHIVO_AMISTADES))) {
                for (Usuario usuario : usuarios) {
                    for (String amigoId : usuario.getAmigos()) {
                        // Formato: usuarioId,amigoId
                        pwAmigos.println(usuario.getId() + "," + amigoId);
                    }
                }
            }

            System.out.println("Usuarios guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga usuarios desde archivos de texto.
     */
    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            return usuarios;
        }

        // Primero cargar usuarios
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String id = partes[0];
                    String nombre = partes[1];
                    usuarios.add(new Usuario(id, nombre));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
            return usuarios;
        }

        // Luego cargar amistades
        File archivoAmistades = new File(ARCHIVO_AMISTADES);
        if (archivoAmistades.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_AMISTADES))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 2) {
                        String usuarioId = partes[0];
                        String amigoId = partes[1];

                        // Buscar el usuario y agregarle el amigo
                        for (Usuario usuario : usuarios) {
                            if (usuario.getId().equals(usuarioId)) {
                                usuario.agregarAmigo(amigoId);
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al cargar amistades: " + e.getMessage());
            }
        }

        return usuarios;
    }

    // ==================== PUBLICACIONES ====================

    /**
     * Guarda publicaciones en formato CSV:
     * id,autorId,contenido,fecha,likes,tamano,beneficio
     */
    public void guardarPublicaciones(List<PublicacionModelo> publicaciones) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PUBLICACIONES))) {
            for (PublicacionModelo pub : publicaciones) {
                // Formato CSV simple
                pw.println(pub.getId() + "," +
                        pub.getAutorId() + "," +
                        pub.getContenido().replace(",", ";") + "," + // Reemplazar comas en contenido
                        pub.getFecha().getTime() + "," +
                        pub.getLikes() + "," +
                        pub.getTamano() + "," +
                        pub.getBeneficio());
            }
            System.out.println("Publicaciones guardadas exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar publicaciones: " + e.getMessage());
        }
    }

    /**
     * Carga publicaciones desde archivo de texto.
     */
    public List<PublicacionModelo> cargarPublicaciones() {
        List<PublicacionModelo> publicaciones = new ArrayList<>();
        File archivo = new File(ARCHIVO_PUBLICACIONES);

        if (!archivo.exists()) {
            return publicaciones;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PUBLICACIONES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 7) {
                    String id = partes[0];
                    String autorId = partes[1];
                    String contenido = partes[2].replace(";", ","); // Restaurar comas
                    long fechaMillis = Long.parseLong(partes[3]);
                    int likes = Integer.parseInt(partes[4]);
                    int tamano = Integer.parseInt(partes[5]);
                    int beneficio = Integer.parseInt(partes[6]);

                    Date fecha = new Date(fechaMillis);
                    publicaciones.add(new PublicacionModelo(id, autorId, contenido, fecha, likes, tamano, beneficio));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar publicaciones: " + e.getMessage());
        }

        return publicaciones;
    }

    // ==================== ANUNCIOS ====================

    /**
     * Guarda anuncios en formato CSV: nombre,costo,alcance
     */
    public void guardarAnuncios(List<Anuncio> anuncios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_ANUNCIOS))) {
            for (Anuncio anuncio : anuncios) {
                pw.println(anuncio.getNombre() + "," +
                        anuncio.getCosto() + "," +
                        anuncio.getAlcance());
            }
            System.out.println("Anuncios guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar anuncios: " + e.getMessage());
        }
    }

    /**
     * Carga anuncios desde archivo de texto.
     */
    public List<Anuncio> cargarAnuncios() {
        List<Anuncio> anuncios = new ArrayList<>();
        File archivo = new File(ARCHIVO_ANUNCIOS);

        if (!archivo.exists()) {
            return anuncios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_ANUNCIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String nombre = partes[0];
                    int costo = Integer.parseInt(partes[1]);
                    int alcance = Integer.parseInt(partes[2]);
                    anuncios.add(new Anuncio(nombre, costo, alcance));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar anuncios: " + e.getMessage());
        }

        return anuncios;
    }

    // ==================== INTERACCIONES (LIKES) ====================

    /**
     * Guarda interacciones en formato CSV:
     * usuarioId,publicacionId,autorPublicacion,timestamp
     */
    public void guardarInteracciones(List<Interaccion> interacciones) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_INTERACCIONES))) {
            for (Interaccion interaccion : interacciones) {
                pw.println(interaccion.getUsuarioId() + "," +
                        interaccion.getPublicacionId() + "," +
                        interaccion.getAutorPublicacion() + "," +
                        interaccion.getTimestamp());
            }
            System.out.println("Interacciones guardadas exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar interacciones: " + e.getMessage());
        }
    }

    /**
     * Carga interacciones desde archivo de texto.
     */
    public List<Interaccion> cargarInteracciones() {
        List<Interaccion> interacciones = new ArrayList<>();
        File archivo = new File(ARCHIVO_INTERACCIONES);

        if (!archivo.exists()) {
            return interacciones;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_INTERACCIONES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    String usuarioId = partes[0];
                    String publicacionId = partes[1];
                    String autorPublicacion = partes[2];
                    long timestamp = Long.parseLong(partes[3]);

                    Interaccion interaccion = new Interaccion(usuarioId, publicacionId, autorPublicacion);
                    interaccion.setTimestamp(timestamp);
                    interacciones.add(interaccion);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar interacciones: " + e.getMessage());
        }

        return interacciones;
    }
}
