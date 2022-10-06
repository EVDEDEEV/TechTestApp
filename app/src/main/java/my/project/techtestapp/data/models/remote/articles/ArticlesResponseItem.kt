package my.project.techtestapp.data.models.remote.articles


import com.google.gson.annotations.SerializedName

data class ArticlesResponseItem(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("title")
    val title: String
)