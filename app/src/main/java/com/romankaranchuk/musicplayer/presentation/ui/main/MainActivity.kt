
package com.romankaranchuk.musicplayer.presentation.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.romankaranchuk.musicplayer.R
import com.romankaranchuk.musicplayer.data.Song
import timber.log.Timber
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {

//    private var mIsServiceBound: Boolean = false
//    private lateinit var mSearchBinder: SearchService.SearchBinder

//    private val mSearchConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
//            Timber.d("MainActivity onServiceConnected")
//            mSearchBinder = (binder as SearchService.SearchBinder)
//            mIsServiceBound = true
////            mSearchService.startMusicSearch()
//        }
//
//        override fun onServiceDisconnected(name: ComponentName) {
//            Timber.d("MainActivity onServiceDisconnected")
//            mIsServiceBound = false
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val playerFragment = supportFragmentManager.findFragmentByTag(PlayerFragment.PLAYER_FRAGMENT_TAG)
//        val tracklistFragment = supportFragmentManager.findFragmentByTag(TRACK_LIST_TAG)
        val transaction = supportFragmentManager.beginTransaction()
        when {
//            playerFragment != null -> transaction.replace(R.id.fragment_container_main_activity, playerFragment, PlayerFragment.PLAYER_FRAGMENT_TAG)
//            tracklistFragment != null -> transaction.replace(R.id.fragment_container_main_activity, tracklistFragment, TRACK_LIST_TAG)
            else -> transaction.replace(R.id.ma_container, MainFragment.newInstance(), MainFragment.TAG)
        }
        transaction.commit()

        Timber.d("onCreate")
    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//
//        if (hasFocus) {
//            hideSystemUI(window)
//        }