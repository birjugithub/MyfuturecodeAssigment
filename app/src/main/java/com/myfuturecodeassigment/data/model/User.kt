package com.myfuturecodeassigment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserResponse(
    val users: List<User>
)

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val mobile: Long,
    val age: Int,
    val gender: String,
    val city: String,
    val profileImage: String,
    val bio: String,
    val rating: Double,
    val followers: Int,
    val following: Int,
    val isOnline: Boolean,
    val isVerified: Boolean,
    val isPremium: Boolean,
    val status: String,
    val joinedDate: String
)