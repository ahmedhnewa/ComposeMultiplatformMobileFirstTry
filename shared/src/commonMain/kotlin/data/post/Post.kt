package data.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int = 1,
    val id: Int = 1,
    val title: String,
    val body: String
)