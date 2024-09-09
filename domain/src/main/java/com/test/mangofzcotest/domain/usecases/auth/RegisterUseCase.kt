package com.test.mangofzcotest.domain.usecases.auth

import com.test.mangofzcotest.domain.entities.TokenData
import com.test.mangofzcotest.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(phone: String, name: String, username: String): Result<TokenData> {
        return repository.register(phone, name, username)
    }

}