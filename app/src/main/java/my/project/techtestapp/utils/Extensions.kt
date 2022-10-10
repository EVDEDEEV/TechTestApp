package my.project.techtestapp.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.project.techtestapp.data.models.database.articlesTable.ArticlesEntity
import my.project.techtestapp.data.models.remote.articles.ArticlesResponseItem
import my.project.techtestapp.presentation.models.ArticlesListUiModel
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun <T, F : Fragment> F.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest {
                onCollect(it)
            }
        }
    }
}

fun Fragment.makeToast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}

fun ArticlesListUiModel.formatDate(date: String): String {
    val dateTime: ZonedDateTime = OffsetDateTime.parse(date).toZonedDateTime()
    val defaultZoneTime: ZonedDateTime =
        dateTime.withZoneSameInstant(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_PATTERN)
    return defaultZoneTime.format(formatter)
}

fun List<ArticlesListUiModel>.mapToEntity(): List<ArticlesEntity> {
    return this.map {
        ArticlesEntity(id = it.id,
            date = it.date,
            image = it.image,
            sort = it.sort,
            text = it.text,
            title = it.title
        )
    }
}

fun List<ArticlesEntity>.mapFromArticlesEntityToUiModel(): List<ArticlesListUiModel> {
    return this.map {
        ArticlesListUiModel(date = it.date,
            id = it.id,
            image = it.image,
            sort = it.sort,
            text = it.text,
            title = it.title
        )
    }
}

fun List<ArticlesResponseItem>.mapFromArticlesResponseToUiModel(): List<ArticlesListUiModel> {
    return this.map {
        ArticlesListUiModel(id = it.id,
            date = it.date,
            image = it.image,
            sort = it.sort,
            text = it.text,
            title = it.title
        )
    }
}


fun String.changeXtoNumber(): String {
    return this.replace('Х', '#', true)
}

