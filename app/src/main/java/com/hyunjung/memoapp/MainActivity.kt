package com.hyunjung.memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunjung.memoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val helper = SqliteHelper(this, "memo", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = RecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper.selectMemo())

        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(binding.recyclerMemo.context, LinearLayoutManager(this).orientation)
        binding.recyclerMemo.addItemDecoration(dividerItemDecoration)

        // 저장 버튼
        binding.buttonSave.setOnClickListener {
            if(binding.editMemo.text.toString().isNotEmpty()) {
                val memo = Memo(null, binding.editMemo.text.toString(), System.currentTimeMillis())
                helper.insertMemo(memo)
                adapter.listData.clear()
                adapter.listData.addAll(helper.selectMemo())
                adapter.notifyDataSetChanged()
                binding.editMemo.setText("")
            }
        }

    }
}