package com.example.amplifydatastore

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin


class AmplifyDatastoreApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Log.i("AmplifyDatastore", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("AmplifyDatastore", "Could not initialize Amplify", error)
        }
    }
}