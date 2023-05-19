package data.post

import data.post.data_sources.JsonApiPlaceHolderPostDataSource

object PostDataSourceService: PostDataSource {
    private val service = JsonApiPlaceHolderPostDataSource()
    override suspend fun getAll(): List<Post> = service.getAll()

    override suspend fun getOne(id: Int): Post? = service.getOne(id)

    override suspend fun createOne(entity: Post): Boolean = service.createOne(entity)

    override suspend fun updateOne(id: Int, entity: Post): Boolean = service.updateOne(id, entity)

    override suspend fun deleteOne(id: Int): Boolean = service.deleteOne(id)
}