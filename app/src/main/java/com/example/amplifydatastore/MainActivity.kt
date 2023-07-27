package com.example.amplifydatastore

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Switch
import android.widget.Toast
import com.amplifyframework.datastore.generated.model.PostStatus

class MainActivity : AppCompatActivity() {
    private lateinit var edtTitle: EditText
    private lateinit var edtContent: EditText
    private lateinit var ratingView: RatingBar
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var isActiveView: Switch
    private lateinit var btnPost: Button
    private lateinit var btnSync: Button

    private val datastore by lazy {
        DatastoreHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtTitle = findViewById(R.id.edt_title)
        edtContent = findViewById(R.id.edt_content)
        ratingView = findViewById(R.id.rating_view)
        isActiveView = findViewById(R.id.post_status)
        btnPost = findViewById(R.id.btn_post)
        btnSync = findViewById(R.id.btn_sync)

        btnPost.setOnClickListener {
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()
            val rating = ratingView.numStars
            val status = if (isActiveView.isChecked) PostStatus.ACTIVE else PostStatus.INACTIVE

            datastore.createPost(title, content, rating, status, ::handleCreationResult)
        }

        btnSync.setOnClickListener {
            datastore.sync(::handleSyncResult)
        }
    }

    private fun handleCreationResult(isCompleted: Boolean) {
        showMessage(if (isCompleted) "Post created" else "Something goes wrong")
    }

    private fun handleSyncResult(isSync: Boolean) {
        showMessage(if (isSync) "Data uploaded" else "Something goes wrong")
    }

    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
