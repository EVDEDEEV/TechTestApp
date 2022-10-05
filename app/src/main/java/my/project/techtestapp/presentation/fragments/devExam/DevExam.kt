package my.project.techtestapp.presentation.fragments.devExam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.project.techtestapp.R
import my.project.techtestapp.data.models.remote.articles.ArticlesResponseItem
import my.project.techtestapp.databinding.FragmentDevExamBinding
import my.project.techtestapp.presentation.MainViewModel
import my.project.techtestapp.utils.collectFlow
import my.project.techtestapp.utils.safeNavigate

@AndroidEntryPoint
class DevExam : Fragment(R.layout.fragment_dev_exam) {

    private val binding by viewBinding(FragmentDevExamBinding::bind)
    private val articlesAdapter by lazy { DevExamAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

//    private val onClick: OnCharacterClicked = { article ->
//        val action = DevExamDirections.actionDevExamToDetailedArticleFragment()
//        view?.findNavController()?.navigate(action)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setDataToRecyclerView()
//        getArticlesFromApi()
//        getArticlesFromDb()
        initFilterButton()
        initRefreshButton()
    }

    private fun initRecyclerView() {
        binding.articlesRecyclerView.apply {
            adapter = articlesAdapter
//            getArticlesFromApi()
//            getArticlesFromDb()
        }
    }

    private fun initRefreshButton() {
        binding.refreshButton.setOnClickListener {
            clearTab()
            refresh()
//            getArticlesFromApi()
//            getArticlesFromDb()
        }
    }

    private fun refresh() {
        mainViewModel.refresh()
    }

    private fun clearTab() {
        mainViewModel.deleteFromTab()
    }

    private fun setDataToRecyclerView() {
        collectFlow(mainViewModel.listArticles) {
            articlesAdapter.submitList(it)
        }

    }

    private fun initFilterButton() {
        binding.filterButton.setOnClickListener {
            val action = DevExamDirections.actionDevExamToFilterBottomSheetFragment()
            view?.findNavController()?.safeNavigate( action)
        }
    }
}


