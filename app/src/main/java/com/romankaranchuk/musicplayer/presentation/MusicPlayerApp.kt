package com.romankaranchuk.musicplayer.presentation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.romankaranchuk.musicplayer.BuildConfig
import com.romankaranchuk.musicplayer.di.AppDeps
import com.romankaranchuk.musicplayer.di.DaggerAppComponent
import com.romankaranchuk.musicplayer.di.util.Injectable
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class MusicPlayerApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var context: Context
    }

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        cont