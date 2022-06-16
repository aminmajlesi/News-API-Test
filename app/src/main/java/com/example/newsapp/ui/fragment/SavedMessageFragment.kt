package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import androidx.lifecycle.Observer
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentSavedMessageBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_general_message.*
import kotlinx.android.synthetic.main.fragment_saved_message.*

/**
 * @author by Amin Majlesi
 */
class SavedMessageFragment : Fragment() {


    private lateinit var binding: FragmentSavedMessageBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "SavedMessageFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedMessageBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()




        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { message ->
            newsAdapter.differ.submitList(message)
        })

    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(onSaveClick = {
            //viewModel.saveMessage(it)
        })
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}