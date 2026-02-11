package com.tantalean.mihorariomed.domain.usecase

import com.tantalean.mihorariomed.domain.model.Schedule
import com.tantalean.mihorariomed.domain.repository.ScheduleRepository

class CreateScheduleUseCase(private val repo: ScheduleRepository) {
    suspend operator fun invoke(schedule: Schedule) = repo.createSchedule(schedule)
}
