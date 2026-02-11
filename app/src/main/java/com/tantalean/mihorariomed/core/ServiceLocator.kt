package com.tantalean.mihorariomed.core.utils

import com.tantalean.mihorariomed.data.remote.ApiClient
import com.tantalean.mihorariomed.data.repository.ScheduleRepositoryImpl
import com.tantalean.mihorariomed.domain.usecase.DeleteScheduleUseCase
import com.tantalean.mihorariomed.domain.usecase.GetScheduleByIdUseCase
import com.tantalean.mihorariomed.domain.usecase.GetSchedulesUseCase
import com.tantalean.mihorariomed.domain.usecase.UpdateScheduleUseCase
import com.tantalean.mihorariomed.domain.usecase.CreateScheduleUseCase

object ServiceLocator {

    private val api by lazy { ApiClient.scheduleApi }

    private val scheduleRepository by lazy {
        ScheduleRepositoryImpl(api)
    }

    fun getSchedulesUseCase() = GetSchedulesUseCase(scheduleRepository)

    fun getScheduleByIdUseCase() = GetScheduleByIdUseCase(scheduleRepository)

    fun createScheduleUseCase() = CreateScheduleUseCase(scheduleRepository)

    fun updateScheduleUseCase() = UpdateScheduleUseCase(scheduleRepository)

    fun deleteScheduleUseCase() = DeleteScheduleUseCase(scheduleRepository)
}
