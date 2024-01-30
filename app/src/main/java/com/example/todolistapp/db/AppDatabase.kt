package com.example.todolistapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
room database의 조건

1. @Database annotation
2. Roomdatabase를 상속받아야함
3. Dao를 반환하는 추상함수

데이터베이스 객체는 싱글톤(전체 프로젝트에서  1개만 객체를 만든다)

 */


@Database(entities = arrayOf(TodoEntity::class),version = 1) // 조건 1
abstract class AppDatabase : RoomDatabase(){ //조건 2

    abstract fun getTodoDao() : TodoDao


    companion object{
        val databaseName = "db_todo"
        var appDatabase : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? { //싱글톤 패턴 -> 전체 프로젝트에서 객체를 1개만 생성하기 위해 null일 때만 생성함
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    databaseName).
                        fallbackToDestructiveMigration()
                    .build()
            }

            return appDatabase
        }

    }




}