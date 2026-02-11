package com.tantalean.mihorariomed.data.remote

import com.tantalean.mihorariomed.data.dto.ScheduleRequest
import com.tantalean.mihorariomed.data.dto.ScheduleResponse
import retrofit2.http.*

interface ScheduleApiService {

    @GET("schedules")
    suspend fun getSchedules(): List<ScheduleResponse>

    @GET("schedules/{id}")
    suspend fun getScheduleById(@Path("id") id: Long): ScheduleResponse

    @POST("schedules")
    suspend fun createSchedule(@Body body: ScheduleRequest): ScheduleResponse

    @PUT("schedules/{id}")
    suspend fun updateSchedule(
        @Path("id") id: Long,
        @Body body: ScheduleRequest
    ): ScheduleResponse

    @DELETE("schedules/{id}")
    suspend fun deleteSchedule(@Path("id") id: Long)
}
