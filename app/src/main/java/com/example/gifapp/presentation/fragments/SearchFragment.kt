package com.example.gifapp.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gifapp.Constants
import com.example.gifapp.MainActivity
import com.example.gifapp.R
import com.example.gifapp.databinding.FragmentSearchBinding
import com.example.gifapp.presentation.adapters.SearchAdapter
import com.example.gifapp.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {//Фрагмент для поиска новых гифок
    private lateinit var bind: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val title = "Search"
    private var isConnected = true

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setQuery(arguments?.getString("q").toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity?)?.setActionBarTitle(title)
        bind = FragmentSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainRecView = bind.searchRecView
        lifecycleScope.launchWhenCreated {
            viewModel.isConnected.collectLatest {
                if (it) {
                    if (!isConnected)
                        Toast.makeText(
                            bind.root.context,
                            Constants.connectionRestored,
                            Toast.LENGTH_SHORT
                        ).show()
                    isConnected = true
                } else {
                    Toast.makeText(
                        bind.root.context,
                        Constants.connectionError,
                        Toast.LENGTH_SHORT
                    ).show()
                    isConnected = false
                }
            }
        }
        mainRecView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            searchAdapter = SearchAdapter()
            adapter = searchAdapter
        }
        lifecycleScope.launchWhenCreated { viewModel.gifs.collectLatest(searchAdapter::submitData) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        (activity as MainActivity?)?.onSupportNavigateUp()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.setQuery(query.toString())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}