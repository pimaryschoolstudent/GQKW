package com.example.myapplication.Main.FileConnect.DataBase


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author HuangJiaHeng
 * @date 2019/10/16.
 */
@Entity
data class FileBean (@PrimaryKey(autoGenerate = true) var Id:Long, @ColumnInfo(name = "file") var file:String, @ColumnInfo(name = "fileName") var fileName:String, @ColumnInfo(name = "fileSize") var fileSize:String)