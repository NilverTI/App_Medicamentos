package com.tantalean.mihorariomed.ui.screens.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tantalean.mihorariomed.core.result.Resource
import com.tantalean.mihorariomed.core.utils.ServiceLocator
import com.tantalean.mihorariomed.domain.model.Schedule
import com.tantalean.mihorariomed.domain.usecase.CreateScheduleUseCase
import com.tantalean.mihorariomed.domain.usecase.GetScheduleByIdUseCase
import com.tantalean.mihorariomed.domain.usecase.UpdateScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ScheduleFormViewModel(
    private val getById: GetScheduleByIdUseCase,
    private val create: CreateScheduleUseCase,
    private val update: UpdateScheduleUseCase
) : ViewModel() {

    constructor() : this(
        getById = ServiceLocator.getScheduleByIdUseCase(),
        create = ServiceLocator.createScheduleUseCase(),
        update = ServiceLocator.updateScheduleUseCase()
    )

    private val _uiState = MutableStateFlow(ScheduleFormUiState())
    val uiState: StateFlow<ScheduleFormUiState> = _uiState

    private fun isValidHour(value: String): Boolean {
        val p = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$")
        return p.matcher(value.trim()).matches()
    }

    fun loadIfEdit(scheduleId: Long?) {
        if (scheduleId == null) {
            _uiState.value = ScheduleFormUiState()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, successMessage = null, savedId = null) }

            when (val res = getById(scheduleId)) {
                is Resource.Success -> {
                    val s = res.data
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            id = s.id,
                            nombre = s.nombre,
                            dosis = s.dosis,
                            hora = s.hora,
                            frecuencia = s.frecuencia,
                            notas = s.notas,
                            activo = s.activo,
                            nombreError = null,
                            horaError = null
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = res.message ?: "Error al cargar.") }
                }

                Resource.Loading -> Unit
            }
        }
    }

    fun onNombre(value: String) = _uiState.update { it.copy(nombre = value, nombreError = null, error = null, successMessage = null) }
    fun onDosis(value: String) = _uiState.update { it.copy(dosis = value, error = null, successMessage = null) }
    fun onHora(value: String) = _uiState.update { it.copy(hora = value, horaError = null, error = null, successMessage = null) }
    fun onFrecuencia(value: String) = _uiState.update { it.copy(frecuencia = value, error = null, successMessage = null) }
    fun onNotas(value: String) = _uiState.update { it.copy(notas = value, error = null, successMessage = null) }
    fun onActivo(value: Boolean) = _uiState.update { it.copy(activo = value, error = null, successMessage = null) }

    fun consumeSaved() {
        _uiState.update { it.copy(savedId = null) }
    }

    fun save() {
        val st = _uiState.value

        var nombreError: String? = null
        var horaError: String? = null

        if (st.nombre.trim().isEmpty()) nombreError = "El nombre es obligatorio."
        if (st.hora.trim().isEmpty()) horaError = "La hora es obligatoria."
        else if (!isValidHour(st.hora)) horaError = "Formato inválido (HH:mm)."

        if (nombreError != null || horaError != null) {
            _uiState.update { it.copy(nombreError = nombreError, horaError = horaError) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, successMessage = null, savedId = null) }

            val schedule = Schedule(
                id = st.id ?: 0L,
                nombre = st.nombre.trim(),
                dosis = st.dosis.trim(),
                hora = st.hora.trim(),
                frecuencia = st.frecuencia.trim(),
                notas = st.notas.trim(),
                activo = st.activo
            )

            val result = if (st.id == null) create(schedule) else update(st.id, schedule)

            when (result) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        id = result.data.id,
                        savedId = result.data.id,
                        successMessage = if (st.id == null) "Horario creado ✅" else "Horario actualizado ✅"
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(isLoading = false, error = result.message ?: "Error al guardar.")
                }

                Resource.Loading -> Unit
            }
        }
    }
}
