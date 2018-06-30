package com.mai.cat_chain.block.others

import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.MenuItem
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * Created by maijuntian on 2018/3/31.
 *
 * webViewActivity
 * 基本的访问操作
 */
open class WebViewActivity : BaseActivity() {
    override fun initView() {

        iv_return.setOnClickListener {
            finish()
        }
        getIntentData()
        showWebContent()
    }


    /**
     * url
     */
    var url: String? = null


    override fun setLayoutId(): Int {
        return R.layout.activity_web_view
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 获取传入的数据
     */
    open fun getIntentData() {
        url = intent.getStringExtra("url")
    }

    /**
     * 展示webView的内容
     */
    fun showWebContent() {
        if (TextUtils.isEmpty(url)) {
            throw RuntimeException("传入的地址不存在")
        }
        var builder: AgentWeb.AgentBuilder = AgentWeb.with(this)
        builder.setAgentWebParent(flWebContent, FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setIndicatorColor(ContextCompat.getColor(this, R.color.blue))
                .createAgentWeb()
                .ready().go(url)
    }


}