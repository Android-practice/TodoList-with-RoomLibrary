package com.example.todolistapp

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolistapp.databinding.ActivityAddTodoBinding
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.db.AppDatabase
import com.example.todolistapp.db.TodoDao
import com.example.todolistapp.db.TodoEntity

class AddTodoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = AppDatabase.getInstance(this)!!// db는 싱글톤 방식이므로 프로젝트 내에 단 한개의 인스턴스만 존재하고 공유해서 사용함
        todoDao = db.getTodoDao()


        binding.btnComplete.setOnClickListener {
            insertTodo()
        }

    }

    //할 일을 추가해주는 함수
    private fun insertTodo(){

        val todoTitle = binding.editTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId //라디오 그룹 내에 체크된 것의 아이디


        var impData = 0
        when(todoImportance){
            R.id.btn_high -> {
                impData = 1
            }
            R.id.btn_middle -> {
                impData = 2
            }
            R.id.btn_low -> {
                impData = 3
            }
        }

        //모든 정보가 제대로 입력되지 않앗으면 토스트 팝업
        if(impData == 0 || todoTitle.isBlank() ){
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        }else {

            //db접근 시 백그라운드 스레드
            Thread {
                todoDao.insertTodo(
                    TodoEntity(
                        null,
                        todoTitle,
                        impData
                    )
                )// id값은 autoincrement로 자동 삽입되어서 null로 작성해도됨

                runOnUiThread {
                    Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_LONG).show()
                    finish() //addTodoAcitvity가 종료되고 이전 액티비티로 돌아가게됨
                }

            }.start()

        }



    }


}