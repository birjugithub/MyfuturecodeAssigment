package com.myfuturecodeassigment.data.repository

import com.myfuturecodeassigment.data.api.ApiService
import com.myfuturecodeassigment.data.db.UserDao
import com.myfuturecodeassigment.utils.NetworkHelper
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val dao: UserDao,
    private val networkHelper: NetworkHelper
) {
    fun getUsers() = dao.getUsers()
    suspend fun fetchUsers() {
        if (networkHelper.isInternetAvailable()) {
            try {
                val response = api.getUsers()
                dao.insertUsers(response.users)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }else{
            // ❗ No internet → do nothing
            // Room data will automatically show
        }
    }

        fun search(query: String) =
            dao.searchUsers("%$query%")

}
