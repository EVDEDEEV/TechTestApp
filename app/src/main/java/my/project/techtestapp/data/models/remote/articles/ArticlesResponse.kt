package my.project.techtestapp.data.models.remote.articles

import my.project.techtestapp.data.models.database.articles.ArticlesEntity


class ArticlesResponse : ArrayList<ArticlesResponseItem>()

fun List<ArticlesResponseItem>.mapToEntity(): List<ArticlesEntity?> {
    return this.map {
        it.id?.let { it1 ->
            it.date?.let { it2 ->
                it.image?.let { it3 ->
                    it.sort?.let { it4 ->
                        it.text?.let { it5 ->
                            it.title?.let { it6 ->
                                ArticlesEntity(
                                    id = it1,
                                    date = it2,
                                    image = it3,
                                    sort = it4,
                                    text = it5,
                                    title = it6,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}