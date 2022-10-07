package my.project.techtestapp.presentation.fragments.articlesList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentArticlesListBinding
import my.project.techtestapp.utils.OnArticleClicked
import my.project.techtestapp.utils.collectFlow
import my.project.techtestapp.utils.makeToast
import my.project.techtestapp.utils.safeNavigate

@AndroidEntryPoint
class ArticlesList : Fragment(R.layout.fragment_articles_list) {

    private val binding by viewBinding(FragmentArticlesListBinding::bind)
    private val articlesAdapter by lazy { ArticlesListAdapter(onClick) }
    private val articlesListViewModel: ArticlesListViewModel by activityViewModels()

    private val onClick: OnArticleClicked = {
        val action = ArticlesListDirections.actionDevExamToDetailedArticleFragment(it)
        view?.findNavController()?.navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setDataToRecyclerView()
        initFilterButton()
        refreshArticlesInBackground()
        initRefreshButton()
        setMenu()
    }


    private fun setMenu() {
        val toolbar = binding.articlesListToolbar
        toolbar.apply {
            inflateMenu(R.menu.articles_list_action_bar)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.refresh_button_menu_icon -> initRefreshButton()
                }
                true
            }
        }
    }

    private fun refreshArticlesInBackground() {
        lifecycleScope.launch {
            clearTab()
            Log.d("Articles Worker", "Work initiated")
            articlesListViewModel.refreshArticlesInBackground()
        }
    }

    private fun initRecyclerView() {
        binding.articlesRecyclerView.apply {
            adapter = articlesAdapter
        }
    }

    private fun initRefreshButton() {
        if (isHasInternet() && !isAirplaneModeOn()) {
            clearTab()
            refresh()
        } else {
            makeToast(getString(R.string.check_internet))
        }
    }

    private fun isAirplaneModeOn(): Boolean {
        return articlesListViewModel.isAirplaneModeOn()
    }

    private fun isHasInternet(): Boolean {
        return articlesListViewModel.isHasInternetConnection()
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
            scrollRecyclerViewToTopWhenItRefreshed()
        }
    }

    private fun scrollRecyclerViewToTopWhenItRefreshed() {
        articlesAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (binding.articlesRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    positionStart,
                    0)
            }
        })
    }

    private fun initFilterButton() {
        binding.filterButton.setOnClickListener {
            val action = ArticlesListDirections.actionDevExamToFilterBottomSheetFragment()
            view?.findNavController()?.safeNavigate(action)
        }
    }
}