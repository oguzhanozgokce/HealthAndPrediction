
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.constants.ImageLoader
import com.oguzhanozgokce.healthandprediction.model.modelNews.Article

class NewsListAdapter(
    private val articles: List<Article>,
    private val onItemClickListener: (Article) -> Unit // Burada bir onClickListener tanımlanır
) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false)
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
        private val authorTextView: TextView = itemView.findViewById(R.id.news_author_text_view_Id)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.news_description_text_view_Id)
        fun bind(article: Article, onItemClickListener: (Article) -> Unit) {
            ImageLoader.loadImage(article.urlToImage, imageView, R.drawable.newspaper_2540832)

            titleTextView.text = article.title
            publishedAt.text = article.publishedAt
            authorTextView.text = article.author
            descriptionTextView.text = article.description

            // Tıklanıldığında onClickListener'ı tetikle
            itemView.setOnClickListener { onItemClickListener(article) }
        }
    }
}

