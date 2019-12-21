package com.example.note.ui.noteList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.database.entity.Note
import com.example.note.databinding.NoteItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class NoteAdapter(val editListener: EditListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(NoteDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Note>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.NoteItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val noteItem = getItem(position) as DataItem.NoteItem
                holder.bind(noteItem.note, editListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.NoteItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }


    class ViewHolder private constructor(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Note, editListener: EditListener) {
            binding.note = item
            binding.editListener = editListener
            binding.textViewItemTitle.setText(item.title)
            binding.textViewItemBody.setText(item.body)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


class EditListener(val clickListener: (note: Note) -> Unit) {
    fun onClick(note: Note) = clickListener(note)
}


sealed class DataItem {
    data class NoteItem(val note:  Note): DataItem() {
        override val id = note.id
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}





//package com.example.note.ui.noteList
//
//import android.content.Context
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.note.database.entity.Note
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//
//class NoteAdapter (val clickListener: EditListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(NoteListDiffCallback()){
//
//    private val adapterScope = CoroutineScope(Dispatchers.Default)
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//
//
//
//
//
//
//
//    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)
//}
//
//class EditListener(val noteClickListener: (note : Note) -> Unit) {
//    fun edit(note: Note) = noteClickListener(note)
//}
//
//
//
//class NoteListDiffCallback : DiffUtil.ItemCallback<Note>() {
//    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
//        return oldItem == newItem
//    }
//
//}
//
//sealed class DataItem {
//
//    abstract val id: Long
//
//    data class NoteItem(val note : Note) : DataItem() {
//        override val id = note.id
//    }
//
////    object Header : DataItem() {
////        override val id = Long.MIN_VALUE
////
////    }
//
//}