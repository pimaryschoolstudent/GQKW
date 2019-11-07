package com.example.myapplication.Main.FileConnect.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.Main.FileConnect.Bean.FileData

/**
 * @author HuangJiaHeng
 * @date 2019/10/16.
 */
@Dao
interface FileDataDao {
    @Query("select * from FileBean")
   fun loadAll(): List<FileBean>
    @Insert
    fun insertAll(vararg fb: FileBean)
    @Query("DELETE FROM FileBean")
    fun deleteAll()
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
}