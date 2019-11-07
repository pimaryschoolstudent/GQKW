package com.example.myapplication.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.RESTART
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_startup.*
import kotlinx.android.synthetic.main.loading_dialog.*

class LoadingDialog: Dialog {

    constructor(context: Context):super(context)
    fun showDialog(){
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.loading_dialog)

        lav.setAnimation("loading.json")
        lav.playAnimation()
        show()
    }
    fun close(){
        dismiss()
    }
}