package data

interface CrudRepository<T, Id> {
    suspend fun getAll(): List<T>
    suspend fun getOne(id: Id): T?
    suspend fun createOne(entity: T): Boolean
    suspend fun updateOne(id: Id, entity: T): Boolean
    suspend fun deleteOne(id: Id): Boolean
}