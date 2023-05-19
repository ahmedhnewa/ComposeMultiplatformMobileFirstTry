package data.post

import data.CrudRepository

interface PostDataSource: CrudRepository<Post, Int>