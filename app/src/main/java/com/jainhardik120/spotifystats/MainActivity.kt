package com.jainhardik120.spotifystats

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonGetCode = findViewById<Button>(R.id.buttonGetCode)
        val authorization = "Basic " + Base64.getEncoder().encodeToString("9197e9d29e34456a84fefc715e531a6a:7eb3629e8beb40bdb4fdf2f83223956e".toByteArray())
        val authorizationUrl = "https://accounts.spotify.com/authorize"
        val scope = "user-read-playback-state playlist-read-private user-follow-modify playlist-read-collaborative user-follow-read user-read-currently-playing user-library-modify playlist-modify-private playlist-modify-public user-read-email user-top-read streaming user-read-recently-played user-read-private user-library-read"
        val redirectUri = "auth://localhost"
        val uniqueState = UUID.randomUUID().toString()
        val uri = Uri.parse(authorizationUrl).buildUpon()
            .appendQueryParameter("client_id", "9197e9d29e34456a84fefc715e531a6a")
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", scope)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("state", uniqueState)
            .build()
        val builder = CustomTabsIntent.Builder()
        Log.d("TAG", "onCreate: " + uri.toString())
        buttonGetCode.setOnClickListener {
            val customTabsIntent : CustomTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, uri)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code")
        Log.d("TAG", "onNewIntent: " + code)
        Log.d("TAG", intent?.data.toString())
    }
}