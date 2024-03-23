
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.model.Article

class NewsListAdapter(
    private val articles: List<Article>,
    private val onItemClickListener: (Article) -> Unit // Burada bir onClickListener tanımlanır
) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_desing, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position], onItemClickListener) // onClickListener burada aktarılır
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.news_imageView_ID)
        private val titleTextView: TextView = itemView.findViewById(R.id.news_title_text_view_Id)
        private val publishedAt: TextView = itemView.findViewById(R.id.news_publishedAt_text_view_Id)

        fun bind(article: Article, onItemClickListener: (Article) -> Unit) {
            if (article.urlToImage.isNullOrEmpty()) {
                imageView.setImageResource(R.drawable.default_news_image)
            } else {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .into(imageView)
            }
            titleTextView.text = article.title
            publishedAt.text = article.publishedAt

            // Tıklanıldığında onClickListener'ı tetikle
            itemView.setOnClickListener { onItemClickListener(article) }
        }
    }
}

