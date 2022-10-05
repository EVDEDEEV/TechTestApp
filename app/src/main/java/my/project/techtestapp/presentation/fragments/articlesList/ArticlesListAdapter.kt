package my.project.techtestapp.presentation.fragments.articlesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import my.project.techtestapp.data.models.database.articles.ArticlesEntity
import my.project.techtestapp.databinding.ArticleItemBinding
import my.project.techtestapp.utils.Constants.BASE_URL
import my.project.techtestapp.utils.Constants.DATE_FORMAT_PATTERN
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext

class ArticlesListAdapter() :
    ListAdapter<ArticlesEntity, ArticlesListAdapter.ArticlesViewHolder>(AsyncDifferConfig.Builder(DIFF_CALLBACK).build()) {

    inner class ArticlesViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesEntity) {
            binding.apply {
                articleTitle.text = article.title
                articleText.text = article.text
                articleDate.text = formatDate(article.date)
                Glide.with(itemView.context).load(BASE_URL+ article.image).into(articleItemImage)
//                Picasso.get().load(BASE_URL + article.image).centerCrop().fit().into(articleItemImage)
//                articleItem.setOnClickListener {
//                    onItemClick.invoke(article)
//                }
            }
        }

        private fun formatDate(date: String): String {
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
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesEntity>() {
            override fun areItemsTheSame(
                oldItem: ArticlesEntity,
                newItem: ArticlesEntity,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticlesEntity,
                newItem: ArticlesEntity,
            ): Boolean = oldItem == newItem
        }
    }
}