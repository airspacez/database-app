package com.example.dbapplication.DAO
import androidx.room.*
import com.example.dbapplication.Entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("DELETE FROM users WHERE id = :Id")
    fun deleteUserById(Id: Int)
}