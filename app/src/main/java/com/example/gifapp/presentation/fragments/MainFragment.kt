package com.example.gifapp.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gifapp.Constants
import com.example.gifapp.MainActivity
import com.example.gifapp.R
import com.example.gifapp.databinding.FragmentMainBinding
import com.example.gifapp.presentation.adapters.MainAdapter
import com.example.gifapp.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment(), SearchView.OnQueryTextListener {//Страница Trending
    private lateinit var bind: FragmentMainBinding
    private lateinit var mainAdapter: MainAdapter
    private val title = "Trending"
    private var isConnected = false

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity?)?.setActionBarTitle(title)
        bind = FragmentMainBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainRecView = bind.mainRecView
        mainRecView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            mainAdapter = MainAdapter()
            adapter = mainAdapter
        }
        lifecycleScope.launchWhenCreated {
            viewModel.isConnected.collectLatest {
                if (it) {
                    if (!isConnected)
                        viewModel.trending()
                    isConnected = true
                } else {
                    isConnected = false
                    Toast.makeText(
                        bind.root.context,
                        Constants.connectionError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.dataResp.observe(requireActivity()) {
            if (it != null) {
                if (isConnected) {
                    viewModel.deleteAll()
                    viewModel.insertAll(it)
                }
            }
        }
        viewModel.getAllData().observe(requireActivity()) {
            mainAdapter.setListData(it)
            mainAdapter.notifyDataSetChanged()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.trending()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        bind.root.findNavController().navigate(R.id.searchFragment, bundleOf("q" to query))
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}