package com.test.mangofzcotest.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserProfileDbModel.TABLE_NAME)
data class UserProfileDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val last: String?,
    val online: Boolean,
    val created: String?,
    val phone: String,
    val completedTask: Int,
    val bigAvatar: String?,
    val miniAvatar: String?
) {
    companion object {
        const val TABLE_NAME = "user_profile_table"
    }
}
