# 📝 Documentación de Integración del Sistema

src/
├── App.java ← Menú principal unificado
├── modelo/ ← Clases compartidas
│ ├── Usuario.java
│ ├── PublicacionModelo.java
│ └── Anuncio.java
├── datos/ ← Persistencia
│ └── GestorDatos.java
├── servicio/ ← Lógica de negocio
│ ├── ServicioUsuarios.java
│ ├── ServicioPublicaciones.java
│ └── ServicioAnuncios.java
├── algoritmos/ ← Módulos refactorizados
│ ├── AsignacionPublicidad.java
│ ├── OptimizacionPortada.java
│ ├── RecomendacionAmigos.java
│ ├── RutasInfluencia.java
│ └── GestionPublicaciones.java
└── util/
└── DatosIniciales.java

```

**Ventajas:**

- ✅ Clases de modelo únicas y reutilizables
- ✅ Todos los módulos comparten los mismos datos
- ✅ Persistencia automática en archivos
- ✅ Menú integrado para acceder a todas las funciones
- ✅ Datos dinámicos con CRUD completo

---

## 🔄 Mapeo de Funcionalidades

### 1. Gestión de Usuarios (NUEVO)

**Ubicación**: `servicio/ServicioUsuarios.java`

**Funcionalidades:**

- Agregar usuarios
- Crear relaciones de amistad
- Listar usuarios
- Persistencia en `datos/usuarios.dat`

**Uso en algoritmos:**

- Recomendación de Amigos → usa el grafo de amistades
- Rutas de Influencia → usa el grafo de amistades

### 2. Gestión de Publicaciones (MEJORADO)

**Antes**: `GestionPublicaciones.java` - clase Muro con datos hardcodeados
**Ahora**:

- **Modelo**: `modelo/PublicacionModelo.java`
- **Servicio**: `servicio/ServicioPublicaciones.java`
- **Algoritmos**: `algoritmos/GestionPublicaciones.java`

**Mejoras:**

- Persistencia en `datos/publicaciones.dat`
- CRUD completo (agregar, listar, dar likes)
- Atributos extendidos (tamaño, beneficio, autor)
- Mismo modelo usado por todos los módulos

**Uso en algoritmos:**

- Visualización de Publicaciones → vistas cronológicas y por relevancia
- Optimización de Portada → usa tamaño y beneficio

### 3. Gestión de Anuncios (MEJORADO)

**Antes**: `AsignacionPublicidad.java` - clase Anuncio interna
**Ahora**:

- **Modelo**: `modelo/Anuncio.java`
- **Servicio**: `servicio/ServicioAnuncios.java`

**Mejoras:**

- Persistencia en `datos/anuncios.dat`
- CRUD completo
- Reutilizable por todos los módulos

### 4. Asignación de Publicidad (REFACTORIZADO)

**Antes**: `AsignacionPublicidad.java` - main() independiente
**Ahora**: `algoritmos/AsignacionPublicidad.java`

**Cambios:**

- ✅ Algoritmo de Programación Dinámica **preservado**
- ✅ Recibe List<Anuncio> en lugar de array
- ✅ Usa anuncios del ServicioAnuncios
- ✅ Resultado encapsulado en clase interna ResultadoAsignacion
- ✅ Salida formateada y detallada

**Lógica del algoritmo: INTACTA** ✓

### 5. Optimización de Portada (REFACTORIZADO)

**Antes**: `OptimizacionPortada.java` - main() independiente
**Ahora**: `algoritmos/OptimizacionPortada.java`

**Cambios:**

- ✅ Algoritmo de Programación Dinámica **preservado**
- ✅ Usa PublicacionModelo en lugar de clase interna
- ✅ Usa publicaciones del ServicioPublicaciones
- ✅ Resultado encapsulado en clase interna ResultadoOptimizacion
- ✅ Reconstrucción de solución mejorada

**Lógica del algoritmo: INTACTA** ✓

### 6. Recomendación de Amigos (REFACTORIZADO)

**Antes**: `RecomendacionAmigos.java` - clase RedAmigos interna
**Ahora**: `algoritmos/RecomendacionAmigos.java`

**Cambios:**

- ✅ Algoritmo BFS **preservado**
- ✅ Usa grafo del ServicioUsuarios
- ✅ Funciones estáticas reutilizables
- ✅ Nueva función: recomendarAmigos() (distancia 2)
- ✅ Funciones de visualización mejoradas

**Lógica del algoritmo: INTACTA** ✓

### 7. Rutas de Influencia (REFACTORIZADO)

**Antes**: `RutasInfluencia.java` - main() independiente
**Ahora**: `algoritmos/RutasInfluencia.java`

**Cambios:**

- ✅ Algoritmo BFS **preservado**
- ✅ Usa grafo del ServicioUsuarios
- ✅ Funciones estáticas reutilizables
- ✅ Nueva función: encontrarTodasLasRutas() (opcional)
- ✅ Función de visualización mejorada

**Lógica del algoritmo: INTACTA** ✓

---

## 💾 Sistema de Persistencia

### Implementación

**Clase**: `datos/GestorDatos.java`

**Tecnología**: Serialización de objetos Java

**Archivos generados:**

```

datos/
├── usuarios.dat (List<Usuario>)
├── publicaciones.dat (List<PublicacionModelo>)
└── anuncios.dat (List<Anuncio>)

````

### Funcionamiento

1. **Carga al inicio**: Los servicios cargan datos existentes
2. **Primera ejecución**: Si no hay datos, se crean datos de prueba
3. **Guardado manual**: Opción 9 del menú
4. **Guardado automático**: Al salir del sistema (opción 0)

### Ventajas

- ✅ Persistencia transparente
- ✅ Sin dependencias externas (solo Java estándar)
- ✅ Fácil backup (copiar archivos .dat)
- ✅ Serialización de grafos completos

---

## 🎮 Menú Interactivo

### Estructura del Switch

```java
switch (opcion) {
    case 1: menuGestionUsuarios(); break;
    case 2: menuGestionPublicaciones(); break;
    case 3: menuGestionAnuncios(); break;
    case 4: ejecutarAsignacionPublicidad(); break;
    case 5: ejecutarOptimizacionPortada(); break;
    case 6: ejecutarRecomendacionAmigos(); break;
    case 7: ejecutarRutasInfluencia(); break;
    case 8: ejecutarVisualizacionPublicaciones(); break;
    case 9: guardarTodosDatos(); break;
    case 0: continuar = false; despedida(); break;
    default: System.out.println("❌ Opción inválida");
}
````

### Sub-menús

Cada módulo de gestión (usuarios, publicaciones, anuncios) tiene su propio sub-menú con switch-case.

---

## 🔗 Integración de Datos

### Flujo de Datos

```
┌─────────────────┐
│   GestorDatos   │  ← Persistencia en archivos
└────────┬────────┘
         │
    ┌────┴────┬────────────┬────────────┐
    │         │            │            │
┌───▼───┐ ┌──▼──┐ ┌───────▼──────┐ ┌──▼──┐
│Usuario│ │Publi│ │   Anuncio    │ │ ... │
└───┬───┘ └──┬──┘ └──────┬───────┘ └─────┘
    │        │           │
┌───▼────────▼───────────▼────┐
│        Servicios             │
│  - ServicioUsuarios          │
│  - ServicioPublicaciones     │
│  - ServicioAnuncios          │
└──────────┬───────────────────┘
           │
    ┌──────┴──────────────┬──────────────┐
    │                     │              │
┌───▼──────────┐  ┌──────▼─────┐  ┌────▼────┐
│  Algoritmos  │  │   Menú     │  │  CRUD   │
│  - BFS       │  │  Principal │  │  Datos  │
│  - Prog Dín  │  │            │  │         │
│  - Heap      │  │            │  │         │
└──────────────┘  └────────────┘  └─────────┘
```

### Ejemplo de Flujo Completo

**Caso de uso: Optimizar Portada**

1. Usuario selecciona opción 5 del menú
2. `ejecutarOptimizacionPortada()` se invoca
3. Obtiene publicaciones de `servicioPublicaciones`
4. Llama a `OptimizacionPortada.optimizar(publicaciones, espacio)`
5. El algoritmo procesa con Programación Dinámica
6. Retorna `ResultadoOptimizacion`
7. Se muestra en consola formateado
8. Usuario presiona ENTER para volver al menú

---

## 📊 Datos de Prueba

### Usuarios (8)

```
U001 - Ana García
U002 - Beto Fernández
U003 - Carla López
U004 - Diego Martínez
U005 - Eva Rodríguez
U006 - Franco Silva
U007 - Gisela Torres
U008 - Hugo Vargas
```

### Red de Amistades

```
Ana ─── Beto ─── Diego ─── Gisela
 │       │                   │
Carla   Eva ─── Franco ─── Hugo
         │
        Franco
```

### Publicaciones (8)

Cada publicación tiene:

- ID único (POST001 - POST008)
- Autor
- Contenido
- Fecha (variadas)
- Likes (45 - 350)
- Tamaño calculado (1-5)
- Beneficio calculado

### Anuncios (6)

```
Banner Principal    - Costo: 5, Alcance: 100
Video Promocional   - Costo: 8, Alcance: 180
Pop-up Interactivo  - Costo: 3, Alcance: 60
Story Patrocinada   - Costo: 4, Alcance: 90
Banner Lateral      - Costo: 2, Alcance: 40
Video Corto         - Costo: 6, Alcance: 130
```

---

## 🧪 Casos de Prueba Sugeridos

### Test 1: Asignación de Publicidad

```
Presupuesto: 10
Resultado esperado: Selección óptima que maximice alcance
```

### Test 2: Optimización de Portada

```
Espacio: 8 unidades
Resultado esperado: Publicaciones con mejor relación beneficio/tamaño
```

### Test 3: Recomendación de Amigos

```
Usuario: U001 (Ana)
Resultado esperado: Diego, Eva (distancia 2 desde Ana)
```

### Test 4: Rutas de Influencia

```
Origen: U001 (Ana)
Destino: U008 (Hugo)
Resultado esperado: Ana → Carla → Franco → Hugo (o similar)
```

### Test 5: Visualización

```
Vista por Relevancia: Top 3
Resultado esperado: Publicaciones ordenadas por relevancia
```

---

## 🎯 Mantenimiento de Algoritmos

### Garantías de Preservación

1. **Asignación de Publicidad**

   - Tabla DP: `dp[i][j]` - PRESERVADA
   - Reconstrucción de solución - PRESERVADA
   - Complejidad O(n × presupuesto) - PRESERVADA

2. **Optimización de Portada**

   - Tabla DP: `dp[i][j]` - PRESERVADA
   - Reconstrucción de solución - PRESERVADA
   - Complejidad O(n × espacio) - PRESERVADA

3. **Recomendación de Amigos**

   - BFS con cola - PRESERVADO
   - Mapa de distancias - PRESERVADO
   - Complejidad O(V + E) - PRESERVADA

4. **Rutas de Influencia**

   - BFS con rutas - PRESERVADO
   - Conjunto de visitados - PRESERVADO
   - Complejidad O(V + E) - PRESERVADA

5. **Visualización de Publicaciones**
   - PriorityQueue (Heap) - PRESERVADO
   - Comparador de relevancia - PRESERVADO
   - Complejidad O(n log n) - PRESERVADA

---

## 🚀 Próximas Mejoras Posibles

1. **Interfaz Gráfica**: Migrar a JavaFX o Swing
2. **Base de Datos**: Cambiar de archivos a SQLite/MySQL
3. **API REST**: Exponer funcionalidades vía HTTP
4. **Tests Unitarios**: JUnit para cada módulo
5. **Logs**: Sistema de logging con Log4j
6. **Configuración**: Archivo properties para configuraciones
7. **Internacionalización**: Soporte multi-idioma
8. **Seguridad**: Autenticación de usuarios
9. **Estadísticas**: Dashboard con métricas del sistema
10. **Exportación**: Generar reportes en PDF/Excel

---

## ✅ Checklist de Completitud

- [x] Menú interactivo con switch-case
- [x] Modularización completa
- [x] Datos compartidos (Usuario, Publicacion, Anuncio)
- [x] Sistema de persistencia en archivos
- [x] Algoritmos preservados (lógica intacta)
- [x] CRUD completo para todas las entidades
- [x] Inicialización automática de datos
- [x] Manejo de errores robusto
- [x] Documentación completa (README.md)
- [x] Scripts de compilación y ejecución
- [x] Código comentado y legible
- [x] Separación de responsabilidades (MVC-like)

---

## 📚 Referencias

- **Programación Dinámica**: Cormen et al., "Introduction to Algorithms"
- **BFS**: Sedgewick & Wayne, "Algorithms, 4th Edition"
- **Heap/PriorityQueue**: Java Collections Framework Documentation
- **Serialización**: Java Object Serialization Specification

---

**Fecha de integración**: Octubre 2025  
**Estado**: ✅ COMPLETO Y FUNCIONAL
