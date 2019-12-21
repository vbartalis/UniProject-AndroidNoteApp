package com.example.note.ui.noteList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.note.R
import com.example.note.database.NoteDatabase
import com.example.note.database.entity.Note
import com.example.note.databinding.NoteListFragmentBinding
import com.example.note.databinding.RegisterFragmentBinding
import com.example.note.ui.register.RegisterViewModel
import com.example.note.ui.register.RegisterViewModelFactory


class NoteListFragment : Fragment() {


    private lateinit var binding: NoteListFragmentBinding
    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<NoteListFragmentBinding>(inflater, R.layout.note_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Create the viewmodel
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NoteListViewModelFactory(dataSource,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteListViewModel::class.java)

        binding.viewModel = viewModel

        //Create the adapter
        val adapter = NoteAdapter(EditListener { note ->
            viewModel.onNoteClicked(note)
        })
        binding.recyclerViewNotes.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        viewModel.navigateToEdit.observe(this, Observer { note ->
            note?.let {
                val action = NoteListFragmentDirections.actionNoteListToNoteEdit()
                action.note = note
                this.findNavController().navigate(action)
                //this.findNavController().navigate(NoteListFragmentDirections.actionNoteListToNoteEdit(note))
                viewModel.toEditNavigated()
            }
        })

        viewModel.currentNotes.observe(viewLifecycleOwner, Observer {
            binding.textView.text = it
        })





        binding.buttonNewNote.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_noteList_to_noteEdit)
        }

        //        binding.recyclerViewNotes.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
    }

}
