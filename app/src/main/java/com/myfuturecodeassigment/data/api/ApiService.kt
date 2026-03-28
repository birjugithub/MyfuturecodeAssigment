package com.myfuturecodeassigment.data.api

import com.myfuturecodeassigment.data.model.UserResponse
import retrofit2.http.GET

interface ApiService {
    @GET("user.json")
    suspend fun getUsers(): UserResponse
}