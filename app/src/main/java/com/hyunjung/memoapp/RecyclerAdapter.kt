package com.hyunjung.RoomMemoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunjung.memoapp.RoomHelper
import com.hyunjung.memoapp.RoomMemo
import com.hyunjung.memoapp.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var listData = mutableListOf<RoomMemo>()
    var helper: RoomHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val RoomMemo = listData.get(position)
        holder.setRoomMemo(RoomMemo)
    }


    inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        var mRoomMemo: RoomMemo? = null

        init {
            binding.btnDelete.setOnClickListener {
                helper?.roomMemoDao()?.delete(mRoomMemo!!)
                listData.remove(mRoomMemo)
                helper?.roomMemoDao()?.deleteAll()

                var i = 1
                for(m in listData){
                    m.no = i.toLong()
                    i = i + 1
                    helper?.roomMemoDao()?.insert(m)
                }

                notifyDataSetChanged()
            }
        }

        fun setRoomMemo(RoomMemo: RoomMemo) {
            binding.tvNo.text = "${RoomMemo.no}"
            binding.tvContent.text = RoomMemo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.tvDatetime.text = sdf.format(RoomMemo.datetime)

            this.mRoomMemo = RoomMemo
        }
    }
}

