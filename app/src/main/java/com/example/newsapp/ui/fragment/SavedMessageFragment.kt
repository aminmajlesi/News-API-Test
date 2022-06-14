package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSavedMessageBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel

class SavedMessageFragment : Fragment() {


    private lateinit var binding: FragmentSavedMessageBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupData()
    }

    private fun setupData() {
        binding.label.text = getString(R.string.third_fragment)
    }

}