# 📱 MiHorarioMed

Aplicación Android desarrollada en **Kotlin + Jetpack Compose**
siguiendo arquitectura **MVVM**, conectada a una **API REST** desplegada
en Render.

Proyecto académico para el curso de Desarrollo de Apps.

------------------------------------------------------------------------

## 🚀 Descripción

**MiHorarioMed** permite gestionar horarios de medicamentos de forma
sencilla e intuitiva.

Funcionalidades principales:

-   ✅ Crear horarios de medicamentos\
-   📋 Listar horarios\
-   ✏️ Editar horarios\
-   🗑 Eliminar horarios\
-   🔎 Ver detalle completo\
-   🔄 Filtrar solo horarios activos

La aplicación consume una API REST propia desarrollada en Spring Boot.

------------------------------------------------------------------------

## 🏗 Arquitectura

### 📲 App Android

-   Kotlin
-   Jetpack Compose
-   MVVM
-   Retrofit
-   StateFlow
-   Material 3

Estructura por capas:

    ui (navigation, screens, components)
    domain (model, repository, usecase)
    data (remote, dto, repository)
    core (utils, result)

------------------------------------------------------------------------

### 🌐 API REST

-   Spring Boot
-   Controller / Service / Repository
-   CRUD completo
-   Desplegada en Render

🔗 API en producción: https://apihorariomed.onrender.com

📂 Repositorio API: https://github.com/NilverTI/ApiHorarioMed

📂 Repositorio App Android: https://github.com/NilverTI/App_Medicamentos

------------------------------------------------------------------------

## 📦 Endpoints principales

  Método   Endpoint
  -------- -----------------
  GET      /schedules
  GET      /schedules/{id}
  POST     /schedules
  PUT      /schedules/{id}
  DELETE   /schedules/{id}

------------------------------------------------------------------------

## 🎯 Funcionales requeridos

✔ Navegación entre pantallas\
✔ Lista con estado activo/inactivo\
✔ Formulario crear/editar\
✔ Detalle completo\
✔ Mensajes de éxito/error\
✔ Arquitectura por capas

------------------------------------------------------------------------

## 🎨 Diseño

Diseño minimalista, moderno y enfocado en usabilidad. Colores
principales: tonos violeta y morado con Material 3.

------------------------------------------------------------------------

## 👨‍💻 Autor

Nilver Tantalean Inga\
Proyecto académico -- Desarrollo de Aplicaciones

------------------------------------------------------------------------

## 📌 Estado del proyecto

✔ API desplegada\
✔ CRUD funcionando\
✔ Integración completa App ↔ API\
✔ Arquitectura limpia implementada

------------------------------------------------------------------------

# 💡 Proyecto MiHorarioMed

Aplicación completa Android + API REST con arquitectura profesional.
