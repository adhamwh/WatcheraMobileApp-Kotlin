package com.example.watchera

import android.app.Application
import com.google.firebase.FirebaseApp

class WatcheraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this) //  This initializes Firebase
    }
}
