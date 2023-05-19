package data.post.data_sources

import data.post.Post
import data.post.PostDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import services.api.HttpService

class JsonApiPlaceHolderPostDataSource(
    private val client: HttpClient = HttpService.client
): PostDataSource {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
    override suspend fun getAll(): List<Post> {
        try {
            val response = client.get("$BASE_URL/posts")
            return response.body()
        } catch (e: ResponseException) {
            throw e
        }
    }

    override suspend fun getOne(id: Int): Post? {
        throw NotImplementedError()
    }

    override suspend fun createOne(entity: Post): Boolean {
        throw NotImplementedError()
    }

    override suspend fun updateOne(id: Int, entity: Post): Boolean {
        throw NotImplementedError()
    }

    override suspend fun deleteOne(id: Int): Boolean {
        throw NotImplementedError()
    }

}