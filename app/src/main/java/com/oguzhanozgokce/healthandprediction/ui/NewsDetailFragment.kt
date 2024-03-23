package com.oguzhanozgokce.healthandprediction.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.databinding.FragmentNewsDetailBinding
import com.oguzhanozgokce.healthandprediction.model.Article

class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        // ViewModel'i başlat
        viewModel = ViewModelProvider(this).get(NewsDetailViewModel::class.java)

        // Haber nesnesini argumentlerden al
        val article = arguments?.getSerializable("article") as? Article

        article?.let {
            // ViewModel'e seçilen haber nesnesini gönder
            viewModel.setSelectedArticle(it)
        }

        // ViewModel'in içindeki haber nesnesini gözlemle
        viewModel.selectedArticle.observe(viewLifecycleOwner, Observer { article ->
            // Haber detaylarını burada göster
            showArticleDetails(article)
        })

        return view
    }


    private fun showArticleDetails(article: Article) {
        // Haber detaylarını gösterme kodu
        binding.apply {
            titleTextView.text = article.title
            descriptionTextViewId.text = article.description
            authorTextViewId.text = article.author
            publishedAtTextViewId.text = article.publishedAt
            sourceTextViewId.text = article.url
            // Görsel için Glide kütüphanesini kullanarak URL'den resmi yükle
            if (article.urlToImage.isNullOrEmpty()) {
                imageView.setImageResource(R.drawable.default_news_image)
            } else {
                Glide.with(requireContext())
                    .load(article.urlToImage)
                    .into(imageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
