package com.example.amplifydatastore

import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.Post
import com.amplifyframework.datastore.generated.model.PostStatus

class DatastoreHelper {

    fun sync(
        callback: (isSynched: Boolean) -> Unit
    ) {
        Amplify.DataStore.start(
            {
                callback(true)
                Log.i("DatastoreHelper", "Data uploaded successfully")
            },
            {
                callback(false)
                Log.e("DatastoreHelper", "Error uploading data", it)
            }
        )
    }

    fun createPost(
        title: String,
        content: String,
        rating: Int,
        status: PostStatus,
        callback: (isCompleted: Boolean) -> Unit
    ) {
        val post = Post.builder()
            .title(title)
            .status(status)
            .rating(rating)
            .content(content)
            .build()

        Amplify.DataStore.save(
            post,
            {
                Log.i("DatastoreHelper", "Created a new post successfully")
                callback(true)
            },
            {
                Log.e("DatastoreHelper", "Error creating post", it)
                callback(false)
            }
        )
    }

    fun readPosts(
        callback: (postList: List<Post>) -> Unit
    ) {
        Amplify.DataStore.query(
            Post::class.java,
            { posts ->
                // TODO: Continue work here
                callback(posts)
            },
            {
                Log.e("DatastoreHelper", "Query failed", it)
            }
        )
    }
}