package com.example.myapplication.Callback

import android.app.Activity
import android.app.Application
import android.os.Bundle

abstract class BaseActivityCallback:Application.ActivityLifecycleCallbacks{
    override fun onActivityPaused(activity: Activity?) {

    }

    override abstract fun onActivityResumed(activity: Activity?)


    override abstract fun onActivityStarted(activity: Activity?)

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override abstract fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?)



}