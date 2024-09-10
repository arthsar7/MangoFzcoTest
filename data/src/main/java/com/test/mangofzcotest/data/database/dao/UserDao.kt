package com.test.mangofzcotest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.test.mangofzcotest.data.database.entities.UserProfileDbModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfileDbModel: UserProfileDbModel)

    @Update
    suspend fun updateUserProfile(userProfileDbModel: UserProfileDbModel)

    @Query("SELECT * FROM user_profile_table WHERE id = :id")
    suspend fun getUserProfile(id: Int): UserProfileDbModel?

}