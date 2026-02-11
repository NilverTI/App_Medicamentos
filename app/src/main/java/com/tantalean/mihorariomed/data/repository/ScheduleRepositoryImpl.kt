package com.tantalean.mihorariomed.data.repository

import com.tantalean.mihorariomed.core.result.Resource
import com.tantalean.mihorariomed.data.dto.ScheduleRequest
import com.tantalean.mihorariomed.data.dto.ScheduleResponse
import com.tantalean.mihorariomed.data.remote.ScheduleApiService
import com.tantalean.mihorariomed.domain.model.Schedule
import com.tantalean.mihorariomed.domain.repository.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleRepositoryImpl(
    private val api: ScheduleApiService
) : ScheduleRepository {

    override suspend fun getSchedules(): Resource<List<Schedule>> = withContext(Dispatchers.IO) {
        try {
            val list = api.getSchedules().map { it.toDomain() }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error("Error al listar horarios.", e)
        }
    }

    override suspend fun getScheduleById(id: Long): Resource<Schedule> = withContext(Dispatchers.IO) {
        try {
            Resource.Success(api.getScheduleById(id).toDomain())
        } catch (e: Exception) {
            Resource.Error("Error al obtener detalle.", e)
        }
    }

    override suspend fun createSchedule(schedule: Schedule): Resource<Schedule> = withContext(Dispatchers.IO) {
        try {
            val created = api.createSchedule(schedule.toRequest()).toDomain()
            Resource.Success(created)
        } catch (e: Exception) {
            Resource.Error("Error al crear horario.", e)
        }
    }

    override suspend fun updateSchedule(id: Long, schedule: Schedule): Resource<Schedule> = withContext(Dispatchers.IO) {
        try {
            val updated = api.updateSchedule(id, schedule.toRequest()).toDomain()
            Resource.Success(updated)
        } catch (e: Exception) {
            Resource.Error("Error al actualizar horario.", e)
        }
    }

    override suspend fun deleteSchedule(id: Long): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            api.deleteSchedule(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error al eliminar horario.", e)
        }
    }

    private fun ScheduleResponse.toDomain() = Schedule(
        id = id,
        nombre = nombre,
        dosis = dosis,
        hora = hora,
        frecuencia = frecuencia,
        notas = notas,
        activo = activo
    )

    private fun Schedule.toRequest() = ScheduleRequest(
        nombre = nombre,
        dosis = dosis,
        hora = hora,
        frecuencia = frecuencia,
        notas = notas,
        activo = activo
    )
}
