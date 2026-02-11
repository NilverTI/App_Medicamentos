package com.tantalean.mihorariomed.domain.usecase

import com.tantalean.mihorariomed.domain.repository.ScheduleRepository

class GetSchedulesUseCase(private val repo: ScheduleRepository) {
    suspend operator fun invoke() = repo.getSchedules()
}
