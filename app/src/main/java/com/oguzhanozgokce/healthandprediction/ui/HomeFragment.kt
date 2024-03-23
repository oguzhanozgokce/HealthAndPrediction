package com.oguzhanozgokce.healthandprediction.ui

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.api.RetrofitClient
import com.oguzhanozgokce.healthandprediction.databinding.FragmentHomeBinding
import com.oguzhanozgokce.healthandprediction.repository.NewsListRepo
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeNewsListViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!
    private var totalLoadedItems = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerviewId // RecyclerView'ı bağlayın

        val layoutManager = GridLayoutManager(requireContext(), 2) // İki sütunlu bir grid
        recyclerView.layoutManager = layoutManager

        viewModel = HomeNewsListViewModel(NewsListRepo(RetrofitClient.service))

        lifecycleScope.launch {
            viewModel.getHealthNews()
        }

        viewModel.newsResponse.observe(viewLifecycleOwner, Observer { newResponse ->
            newResponse?.let {
                val adapter = newResponse.articles?.let { articles ->
                    NewsListAdapter(articles) { article ->
                        // Tıklanıldığında yapılacak işlemler
                        val action = HomeFragmentDirections.actionHomeFragmentToNewsDetailFragment(article)
                        findNavController().navigate(action)
                        Log.d("HomeFragment", "Clicked on article: ${article.title}")
                    }
                }
                recyclerView.adapter = adapter
            } ?: run {
                // Eğer newResponse null ise, boş bir adapter atayın veya hata işleyin
                Log.e("HomeFragment", "News response is null.")
                // recyclerView.adapter = NewsListAdapter(emptyList()) // Varsayılan olarak boş liste kullanabilirsiniz
            }
        })


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (viewModel.isLoading.value!! && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    lifecycleScope.launch {
                        viewModel.loadMoreHealthNews()
                    }
                }
                // Kullanıcı en üste tekrar kaydırırsa, yeni bir yükleme işlemi başlatmayın
                if (firstVisibleItemPosition == 0 && dy < 0) {
                    Log.d("HomeFragment", "Reached to the top, no more loading")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
