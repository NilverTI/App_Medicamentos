package com.tantalean.mihorariomed.domain.usecase

import com.tantalean.mihorariomed.domain.repository.ScheduleRepository

class DeleteScheduleUseCase(private val repo: ScheduleRepository) {
    suspend operator fun invoke(id: Long) = repo.deleteSchedule(id)
}
