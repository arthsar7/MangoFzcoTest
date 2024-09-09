package com.test.mangofzcotest.domain.usecases.auth

import com.test.mangofzcotest.domain.repository.AuthRepository
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(phone: String) = repository.sendAuthCode(phone)

}