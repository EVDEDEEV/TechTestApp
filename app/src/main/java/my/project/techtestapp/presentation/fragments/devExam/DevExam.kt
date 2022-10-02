package my.project.techtestapp.presentation.fragments.devExam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import my.project.techtestapp.R
import my.project.techtestapp.databinding.FragmentDevExamBinding

class DevExam : Fragment(R.layout.fragment_dev_exam) {

    private val binding by viewBinding(FragmentDevExamBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}