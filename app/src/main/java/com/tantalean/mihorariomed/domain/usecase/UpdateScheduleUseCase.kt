package com.tantalean.mihorariomed.domain.usecase

import com.tantalean.mihorariomed.domain.model.Schedule
import com.tantalean.mihorariomed.domain.repository.ScheduleRepository

class UpdateScheduleUseCase(private val repo: ScheduleRepository) {
    suspend operator fun invoke(id: Long, schedule: Schedule) = repo.updateSchedule(id, schedule)
}
