# Deployment Instructions - XY01 IPTV Player

## Requisitos de Sistema

### Desarrollo
- **Android Studio**: Arctic Fox (2020.3.1) o superior
- **JDK**: 8 o superior
- **Gradle**: 8.2 (incluido en el proyecto)
- **Android SDK**: API 24 (Android 7.0) mínimo, recomendado API 34

### Dispositivo de Destino
- **Android**: 7.0 (API 24) o superior
- **RAM**: Mínimo 2GB, recomendado 4GB+
- **Almacenamiento**: 50MB para la aplicación + espacio para caché
- **Conexión**: Internet estable para streaming

## Configuración del Entorno de Desarrollo

### 1. Instalar Android Studio
```bash
# Descargar desde: https://developer.android.com/studio
# Instalar Android SDK con API levels 24-34
```

### 2. Clonar y Configurar el Proyecto
```bash
git clone https://github.com/Luis-Segura-web/xy01.git
cd xy01
```

### 3. Abrir en Android Studio
1. Abre Android Studio
2. Selecciona "Open an Existing Project"
3. Navega hasta la carpeta `xy01`
4. Permite que Gradle sincronice las dependencias

### 4. Configurar SDK
Asegúrate de tener instalados:
- Android SDK Platform 34
- Android Build Tools 34.0.0
- Google Play Services
- Android Support Repository

## Compilación

### Debug Build
```bash
./gradlew assembleDebug
# APK generado en: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build
```bash
./gradlew assembleRelease
# APK generado en: app/build/outputs/apk/release/app-release.apk
```

### Tests
```bash
./gradlew testDebugUnitTest
./gradlew connectedAndroidTest
```

## Configuración para Producción

### 1. Signing Config
Editar `app/build.gradle.kts`:
```kotlin
android {
    signingConfigs {
        release {
            storeFile file("path/to/keystore.jks")
            storePassword "your_store_password"
            keyAlias "your_key_alias"
            keyPassword "your_key_password"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled true
            signingConfig signingConfigs.release
        }
    }
}
```

### 2. ProGuard/R8 (Ya configurado)
El proyecto ya incluye configuración básica de ofuscación en `proguard-rules.pro`.

### 3. App Bundle (Recomendado)
```bash
./gradlew bundleRelease
# Bundle generado en: app/build/outputs/bundle/release/app-release.aab
```

## Instalación en Dispositivo

### Método 1: ADB
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Método 2: Android Studio
1. Conecta el dispositivo con depuración USB habilitada
2. Haz clic en "Run" en Android Studio

### Método 3: APK Manual
1. Transfiere el APK al dispositivo
2. Habilita "Fuentes desconocidas" en configuración
3. Instala el APK desde el explorador de archivos

## Configuración del Servidor IPTV

La aplicación requiere acceso a un servidor IPTV compatible con Xtream Codes:

### Formato de Servidor
- **URL**: `http://servidor.com:puerto` o `https://servidor.com:puerto`
- **Usuario**: Proporcionado por el proveedor IPTV
- **Contraseña**: Proporcionada por el proveedor IPTV

### Ejemplo de Configuración
```
Nombre: Mi Servidor IPTV
URL: http://my-server.com:8080
Usuario: mi_usuario
Contraseña: mi_contraseña
```

## Troubleshooting

### Problemas Comunes

#### 1. Error de Compilación
```
Plugin 'com.android.application' not found
```
**Solución**: Verificar que Android SDK esté instalado correctamente.

#### 2. Error de Networking
```
java.net.ConnectException: Failed to connect
```
**Solución**: 
- Verificar conexión a internet
- Verificar URL del servidor IPTV
- Verificar credenciales

#### 3. Error de Base de Datos
```
SQLiteException: no such table
```
**Solución**: Limpiar datos de la aplicación o desinstalar/reinstalar.

### Logs de Debug
Para habilitar logs detallados, agregar en `Application.onCreate()`:
```kotlin
if (BuildConfig.DEBUG) {
    Log.d("IPTV", "Debug mode enabled")
}
```

## Performance

### Optimizaciones Implementadas
- **Caché local**: Reduce llamadas a la API
- **Glide**: Carga eficiente de imágenes
- **ExoPlayer**: Reproductor optimizado
- **Room**: Base de datos eficiente

### Métricas Esperadas
- **Tiempo de inicio**: < 2 segundos
- **Carga de contenido**: < 5 segundos (primera vez)
- **Cambio de canal**: < 3 segundos
- **Uso de memoria**: < 100MB en uso normal

## Distribución

### Google Play Store
1. Crear cuenta de desarrollador
2. Configurar firma de aplicación
3. Subir AAB (recomendado) o APK
4. Completar información de la store

### Distribución Directa
1. Generar APK firmado
2. Distribuir mediante:
   - Sitio web
   - Firebase App Distribution
   - Email/transferencia directa

## Monitoreo

### Firebase Crashlytics (Opcional)
Agregar dependencia para monitoreo de crashes:
```kotlin
implementation 'com.google.firebase:firebase-crashlytics-ktx'
```

### Analytics (Opcional)
Para estadísticas de uso:
```kotlin
implementation 'com.google.firebase:firebase-analytics-ktx'
```

## Actualizaciones

### Versionado
El proyecto usa versionado semántico:
- `versionCode`: Número entero incremental
- `versionName`: "MAJOR.MINOR.PATCH"

### Auto-Update
Consideraciones para implementar actualizaciones automáticas:
- In-app updates de Google Play
- Verificación manual de versiones
- Delta updates para reducir tamaño

---

**Notas Importantes:**
- Siempre probar en dispositivos reales antes de distribuir
- Verificar permisos y funcionalidad en diferentes versiones de Android
- Considerar limitaciones de red y dispositivos de gama baja
- Mantener logs y analytics para diagnosticar problemas en producción