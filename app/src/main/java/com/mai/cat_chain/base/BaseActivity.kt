package com.mai.cat_chain.base

import android.app.ActivityManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.mai.cat_chain.R

/**
 * Created by maijuntian on 2018/6/13.
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutId())
        initView()
    }

    protected abstract fun setLayoutId(): Int

    protected abstract fun initView()

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = getStatusColor()
        }
    }

    open fun getStatusColor(): Int {
        return ContextCompat.getColor(this, R.color.blue)
    }
}