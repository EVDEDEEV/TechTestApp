package my.project.techtestapp.presentation.fragments.articlesList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentArticlesListBinding
import my.project.techtestapp.utils.ArticlesState
import my.project.techtestapp.utils.OnArticleClicked
import my.project.techtestapp.utils.makeToast
import my.project.techtestapp.utils.safeNavigate

@AndroidEntryPoint
class ArticlesListFragment : Fragment(R.layout.fragment_articles_list) {

    private val binding by viewBinding(FragmentArticlesListBinding::bind)
    private val articlesAdapter by lazy { ArticlesListAdapter(onClick) }
    private val articlesListViewModel: ArticlesListViewModel by viewModels()

    private val onClick: OnArticleClicked = {
        val action = ArticlesListFragmentDirections.actionListFragmentToDetailedArticleFragment(it)
        view?.findNavController()?.safeNavigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Articles Worker", "ArticlesListFragment created")
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        checkArticlesListUiState()
        initFilterButton()
        setToolbarMenu()
        setDataToRecyclerView()
    }

    private fun setToolbarMenu() {
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

    private fun initRefreshButton() {
        val isHasInternetConnection = articlesListViewModel.isHasInternetConnection()
        val isAirplaneModeOn = articlesListViewModel.isAirplaneModeOn()
        if (isHasInternetConnection && !isAirplaneModeOn) {
            clearTab()
            loadArticles()
        } else {
            makeToast(getString(R.string.check_internet))
        }
    }

    private fun initRecyclerView() {
        binding.articlesRecyclerView.apply {
            adapter = articlesAdapter
        }
    }

    private fun loadArticles() {
        articlesListViewModel.loadArticles()
    }

    private fun clearTab() {
        articlesListViewModel.deleteFromTab()
    }

    private fun setDataToRecyclerView() {
        articlesListViewModel.listArticles.observe(viewLifecycleOwner) {
            articlesAdapter.submitList(it)
            scrollRecyclerViewToTopWhenItRefreshed()
        }
    }

    private fun checkArticlesListUiState() {
        lifecycleScope.launchWhenCreated {
            binding.apply {
                articlesListViewModel.articlesState.collect {
                    when (it) {
                        is ArticlesState.Data -> {
                            articlesRecyclerView.isVisible = true
                            articlesLoadProgressbar.isVisible = false
                            Log.d("Articles Worker", "DATA")
                        }
                        is ArticlesState.Loading -> {
                            articlesRecyclerView.isVisible = false
                            articlesLoadProgressbar.isVisible = true
                            Log.d("Articles Worker", "LOAD")
                        }
                        is ArticlesState.Error -> {
                            Log.d("Articles Worker", "ERROR")
                            makeToast(getString(R.string.error_answer))
                        }
                    }
                }
            }
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
            val action = ArticlesListFragmentDirections.actionDevExamToFilterBottomSheetFragment()
            view?.findNavController()?.safeNavigate(action)
        }
    }
}