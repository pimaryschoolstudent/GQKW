package com.example.myapplication.Main.FileConnect.DataBase

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Main.FileConnect.Bean.FileData

/**
 * @author HuangJiaHeng
 * @date 2019/10/16.
 */
@Database(entities =[FileBean::class],version = 1)
abstract class FileDataBase : RoomDatabase() {
    companion object{
        const val  DB_NAME = "FileData.db"
        private var context:Context ?=null
        private var database:FileDataBase?=null
        fun getInstance(context: Context):FileDataBase{
            this.context=context
            if (database==null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    FileDataBase::class.java, "DB_NAME"
                ).build()
            }
            return database!!
        }
    }
    abstract fun getDao(): FileDataDao
    fun loadDataBaseData():List<FileBean>{
        Log.e(DB_NAME,"查询数据")
        return getDao().loadAll()
    }
    fun insertFile(file:FileBean){
        Log.e(DB_NAME,"插入数据")
        getDao().insertAll(file)
    }
    fun deleteAllData(){
        Log.e(DB_NAME,"清空数据")
        getDao().deleteAll()
    }
}