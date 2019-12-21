package com.example.note.ui.login

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
import com.example.note.databinding.LoginFragmentBinding
import com.example.note.databinding.RegisterFragmentBinding
import com.example.note.model.CurrentUser
import com.example.note.ui.register.RegisterViewModel
import com.example.note.ui.register.RegisterViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LoginFragmentBinding>(inflater, R.layout.login_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application

        val dataSource = NoteDatabase.getInstance(application).userDao
        val viewModelFactory = LoginViewModelFactory(dataSource,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.viewModel = viewModel

        binding.buttonLogin.setOnClickListener { view : View -> login(view) }

    }

    private fun login(view: View) {
        var currentUser : CurrentUser? = viewModel.login(binding.editTextLoginName.text.toString(), binding.editTextLoginPwd.text.toString())

        if (currentUser != null) {
            view.findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
            Toast.makeText(this.context, "Succesfully logged in!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "Wrong credentials!", Toast.LENGTH_SHORT).show()
        }
    }


}
