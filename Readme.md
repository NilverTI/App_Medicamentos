# 📱 MiHorarioMed

Aplicación Android desarrollada en **Kotlin + Jetpack Compose**
siguiendo arquitectura **MVVM** y consumo de **API REST**.

Permite gestionar horarios de medicamentos con las siguientes
funcionalidades:

-   ✅ Crear horarios
-   📋 Listar horarios
-   ✏️ Editar horarios
-   🗑 Eliminar horarios
-   🔎 Ver detalle completo
-   🔄 Filtrar por activos/inactivos

------------------------------------------------------------------------

## 🚀 API REST

Backend desplegado en Render:

🔗 https://apihorariomed.onrender.com\
📂 Repositorio API: https://github.com/NilverTI/ApiHorarioMed\
📂 Repositorio App: https://github.com/NilverTI/App_Medicamentos

------------------------------------------------------------------------

## 🏗 Arquitectura

La aplicación está organizada por capas:

-   **UI (MVVM)**
-   **Domain (Casos de uso)**
-   **Data (Repositorio + API)**
-   **Core (Utilidades y manejo de estados)**

------------------------------------------------------------------------

## 📂 Estructura del Proyecto

    com.tantalean.mihorariomed
    │
    ├── ui
    │   ├── navigation
    │   │   └── AppNavGraph.kt
    │   ├── screens
    │   │   ├── list
    │   │   │   ├── ScheduleListScreen.kt
    │   │   │   ├── ScheduleListViewModel.kt
    │   │   │   └── ScheduleListUiState.kt
    │   │   ├── form
    │   │   │   ├── ScheduleFormScreen.kt
    │   │   │   ├── ScheduleFormViewModel.kt
    │   │   │   └── ScheduleFormUiState.kt
    │   │   └── detail
    │   │       ├── ScheduleDetailScreen.kt
    │   │       ├── ScheduleDetailViewModel.kt
    │   │       └── ScheduleDetailUiState.kt
    │   └── components
    │       ├── ScheduleItem.kt
    │       └── LoadingErrorView.kt
    │
    ├── data
    │   ├── remote
    │   │   ├── ApiClient.kt
    │   │   └── ScheduleApiService.kt
    │   ├── dto
    │   │   ├── ScheduleRequest.kt
    │   │   └── ScheduleResponse.kt
    │   └── repository
    │       └── ScheduleRepositoryImpl.kt
    │
    ├── domain
    │   ├── model
    │   │   └── Schedule.kt
    │   ├── repository
    │   │   └── ScheduleRepository.kt
    │   └── usecase
    │       ├── GetSchedulesUseCase.kt
    │       ├── GetScheduleByIdUseCase.kt
    │       ├── CreateScheduleUseCase.kt
    │       ├── UpdateScheduleUseCase.kt
    │       └── DeleteScheduleUseCase.kt
    │
    ├── core
    │   ├── result
    │   │   └── Resource.kt
    │   └── utils
    │       └── Validators.kt
    │
    └── MainActivity.kt

------------------------------------------------------------------------

## ⚙️ Tecnologías utilizadas

-   Kotlin
-   Jetpack Compose
-   MVVM
-   Retrofit
-   Coroutines
-   Flow
-   Material 3

------------------------------------------------------------------------

## 🎯 Requisitos Académicos Cumplidos

✔ Navegación\
✔ Lista con estado activo/inactivo\
✔ Formulario crear/editar\
✔ Pantalla de detalle\
✔ CRUD completo\
✔ Mensajes de éxito y error\
✔ Arquitectura por capas\
✔ API REST separada

------------------------------------------------------------------------

## 👨‍💻 Autor

**Nilver T.I.**\
Proyecto académico -- Desarrollo de Apps Android
