package com.example.note.ui.web

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.note.R
import com.example.note.databinding.WebFragmentBinding


class WebFragment : Fragment() {



    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: WebViewModel by lazy {
        ViewModelProviders.of(this).get(WebViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = WebFragmentBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val adapter = WebAdapter()

        binding.onlineComments.adapter = adapter
      //  binding.onlineComments.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}
