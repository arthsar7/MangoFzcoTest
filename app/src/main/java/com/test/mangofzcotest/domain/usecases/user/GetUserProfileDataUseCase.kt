package com.test.mangofzcotest.domain.usecases.user

import com.test.mangofzcotest.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = userRepository.getUserProfile()

}