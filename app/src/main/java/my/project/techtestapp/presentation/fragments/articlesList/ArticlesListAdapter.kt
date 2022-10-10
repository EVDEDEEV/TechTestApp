package my.project.techtestapp.presentation.fragments.articlesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.techtestapp.R
import my.project.techtestapp.databinding.ArticleItemBinding
import my.project.techtestapp.presentation.models.ArticlesListUiModel
import my.project.techtestapp.utils.Constants.BASE_URL
import my.project.techtestapp.utils.OnArticleClicked
import my.project.techtestapp.utils.formatDate

class ArticlesListAdapter(
    private val onClick: OnArticleClicked,
) : ListAdapter<ArticlesListUiModel, ArticlesListAdapter.ArticlesViewHolder>(DIFF_CALLBACK) {

    inner class ArticlesViewHolder(
        private val binding: ArticleItemBinding,
        private val onClick: OnArticleClicked,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesListUiModel) {
            binding.apply {
                articleTitle.text = article.title
                articleText.text = article.text
                articleDate.text = article.formatDate(article.date)

                Glide.with(itemView.context)
                    .load(BASE_URL + article.image)
                    .error(R.drawable.ic_baseline_close_24)
                    .into(articleItemImage)

                itemView.setOnClickListener {
                    onClick.invoke(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleItemBinding.inflate(layoutInflater, parent, false)
        return ArticlesViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesListUiModel>() {
            override fun areItemsTheSame(
                oldItem: ArticlesListUiModel,
                newItem: ArticlesListUiModel,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticlesListUiModel,
                newItem: ArticlesListUiModel,
            ): Boolean = oldItem == newItem
        }
    }
}