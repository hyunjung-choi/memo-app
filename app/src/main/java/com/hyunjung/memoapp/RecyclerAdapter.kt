package com.hyunjung.memoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunjung.memoapp.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var listData = mutableListOf<Memo>()
    var helper: SqliteHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }


    inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        var mMemo: Memo? = null

        init {
            binding.btnDelete.setOnClickListener {
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        fun setMemo(memo: Memo) {
            binding.tvNo.text = "${memo.no}"
            binding.tvContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.tvDatetime.text = sdf.format(memo.datetime)

            this.mMemo = memo
        }
    }
}
