# Sistema de Red Social - TPO Algoritmos

## 📋 Descripción

Sistema integrado de red social que implementa algoritmos avanzados para análisis y optimización. El proyecto está completamente modularizado con persistencia de datos en archivos y un menú interactivo por consola.

## 🏗️ Arquitectura del Sistema

### Estructura de Paquetes

```
src/
├── App.java                    # Aplicación principal con menú
├── modelo/                     # Modelos de datos
│   ├── Usuario.java
│   ├── PublicacionModelo.java
│   └── Anuncio.java
├── datos/                      # Capa de persistencia
│   └── GestorDatos.java
├── servicio/                   # Lógica de negocio
│   ├── ServicioUsuarios.java
│   ├── ServicioPublicaciones.java
│   └── ServicioAnuncios.java
├── algoritmos/                 # Módulos de algoritmos
│   ├── AsignacionPublicidad.java
│   ├── OptimizacionPortada.java
│   ├── RecomendacionAmigos.java
│   ├── RutasInfluencia.java
│   └── GestionPublicaciones.java
└── util/                       # Utilidades
    └── DatosIniciales.java
```

## 🎯 Módulos Implementados

### 1. Asignación de Publicidad

- **Algoritmo**: Programación Dinámica (Problema de la Mochila 0/1)
- **Objetivo**: Maximizar el alcance publicitario con presupuesto limitado
- **Complejidad**: O(n × presupuesto)

### 2. Optimización de Portada

- **Algoritmo**: Programación Dinámica (Problema de la Mochila 0/1)
- **Objetivo**: Seleccionar publicaciones que maximicen el beneficio sin exceder el espacio
- **Complejidad**: O(n × espacio)

### 3. Recomendación de Amigos

- **Algoritmo**: BFS (Búsqueda en Amplitud)
- **Objetivo**: Calcular distancias en la red social y recomendar amigos
- **Complejidad**: O(V + E) donde V = vértices, E = aristas

### 4. Rutas de Influencia

- **Algoritmo**: BFS (Búsqueda en Amplitud)
- **Objetivo**: Encontrar la cadena de influencia más corta entre usuarios
- **Complejidad**: O(V + E)

### 5. Visualización de Publicaciones

- **Estructura de Datos**: Heap (PriorityQueue)
- **Vistas**: Cronológica y por Relevancia
- **Complejidad**: O(n log n) para vista por relevancia

## 💾 Sistema de Persistencia

Los datos se guardan automáticamente en **archivos de texto plano (CSV)**:

- `datos/usuarios.txt` - Usuarios (formato: id,nombre)
- `datos/amistades.txt` - Relaciones de amistad (formato: usuarioId,amigoId)
- `datos/publicaciones.txt` - Publicaciones con metadata
- `datos/anuncios.txt` - Anuncios publicitarios

**Ventajas del formato CSV**:

- ✅ Archivos legibles - se pueden abrir con Notepad o Excel
- ✅ Fácil de entender - solo lectura/escritura de texto
- ✅ No requiere conceptos avanzados (sin serialización)
- ✅ Simple de depurar y modificar manualmente

**Inicialización automática**: El sistema carga datos existentes o crea datos de prueba en la primera ejecución.

## 🚀 Compilación y Ejecución

### Compilar el proyecto:

```bash
javac -encoding UTF-8 -d bin src/App.java src/modelo/*.java src/datos/*.java src/servicio/*.java src/algoritmos/*.java src/util/*.java
```

### Ejecutar:

```bash
java -cp bin App
```

## 📖 Uso del Sistema

### Menú Principal

```
┌─────────────────── MENÚ PRINCIPAL ───────────────────┐
│                                                      │
│  1️⃣  Gestión de Usuarios                            │
│  2️⃣  Gestión de Publicaciones                       │
│  3️⃣  Gestión de Anuncios                            │
│                                                      │
│  ── Módulos de Algoritmos ──                        │
│  4️⃣  Asignación de Publicidad (Prog. Dinámica)      │
│  5️⃣  Optimización de Portada (Prog. Dinámica)       │
│  6️⃣  Recomendación de Amigos (BFS)                  │
│  7️⃣  Rutas de Influencia (BFS)                      │
│  8️⃣  Visualización de Publicaciones (Heap)          │
│                                                      │
│  9️⃣  Guardar Datos                                  │
│  0️⃣  Salir                                          │
└──────────────────────────────────────────────────────┘
```

### Funcionalidades por Módulo

#### Gestión de Usuarios (Opción 1)

- Listar todos los usuarios
- Agregar nuevos usuarios
- Crear relaciones de amistad

#### Gestión de Publicaciones (Opción 2)

- Listar publicaciones
- Crear nuevas publicaciones
- Dar "me gusta" a publicaciones

#### Gestión de Anuncios (Opción 3)

- Listar anuncios disponibles
- Agregar nuevos anuncios con costo y alcance

#### Algoritmos (Opciones 4-8)

Cada módulo ejecuta su algoritmo correspondiente con los datos actuales del sistema.

## 🔄 Datos Compartidos

Todos los módulos utilizan los mismos datos:

- **Usuarios**: Se comparten entre todos los módulos de grafos
- **Publicaciones**: Usadas por optimización, visualización y portada
- **Anuncios**: Específicos para asignación de publicidad

## 🎓 Conceptos Aplicados

### Programación Dinámica

- Problema de la Mochila 0/1
- Tabla de memoización
- Reconstrucción de soluciones óptimas

### Búsqueda en Grafos

- BFS para caminos más cortos
- Representación con listas de adyacencia
- Distancias en grafos no ponderados

### Estructuras de Datos

- Heap (PriorityQueue) para ordenamiento eficiente
- HashMap para acceso O(1)
- Archivos de texto (CSV) para persistencia simple

## 📊 Datos de Prueba

El sistema incluye datos iniciales:

- 8 usuarios con red de amistades conectada
- 8 publicaciones con diferentes características
- 6 anuncios publicitarios variados

## 🔧 Características Técnicas

- **Persistencia**: Archivos de texto plano (CSV) - fácil de leer y entender
- **Modularidad**: Separación clara de responsabilidades
- **Reutilización**: Servicios compartidos entre módulos
- **Extensibilidad**: Fácil agregar nuevos módulos
- **Interfaz**: Menú interactivo con switch-case

## 📝 Notas de Implementación

### Cálculo de Relevancia

```java
relevancia = likes - (antiguedad_dias × 0.5)
```

### Cálculo de Beneficio

```java
beneficio = likes + (relevancia × 0.5)
```

### Recomendación de Amigos

Se recomiendan usuarios a distancia 2 (amigos de amigos) en el grafo social.

## 🎯 Casos de Uso

1. **Campaña Publicitaria**: Optimizar inversión en anuncios
2. **Diseño de Portada**: Maximizar engagement en página principal
3. **Networking**: Sugerir nuevas conexiones relevantes
4. **Análisis de Influencia**: Identificar cadenas de conexión
5. **Content Feed**: Ordenar contenido por relevancia

## 🔐 Gestión de Datos

- **Guardado automático**: Al salir del sistema
- **Guardado manual**: Opción 9 del menú
- **Carga automática**: Al iniciar el sistema
- **Backup**: Los archivos .dat pueden copiarse para backup

## 🚦 Estados del Sistema

- ✅ **Inicializado**: Datos cargados o creados
- 💾 **Guardando**: Persistiendo cambios
- 🔄 **Procesando**: Ejecutando algoritmo
- ⚠️ **Advertencia**: Entrada inválida o dato no encontrado
- ❌ **Error**: Operación fallida

## 📚 Archivos Heredados

Los archivos originales (`AsignacionPublicidad.java`, `GestionPublicaciones.java`, etc.) han sido conservados como referencia histórica pero redirigen al sistema integrado.

## 🎉 Mejoras Implementadas

1. ✅ Integración completa de módulos
2. ✅ Sistema de persistencia en archivos
3. ✅ Menú interactivo por consola
4. ✅ Datos compartidos entre módulos
5. ✅ Modularización con servicios
6. ✅ Inicialización automática de datos
7. ✅ Algoritmos preservados intactos
8. ✅ Manejo robusto de errores

---

## Folder Structure

The workspace contains the following folders:

- `src`: source code organized by packages
  - `modelo/`: data models
  - `datos/`: persistence layer
  - `servicio/`: business logic services
  - `algoritmos/`: algorithm implementations
  - `util/`: utilities
- `lib`: dependencies (if needed)
- `bin`: compiled output files
- `datos/`: data files (created automatically)
