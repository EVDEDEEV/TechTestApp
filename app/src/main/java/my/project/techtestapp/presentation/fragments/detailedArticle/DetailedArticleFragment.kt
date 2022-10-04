package my.project.techtestapp.presentation.fragments.detailedArticle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentDetailedArticleBinding
import my.project.techtestapp.utils.Constants.BASE_URL


class DetailedArticleFragment : Fragment(R.layout.fragment_detailed_article) {

    private val binding by viewBinding(FragmentDetailedArticleBinding::bind)
//    private val args: DetailedArticleFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    fun bind() {
//
////        val article = args.article
//
//        binding.apply {
//            detailedArticleTitle.text = article.title
//            detailedArticleDate.text = article.date
//            detailedArticleText.text = article.text
//            Picasso.get().load(BASE_URL + article.image).into(detailedItemImage)
//        }
//    }




}