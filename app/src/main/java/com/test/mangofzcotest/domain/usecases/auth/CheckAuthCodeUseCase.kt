package com.test.mangofzcotest.domain.usecases.auth

import com.test.mangofzcotest.domain.entities.LoginData
import com.test.mangofzcotest.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthCodeUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(phone: String, code: String): Result<LoginData> {
        return authRepository.checkAuthCode(phone, code)
    }

}