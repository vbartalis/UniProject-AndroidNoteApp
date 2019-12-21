package com.example.note.ui.welcome

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.note.R
import com.example.note.databinding.WelcomeFragmentBinding


class WelcomeFragment : Fragment() {


    private lateinit var binding: WelcomeFragmentBinding

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<WelcomeFragmentBinding>(inflater, R.layout.welcome_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)



        binding.buttonLoginNav.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_welcome_to_login)
        }

        binding.buttonRegisterNav.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_welcome_to_register)
        }
    }

}
