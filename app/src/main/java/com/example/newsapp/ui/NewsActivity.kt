package com.example.newsapp.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.db.MessageDatabase
import com.example.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(MessageDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        setupTabLayout()
        setupViewPager()

    }



    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }
    }



    private fun setupTabLayout() {
        binding.tabLayout.apply {
            addTab(this.newTab().setText(resources.getString(R.string.txt_saved)))
            addTab(this.newTab().setText(resources.getString(R.string.txt_general)))

            // tabGravity = TabLayout.GRAVITY_FILL

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        binding.viewPager.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }




}