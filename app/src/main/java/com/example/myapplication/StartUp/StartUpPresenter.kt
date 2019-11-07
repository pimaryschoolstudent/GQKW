package com.example.myapplication.StartUp

import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.Main.MainList.MainActivity


class StartUpPresenter (activity:StartUpActivity): StartUpManifest.StartUpPresenter {
    override fun startAnimation(animation_view:LottieAnimationView) {
        val display = activity.getResources().getDisplayMetrics()
        var LayoutParams = ConstraintLayout.LayoutParams(display.heightPixels,display.heightPixels)
        LayoutParams.leftToLeft= ConstraintLayout.LayoutParams.PARENT_ID
        LayoutParams.rightToRight= ConstraintLayout.LayoutParams.PARENT_ID
        LayoutParams.topToTop= ConstraintLayout.LayoutParams.PARENT_ID
        LayoutParams.bottomToBottom= ConstraintLayout.LayoutParams.PARENT_ID
        animation_view.layoutParams=LayoutParams
        animation_view.setAnimation("startUp.json")
        animation_view.playAnimation()
    }

    var btnClick=false
    var activity = activity
     override fun startCountdown(i:Int) {
         var time = i;
        Thread(Runnable {
            kotlin.run {
            while (time>0){
                Thread.sleep(1000)
                activity.runOnUiThread(Runnable {
                    activity.changeButtonText("跳过("+time+")")
                })
                time--;
            }
                if (!btnClick){
                    skipActivity()
                }
            }
        }).start()
     }


     override fun getBackgroundImage() {
        //获取动画后期拓展
     }

     override fun skipActivity() {
         btnClick=true
         activity.startActivity(Intent(activity,MainActivity::class.java));
         activity.finish()
     }

 }