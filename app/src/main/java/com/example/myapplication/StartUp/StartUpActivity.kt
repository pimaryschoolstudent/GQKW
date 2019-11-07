package com.example.myapplication.StartUp

import android.content.Context
import android.util.DebugUtils
import android.util.Log
import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_startup.*
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID


class StartUpActivity ():BaseActivity(),StartUpManifest.StartUpView{
    override fun addTobBar(): Boolean {
        return false
    }

    var startUpPresenter=StartUpPresenter(this)
    override fun changeButtonText(content: String) {
        btn_skip.setText(content)
    }

    override fun startAnimation() {
        startUpPresenter.startAnimation(animation_view)
    }

    override fun startActivity() {
        startUpPresenter.startCountdown(5)
    }

    override fun skipActivity() {
        startUpPresenter.skipActivity()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_startup
    }

    override fun Entrance() {
        startActivity()
        startAnimation()
        btn_skip.setOnClickListener {
            skipActivity()
        }
    }

}