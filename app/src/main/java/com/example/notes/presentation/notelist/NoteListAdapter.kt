package com.example.notes.presentation.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.notes.common.base.BaseAdapter
import com.example.notes.common.base.BaseHolder
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.databinding.ItemNoteBinding

class NoteListAdapter : BaseAdapter<NoteEntity, NoteListAdapter.NoteListViewHolder>() {

    override fun bindViewHolder(holder: NoteListViewHolder, data: NoteEntity?) {
        data?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val itemBinding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListViewHolder(itemBinding)
    }

    inner class NoteListViewHolder(private val itemNoteBinding: ItemNoteBinding) :
        BaseHolder<NoteEntity>(listener, itemNoteBinding.root) {

        fun bind(data: NoteEntity) {
            with(itemNoteBinding) {
                tvTitle.text = data.title
                tvContent.text = data.content
            }
        }

    }
}