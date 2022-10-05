package my.project.techtestapp.presentation.fragments.articlesList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentDevExamBinding
import my.project.techtestapp.utils.collectFlow
import my.project.techtestapp.utils.safeNavigate

@AndroidEntryPoint
class ArticlesList : Fragment(R.layout.fragment_dev_exam) {

    private val binding by viewBinding(FragmentDevExamBinding::bind)
    private val articlesAdapter by lazy { ArticlesListAdapter() }
    private val articlesListViewModel: ArticlesListViewModel by viewModels()

//    private val onClick: OnCharacterClicked = { article ->
//        val action = DevExamDirections.actionDevExamToDetailedArticleFragment()
//        view?.findNavController()?.navigate(action)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setDataToRecyclerView()
        initFilterButton()
        initRefreshButton()

    }

    private fun initRecyclerView() {
        binding.articlesRecyclerView.apply {
            adapter = articlesAdapter
        }
    }

    private fun initRefreshButton() {
        binding.refreshButton.setOnClickListener {
            clearTab()
            refresh()
        }
    }

    private fun refresh() {
        articlesListViewModel.refresh()
    }

    private fun clearTab() {
        articlesListViewModel.deleteFromTab()
    }

    private fun setDataToRecyclerView() {
        collectFlow(articlesListViewModel.listArticles) {
            articlesAdapter.submitList(it)
            articlesAdapter.notifyDataSetChanged()
        }
    }

    private fun initFilterButton() {
        binding.filterButton.setOnClickListener {
            val action = ArticlesListDirections.actionDevExamToFilterBottomSheetFragment()
            view?.findNavController()?.safeNavigate(action)
        }
    }
}


