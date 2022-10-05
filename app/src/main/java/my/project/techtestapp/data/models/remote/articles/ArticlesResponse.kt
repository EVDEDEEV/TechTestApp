package my.project.techtestapp.data.models.remote.articles

import my.project.techtestapp.data.models.database.articles.ArticlesEntity


class ArticlesResponse : ArrayList<ArticlesResponseItem>()

fun List<ArticlesResponseItem>.mapToEntity(): List<ArticlesEntity> {
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