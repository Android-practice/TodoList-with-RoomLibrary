package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.db.AppDatabase
import com.example.todolistapp.db.TodoDao
import com.example.todolistapp.db.TodoEntity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : AppDatabase
    private lateinit var todoDao : TodoDao
    private lateinit var todoList: ArrayList<TodoEntity>
    private lateinit var adapter : TodoRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()


        binding.btnAddTodo.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java) //버튼을 눌렀을 때 AddToDoActivity 로 넘어감
            startActivity(intent)
        }

    }


    private fun getAllTodoList(){
        Thread{ //db접근 관련 작업은 백그라운드 스레드에서 진행
            todoList = ArrayList(todoDao.getAllTodo())
            setRecyclerView() //해당 함수는 메인 스레드에서 실행되어야 하는데... but thread내에서 실행되고 있음
        }
    }

    //뷰 리소스에 접근하므로 메인 스레드에서 실행되어야함
    private fun setRecyclerView(){
        runOnUiThread{//메인 스레드에서 실행되도록 함
            adapter = TodoRecyclerViewAdapter(todoList)
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(this)
        }
    }


    //다른 액티비티로 갔다가 해당 액티비티로 돌아왔을떄 실행됨
    override fun onRestart() {
        super.onRestart()
        getAllTodoList() // 메인 액티비티 -> 할일 추가 액티비티 -> 메인 액티비티 (getAllTodoList를 호출해서 리스트 갱신)
    }




}