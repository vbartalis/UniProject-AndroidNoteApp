package com.example.note.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.note.R
import com.example.note.database.NoteDatabase
import com.example.note.databinding.RegisterFragmentBinding
import com.example.note.databinding.WelcomeFragmentBinding
import com.example.note.model.CurrentUser


class RegisterFragment : Fragment() {


    private lateinit var binding: RegisterFragmentBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<RegisterFragmentBinding>(inflater, R.layout.register_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Create the viewmodel
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).userDao
        val viewModelFactory = RegisterViewModelFactory(dataSource,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.viewModel = viewModel

        binding.buttonRegister.setOnClickListener { view : View -> register(view) }

    }

    private fun register(view: View) {
        if (binding.editTextRegisterName.text.isNotEmpty() && binding.editTextRegisterPwd1.text.isNotEmpty() && binding.editTextRegisterPwd2.text.isNotEmpty()) {
            if (binding.editTextRegisterPwd1.text.toString() == binding.editTextRegisterPwd2.text.toString()) {
                var currentUser : CurrentUser? = viewModel.register(binding.editTextRegisterName.text.toString(), binding.editTextRegisterPwd1.text.toString())

                if (currentUser != null) {
                    Log.i("Register fragment", "CurrentUser is not null")
                    view.findNavController().navigate(RegisterFragmentDirections.actionRegisterToHome())
                    //TODO safe args username, id
                }
                else {
                    Log.i("Register fragment", "CurrentUser is null")
                }
            }
            else {
                Toast.makeText(this.context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this.context, "Please provide username and password", Toast.LENGTH_SHORT).show()
        }
    }

}
