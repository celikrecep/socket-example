package com.loyer.socket_example.ui.activity.fragments.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyer.socket_example.R
import com.loyer.socket_example.util.inflate
import com.loyer.socket_example.vo.Mock
import kotlinx.android.synthetic.main.list_item_mock.view.*

class MockRvAdapter(
    private val list: MutableList<Mock>
) : RecyclerView.Adapter<MockRvAdapter.MockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {
        return MockViewHolder(parent.inflate(R.layout.list_item_mock))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MockViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<Mock>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun updateItem(id: Int) {
        val mock = list.find { it.id == id }
        val index = list.indexOf(mock)
        if (index != -1) {
            notifyItemChanged(index)
        }
    }

    inner class MockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modal: Mock) {
            itemView.mockId.text = modal.revisedId
            itemView.mockValue.text = modal.name
        }
    }
}