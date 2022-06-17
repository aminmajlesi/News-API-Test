package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentGeneralMessageBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_general_message.*
import kotlinx.android.synthetic.main.item_message_preview.*
import kotlinx.android.synthetic.main.item_message_preview.view.*
import kotlinx.coroutines.launch

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
        initObservers()
        ////////
//        newsAdapter.setOnItemClickListener {
            //Snackbar.make(view, "News saved successfully : $it", Snackbar.LENGTH_LONG).show()
//        }

        newsAdapter.setOnItemLongClickListener {

            btnRemove.visibility = View.VISIBLE
            btnCancel.visibility = View.VISIBLE
            cbDelete.visibility = View.VISIBLE

            true
        }

        btnCancel.setOnClickListener {
            cbDelete.visibility = View.GONE
            btnRemove.visibility = View.GONE
            btnCancel.visibility = View.GONE
        }

        btnRemove.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            val article = newsAdapter.differ.currentList[position]
//            viewModel.deleteMessage(article)
//            Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
//                setAction("Undo") {
//                    viewModel.saveMessage(article)
//                }
//                show()
//            }
        }

        ivSaved.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            val article = newsAdapter.differ.currentList[position]
//            viewModel.updateBookMarked( true, article)

        }


    }

    private fun initObservers() {

        viewModel.breakingNewsList.observe(viewLifecycleOwner) { messagesList ->
            newsAdapter.differ.submitList(messagesList)
            viewModel.saveMessagesList(messagesList)
        }

        viewModel.readMessage.observeOnce(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                Log.d("dataState", "readDatabase called  ")
                newsAdapter.differ.submitList(database)
                hideShimmerEffect()
            }
            getBreakingNews()
        }

    }


    private fun getBreakingNews(){
        viewModel.getBreakingNews()
    }

    private fun requestApiData() {
        Log.d("dataState", "requestApiData called  ")
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.messages)
                    }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }

        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.readMessage.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    newsAdapter.differ.submitList(database)
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.rvBreakingNews.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.rvBreakingNews.hideShimmer()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(onSaveClick = {
            viewModel.saveMessage(it)
        })
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
        observe(lifecycleOwner,object :Observer<T> {
            override fun onChanged(t: T) {
                removeObserver(this)
                observer.onChanged(t)
            }
        })
    }

}