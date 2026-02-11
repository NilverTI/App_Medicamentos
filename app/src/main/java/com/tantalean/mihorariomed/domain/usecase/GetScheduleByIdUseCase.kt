package com.tantalean.mihorariomed.domain.usecase

import com.tantalean.mihorariomed.domain.repository.ScheduleRepository

class GetScheduleByIdUseCase(private val repo: ScheduleRepository) {
    suspend operator fun invoke(id: Long) = repo.getScheduleById(id)
}
