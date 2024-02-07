package com.example.todolistapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.db.AppDatabase
import com.example.todolistapp.db.TodoDao
import com.example.todolistapp.db.TodoEntity

class MainActivity : AppCompatActivity(), OnItemLongClickListener { //리스너를 메인 액티비티에서 구현 (onLongClick 함수 구현->구현은 main에서하고 adapter에 넘겨서 사용)
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
        }.start()
    }

    //뷰 리소스에 접근하므로 메인 스레드에서 실행되어야함
    private fun setRecyclerView(){
        runOnUiThread{//메인 스레드에서 실행되도록 함
            adapter = TodoRecyclerViewAdapter(todoList, this)//this : MainActivity객체(listener를 넘겨주기위해)
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(this)
        }
    }


    //다른 액티비티로 갔다가 해당 액티비티로 돌아왔을떄 실행됨
    override fun onRestart() {
        super.onRestart()
        getAllTodoList() // 메인 액티비티 -> 할일 추가 액티비티 -> 메인 액티비티 (getAllTodoList를 호출해서 리스트 갱신)
    }


    override fun onLongClick(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_no), null) //아무 작동도 하지 않을때 null
        builder.setPositiveButton(getString(R.string.alert_yes),
            object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(position)
                }
            })

        builder.show()
    }

    private fun deleteTodo(position: Int){
        Thread{ //db에 접근해서 없애야함
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)

            runOnUiThread{
                adapter.notifyDataSetChanged() //adapter에 데이터셋 변경을 알림(자동 재조회)
                Toast.makeText(this,"삭제되었습니다", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

}