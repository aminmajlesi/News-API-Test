package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentGeneralMessageBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import androidx.lifecycle.Observer
import com.example.newsapp.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_general_message.*
import kotlinx.android.synthetic.main.item_message_preview.*

/**
 * @author by Amin Majlesi
 */
class GeneralMessageFragment : Fragment () {


    private lateinit var binding: FragmentGeneralMessageBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "GeneralMessageFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralMessageBinding.inflate(layoutInflater ,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        ////////
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("message", it)
            }

            Snackbar.make(view, "News saved successfully : $it", Snackbar.LENGTH_LONG).show()

        }
//
//        ivSaved.setOnClickListener {
//            viewModel.saveMessage(message)
//            Snackbar.make(view, "News saved successfully", Snackbar.LENGTH_SHORT).show()
//        }

//        newsAdapter.setOnItemClickListener{
//            Snackbar.make(view, "News saved successfully : $it", Snackbar.LENGTH_SHORT).show()
//        }


        ////////


        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.messages)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}