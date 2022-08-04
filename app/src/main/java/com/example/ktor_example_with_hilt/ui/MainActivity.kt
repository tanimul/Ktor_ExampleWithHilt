package com.example.ktor_example_with_hilt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktor_example_with_hilt.data.models.User
import com.example.ktor_example_with_hilt.data.network.ApiSate
import com.example.ktor_example_with_hilt.databinding.ActivityMainBinding
import com.example.ktor_example_with_hilt.ui.adapter.UserViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewAdapter: UserViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Recyclerview init
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        userViewAdapter = UserViewAdapter(this) {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
        }
        val dividerItemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.adapter = userViewAdapter


        //init Viewmodel
        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getRepoData("md")
        lifecycleScope.launchWhenStarted {
            viewModel.apiStateFlow.collect {
                binding.apply {
                    when (it) {
                        is ApiSate.Success<*> -> {
                            userViewAdapter.setDataList(it.data as List<User>)
                            binding.recyclerView.isVisible = true
                            binding.loading.isVisible = false
                            binding.tvError.isVisible = false
                        }
                        is ApiSate.Failed -> {
                            tvError.text = it.toString()
                            binding.recyclerView.isVisible = false
                            binding.loading.isVisible = false
                            binding.tvError.isVisible = true

                            Log.e(TAG, "onCreate: FAILED: $it")
                        }
                        is ApiSate.Loading -> {
                            binding.recyclerView.isVisible = false
                            binding.loading.isVisible = true
                            binding.tvError.isVisible = false

                            Log.e(TAG, "onCreate: LOADING $it")
                        }
                        is ApiSate.Empty -> {
                            Log.e(TAG, "onCreate: EMPTY")

                        }
                    }
                }
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String?): Boolean {

                if (!search.isNullOrEmpty())
                    viewModel.getRepoData(search)
                Log.e(TAG, "onQueryTextSubmit: $search")

                return false
            }

            override fun onQueryTextChange(search: String?): Boolean {
//                if (!search.isNullOrEmpty())
//                    viewModel.getRepoData(search)

                Log.e(TAG, "onQueryTextChange: $search")

                return false

            }
        })


    }
}