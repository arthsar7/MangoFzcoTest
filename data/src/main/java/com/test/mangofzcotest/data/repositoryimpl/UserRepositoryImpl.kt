package com.test.mangofzcotest.data.repositoryimpl

import com.test.mangofzcotest.data.database.dao.UserDao
import com.test.mangofzcotest.data.database.entities.UserProfileDbModel
import com.test.mangofzcotest.data.network.apiservice.ApiService
import com.test.mangofzcotest.data.utils.safeApiCall
import com.test.mangofzcotest.data.utils.toDbModel
import com.test.mangofzcotest.data.utils.toDomain
import com.test.mangofzcotest.data.utils.toDto
import com.test.mangofzcotest.domain.entities.UserProfileData
import com.test.mangofzcotest.domain.entities.UserUpdateData
import com.test.mangofzcotest.domain.repository.UserRepository
import com.test.mangofzcotest.domain.storage.PreferencesManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager,
    private val userDao: UserDao
): UserRepository {
    override suspend fun getUserProfile(): Result<UserProfileData> {
        val userId = preferencesManager.userId.first()
        val userDb = userDao.getUserProfile(userId)
        return if (userDb == null) {
            getApiUserProfile()
        }
        else {
            Result.success(userDb.toDomain())
        }
    }

    private suspend fun getApiUserProfile(): Result<UserProfileData> {
        return safeApiCall(apiService::getUserProfile).map { userProfileResponse ->
            userDao.insertUserProfile(userProfileResponse.toDbModel())
            userProfileResponse.toDomain()
        }
    }

    override suspend fun updateUserProfile(profileUpdate: UserUpdateData): Result<UserProfileData> = safeApiCall {
        apiService.updateUserProfile(profileUpdate.toDto())
    }.onSuccess {
        val currentUser: UserProfileDbModel? = userDao.getUserProfile(preferencesManager.userId.first())
        if (currentUser != null) {
            val bigAvatar = it.avatarsResponse?.bigAvatar
            val miniAvatar = it.avatarsResponse?.miniAvatar
            val avatar = it.avatarsResponse?.avatar
            userDao.updateUserProfile(
                profileUpdate.toDbModel(
                    bigAvatar = bigAvatar,
                    miniAvatar = miniAvatar,
                    avatar = avatar,
                    currentUser = currentUser
                )
            )
        }
    }.mapCatching {
        userDao.getUserProfile(preferencesManager.userId.first())!!.toDomain()
    }

}