package com.oguzhanozgokce.healthandprediction.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.adaptor.NewsListAdapter
import com.oguzhanozgokce.healthandprediction.data.api.newsAPI.NewsApi
import com.oguzhanozgokce.healthandprediction.data.repos.NewsListRepo
import com.oguzhanozgokce.healthandprediction.databinding.FragmentNewsBinding
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private var _binding: FragmentNewsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerViewNewsId

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        viewModel = NewsViewModel(NewsListRepo(NewsApi.service))

        lifecycleScope.launch {
            viewModel.getNews()
        }

        viewModel.newsTest.observe(viewLifecycleOwner) { newsTest ->
            newsTest?.let {
                val adapter = newsTest.result.let { result ->
                    NewsListAdapter(result) { result ->
                        Log.d("NewsFragment", "Clicked on result: ${result.name}")
                    }
                }
                recyclerView.adapter = adapter
            } ?: run {
                Log.e("NewsFragment", "News test is null.")
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}