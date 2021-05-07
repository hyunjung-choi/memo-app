package com.hyunjung.memoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hyunjung.RoomMemoapp.RecyclerAdapter
import com.hyunjung.memoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var helper: RoomHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo")
                .addMigrations(MigrateDatabase.MIGRATE_1_2)
                .allowMainThreadQueries().build()

        val adapter = RecyclerAdapter()
        adapter.helper = helper

        adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?: listOf())

        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(binding.recyclerMemo.context, LinearLayoutManager(this).orientation)
        binding.recyclerMemo.addItemDecoration(dividerItemDecoration)

        // 저장 버튼
        binding.buttonSave.setOnClickListener {
            if(binding.editMemo.text.toString().isNotEmpty()) {
                val no = adapter.itemCount + 1
                val memo = RoomMemo(no.toLong(), binding.editMemo.text.toString(), System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                adapter.listData.clear()
                adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?: listOf())
                adapter.notifyDataSetChanged()
                binding.editMemo.setText("")

                val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(binding.editMemo.windowToken, 0)
            }
        }

    }
}

object MigrateDatabase {
    val MIGRATE_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val alter = "ALTER table room_memo add column new_title text"
            database.execSQL(alter)
        }
    }
}