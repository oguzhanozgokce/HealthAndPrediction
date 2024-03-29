package com.oguzhanozgokce.healthandprediction.ui.news

import NewsListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.api.newsAPI.RetrofitClient
import com.oguzhanozgokce.healthandprediction.databinding.FragmentNewsBinding
import com.oguzhanozgokce.healthandprediction.repository.NewsListRepo
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private var _binding: FragmentNewsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerViewNewsId

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        viewModel = NewsViewModel(NewsListRepo(RetrofitClient.service))

        lifecycleScope.launch {
            viewModel.getHealthNews()
        }

        viewModel.newsResponse.observe(viewLifecycleOwner, Observer { newResponse ->
            newResponse?.let {
                val adapter = newResponse.articles?.let { articles ->
                    NewsListAdapter(articles) { article ->
                        Log.d("NewsFragment", "Clicked on article: ${article.title}")
                    }
                }
                recyclerView.adapter = adapter
            } ?: run {
                Log.e("NewsFragment", "News response is null.")
            }
        })

    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}