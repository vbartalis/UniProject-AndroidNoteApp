package com.example.note.ui.noteEdit

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.note.R
import com.example.note.database.NoteDatabase
import com.example.note.database.entity.Note
import com.example.note.databinding.NoteEditFragmentBinding
import com.example.note.databinding.WelcomeFragmentBinding
import com.example.note.ui.welcome.WelcomeViewModel


class NoteEditFragment : Fragment() {

    //private var note: Note? = null

    private lateinit var binding: NoteEditFragmentBinding


    private lateinit var viewModel: NoteEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<NoteEditFragmentBinding>(inflater, R.layout.note_edit_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Create the viewmodel
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NoteEditViewModelFactory(dataSource,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteEditViewModel::class.java)


//        val arguments = arguments?.let { NoteEditFragmentArgs.fromBundle(it) }
//
//        note = arguments?.note
//        binding.editTextTitle.setText(note?.title)
//        binding.editTextNote.setText(note?.body)


        arguments?.let {
            viewModel.note = NoteEditFragmentArgs.fromBundle(it).note
            binding.editTextTitle.setText(viewModel.note?.title)
            binding.editTextNote.setText(viewModel.note?.body)
        }

        binding.viewModel = viewModel


        binding.buttonSave.setOnClickListener { view: View ->
            val title = binding.editTextTitle.text.toString().trim()
            val body = binding.editTextNote.text.toString().trim()

            if (title.isEmpty()) {
                binding.editTextTitle.error = "title required"
                binding.editTextTitle.requestFocus()
                return@setOnClickListener
            }
            else if (body.isEmpty()) {
                binding.editTextNote.error = "note required"
                binding.editTextNote.requestFocus()
                return@setOnClickListener
            }
            else {
                viewModel.saveNote(title, body)
                view.findNavController().navigate(R.id.action_noteEdit_to_noteList)
            }
        }

        binding.buttonDelete.setOnClickListener { view: View ->
            if (viewModel.note != null) {
                delete(view)

            }
            else {
                Toast.makeText(context, "Cannot Delete", Toast.LENGTH_SHORT).show()
            }


        }
    }

    fun delete(view: View) {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes"){_, _ ->
                viewModel.deleteNote()
                view.findNavController().navigate(R.id.action_noteEdit_to_noteList)
            }
            setNegativeButton("No"){_, _ ->

            }
        }.create().show()
    }

}
