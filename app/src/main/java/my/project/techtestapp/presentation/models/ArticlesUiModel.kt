package my.project.techtestapp.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlesUiModel(
    val date: String,
    val id: String,
    val image: String,
    val sort: Int,
    val text: String,
    val title: String,
) : Parcelable

