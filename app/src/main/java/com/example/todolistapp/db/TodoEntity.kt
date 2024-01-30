package com.example.todolistapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
annotation
- 자바(코틀린)코드에 추가할 수 있는 메타데이터의 ㅎ ㅕㅇ태
- 보일러 플레이트 코드(무의미하게 반복되는 코드)를 줄여 간결한 코드작성이 가능
- Room,Retrofit 라이브러리에서 주로 사용
 */

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) var id : Int? = null, //id값 자동생성
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "importance") var importance : Int,
)