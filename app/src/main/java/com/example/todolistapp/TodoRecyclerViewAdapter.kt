package com.example.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ItemTodoBinding
import com.example.todolistapp.db.TodoEntity

class TodoRecyclerViewAdapter(private val todoList: ArrayList<TodoEntity>) : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        val root = binding.root
    }

    //RecyclerView Adapter 구현해야하는 3개의 멤버

    //뷰 홀더를 생성함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    //아이템리스트의 사이즈를 반환
    override fun getItemCount(): Int {
        return todoList.size
    }

    //뷰에 값을 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        when (todoData.importance){
            1->{
                holder.tv_importance.setBackgroundResource(R.color.pastel_red)
            }
            2->{
                holder.tv_importance.setBackgroundResource(R.color.pastel_yellow)
            }
            3->{
                holder.tv_importance.setBackgroundResource(R.color.pastel_green)
            }
        }


        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title


    }


}