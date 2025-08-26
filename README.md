# XY01 IPTV Player

Un reproductor IPTV para Android que utiliza la API de Xtream Codes con gestión de perfiles y sistema de caché.

## Características

### ✅ Características Implementadas

- **Gestión de Perfiles**: Permite guardar múltiples perfiles de servidores IPTV
- **API Xtream Codes**: Integración completa con la API de Xtream Codes
- **Sistema de Caché**: Almacenamiento local de canales, películas y series después de seleccionar el perfil
- **Interfaz en Vertical**: Diseñada específicamente para dispositivos móviles en orientación portrait
- **Navegación por Pestañas**: Categorías y canales organizados en la parte inferior de la pantalla
- **Reproductor de Video**: Integrado con ExoPlayer para reproducción fluida
- **Base de Datos Local**: Utiliza Room para almacenamiento persistente

### 🏗️ Arquitectura

- **MVVM Pattern**: Utiliza ViewModel y LiveData/StateFlow
- **Room Database**: Para almacenamiento local de perfiles y contenido
- **Retrofit**: Para comunicación con la API de Xtream Codes
- **ExoPlayer**: Para reproducción de video
- **Material Design**: UI moderna siguiendo las pautas de Google

## Estructura del Proyecto

```
app/src/main/java/com/xy01/iptvplayer/
├── data/
│   ├── api/          # Servicios de API y modelos de respuesta
│   ├── database/     # DAOs y base de datos Room
│   ├── model/        # Modelos de datos (Profile, Channel, Movie, Series)
│   └── repository/   # Repositorios para gestión de datos
├── ui/
│   ├── main/         # Activity principal y fragmentos
│   ├── player/       # Activity del reproductor
│   └── profile/      # Gestión de perfiles
└── utils/            # Utilidades (construcción de URLs)
```

## Uso de la Aplicación

### 1. Crear un Perfil

1. Al abrir la aplicación, presiona el botón FAB (+) para crear un nuevo perfil
2. Completa los campos:
   - **Nombre del Perfil**: Un nombre descriptivo para identificar el servidor
   - **URL del Servidor**: La URL del servidor Xtream Codes (ej: `http://server.com:8080`)
   - **Usuario**: Tu nombre de usuario
   - **Contraseña**: Tu contraseña

### 2. Seleccionar un Perfil

- En la pantalla de perfiles, toca cualquier perfil guardado para activarlo
- El perfil activo se indica con un ícono de verificación verde
- Al seleccionar un perfil, automáticamente se cargarán y almacenarán en caché los canales, películas y series

### 3. Navegar por el Contenido

#### Canales en Vivo
- Toca la pestaña "Live TV" en la parte inferior
- Navega por la lista de canales disponibles
- Toca cualquier canal para comenzar la reproducción

#### Películas
- Toca la pestaña "Movies" en la parte inferior
- Explora las películas disponibles en formato de cuadrícula
- Toca cualquier película para reproducirla

#### Series
- Toca la pestaña "Series" en la parte inferior
- Navega por las series disponibles
- Toca cualquier serie para reproducirla (implementación básica)

### 4. Reproducir Contenido

- Al seleccionar cualquier contenido, se abrirá el reproductor en pantalla completa
- El reproductor soporta controles táctiles estándar
- La pantalla se mantiene encendida durante la reproducción
- Presiona el botón de retroceso para salir del reproductor

## Configuración de Desarrollo

### Requisitos

- Android Studio Arctic Fox o superior
- JDK 8 o superior
- Android SDK API 24 (Android 7.0) o superior
- Dispositivo/emulador con Android 7.0+

### Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/Luis-Segura-web/xy01.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza las dependencias de Gradle

4. Ejecuta en un dispositivo o emulador

### Dependencias Principales

```gradle
// Interfaz de Usuario
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.11.0")

// Base de Datos
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Reproductor de Video
implementation("androidx.media3:media3-exoplayer:1.2.1")
implementation("androidx.media3:media3-ui:1.2.1")

// Carga de Imágenes
implementation("com.github.bumptech.glide:glide:4.16.0")
```

## API de Xtream Codes

La aplicación utiliza los siguientes endpoints de Xtream Codes:

- `GET /player_api.php?action=get_user_info` - Autenticación
- `GET /player_api.php?action=get_live_categories` - Categorías de canales
- `GET /player_api.php?action=get_vod_categories` - Categorías de películas  
- `GET /player_api.php?action=get_series_categories` - Categorías de series
- `GET /player_api.php?action=get_live_streams` - Lista de canales
- `GET /player_api.php?action=get_vod_streams` - Lista de películas
- `GET /player_api.php?action=get_series` - Lista de series

### Formato de URLs de Streaming

- **Canales**: `{servidor}/live/{usuario}/{contraseña}/{stream_id}.ts`
- **Películas**: `{servidor}/movie/{usuario}/{contraseña}/{stream_id}.mp4`
- **Series**: `{servidor}/series/{usuario}/{contraseña}/{stream_id}.mp4`

## Características Técnicas

- **Orientación**: Forzada a portrait para optimizar la experiencia móvil
- **Caché**: El contenido se almacena localmente después de la primera sincronización
- **Reproductor**: Pantalla completa con controles auto-ocultables
- **UI**: Bottom Navigation para navegación intuitiva
- **Permisos**: Solo requiere acceso a Internet

## Limitaciones Conocidas

1. **Series**: Implementación básica sin selección de episodios específicos
2. **Búsqueda**: No implementada en esta versión
3. **EPG**: Guía de programación no incluida
4. **Favoritos**: Sistema de marcadores no implementado

## Próximas Mejoras

- [ ] Implementar búsqueda de contenido
- [ ] Agregar sistema de favoritos
- [ ] Mejorar la navegación por episodios de series
- [ ] Implementar guía de programación (EPG)
- [ ] Agregar soporte para subtítulos
- [ ] Implementar modo landscape para el reproductor

## Contribución

Este proyecto está en desarrollo activo. Las contribuciones son bienvenidas mediante pull requests.

## Licencia

[Especifica tu licencia aquí]

---

**Nota**: Esta aplicación requiere acceso a un servidor IPTV compatible con Xtream Codes para funcionar correctamente.