package my.project.techtestapp.presentation.fragments.devExam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentDevExamBinding
import my.project.techtestapp.presentation.MainViewModel

@AndroidEntryPoint
class DevExam : Fragment(R.layout.fragment_dev_exam) {

    private val binding by viewBinding(FragmentDevExamBinding::bind)
    private val articlesAdapter by lazy { DevExamAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setDataToRecyclerView()
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.apply {
            adapter = articlesAdapter
        }
    }

    private fun setDataToRecyclerView() {
        mainViewModel.getArticles()
        mainViewModel.listArticles.observe(viewLifecycleOwner) { articleItem ->
            if (articleItem != null) {
                articlesAdapter.setArticles(articleItem)
            }
        }
    }
}
