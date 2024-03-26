package com.oguzhanozgokce.healthandprediction.ui.home

import NewsListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.api.newsAPI.RetrofitClient
import com.oguzhanozgokce.healthandprediction.databinding.FragmentHomeBinding
import com.oguzhanozgokce.healthandprediction.repository.NewsListRepo
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeNewsListViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerviewId

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        viewModel = HomeNewsListViewModel(NewsListRepo(RetrofitClient.service))

        lifecycleScope.launch {
            viewModel.getHealthNews()
        }

        viewModel.newsResponse.observe(viewLifecycleOwner, Observer { newResponse ->
            newResponse?.let {
                val adapter = newResponse.articles?.let { articles ->
                    NewsListAdapter(articles) { article ->
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToNewsDetailFragment(article)
                        findNavController().navigate(action)
                        Log.d("HomeFragment", "Clicked on article: ${article.title}")
                    }
                }
                recyclerView.adapter = adapter
            } ?: run {
                Log.e("HomeFragment", "News response is null.")
            }
        })

        val pharmacyImageView = binding.imageView4
        pharmacyImageView.setOnClickListener {
            navigateToMapFragment()
        }
    }

    private fun navigateToMapFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToMapsFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
