package com.test.mangofzcotest.domain.usecases.auth

import com.test.mangofzcotest.domain.repository.TokenRepository
import javax.inject.Inject

class RefreshAccessTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {

    suspend operator fun invoke() = repository.refreshAccessToken()

}