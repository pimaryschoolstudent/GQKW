package com.example.myapplication.StartUp

import com.airbnb.lottie.LottieAnimationView

interface StartUpManifest {
    interface StartUpView{
        fun startActivity()
        fun skipActivity()
        fun changeButtonText(content:String)
        fun startAnimation()
    }
    interface StartUpPresenter{
        fun startCountdown(i:Int)
        fun getBackgroundImage()
        fun skipActivity()
        fun startAnimation(animation_view: LottieAnimationView)
    }
}