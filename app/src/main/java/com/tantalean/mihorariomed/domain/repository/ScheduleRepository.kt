package com.tantalean.mihorariomed.domain.repository

import com.tantalean.mihorariomed.core.result.Resource
import com.tantalean.mihorariomed.domain.model.Schedule

interface ScheduleRepository {
    suspend fun getSchedules(): Resource<List<Schedule>>
    suspend fun getScheduleById(id: Long): Resource<Schedule>
    suspend fun createSchedule(schedule: Schedule): Resource<Schedule>
    suspend fun updateSchedule(id: Long, schedule: Schedule): Resource<Schedule>
    suspend fun deleteSchedule(id: Long): Resource<Unit>
}
