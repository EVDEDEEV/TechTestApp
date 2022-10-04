package my.project.techtestapp.presentation.fragments.devExam

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import my.project.techtestapp.data.models.remote.articles.ArticlesResponseItem
import my.project.techtestapp.databinding.ArticleItemBinding
import my.project.techtestapp.utils.Constants.BASE_URL
import my.project.techtestapp.utils.Constants.DATE_FORMAT_PATTERN
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DevExamAdapter(private var onItemClick: ((ArticlesResponseItem) -> Unit)) :
    RecyclerView.Adapter<DevExamAdapter.ArticlesViewHolder>() {

    private var listArticles = emptyList<ArticlesResponseItem>()

    inner class ArticlesViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(article: ArticlesResponseItem) {
            binding.apply {
                articleTitle.text = article.title
                articleText.text = article.text
                articleDate.text = article.date?.let { formatDate(it) }
                Picasso.get().load(BASE_URL + article.image).into(articleItemImage)
                articleItem.setOnClickListener {
                    onItemClick.invoke(article)
                }
            }
        }

        fun formatDate(date: String): String {
            val dateTime: ZonedDateTime = OffsetDateTime.parse(date).toZonedDateTime()
            val defaultZoneTime: ZonedDateTime =
                dateTime.withZoneSameInstant(ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)
            return defaultZoneTime.format(formatter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleItemBinding.inflate(layoutInflater, parent, false)
        return ArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(listArticles[position])
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    fun setArticles(articles: List<ArticlesResponseItem>) {
        listArticles = articles
        notifyDataSetChanged()
    }
}