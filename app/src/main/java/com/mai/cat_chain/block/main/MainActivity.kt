package com.mai.cat_chain.block.main

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.mai.cat_chain.MyApplication
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.base.BaseFragment
import com.mai.cat_chain.block.main.me.MeBaseFragment
import com.mai.cat_chain.block.main.star.StarBaseFragment
import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.net.schedules.SchedulerProvider
import com.mai.cat_chain.utils.MLog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.lang.ref.WeakReference

/**
 * Created by maijuntian on 2018/6/14.
 */
class MainActivity : BaseActivity(), MainContract.View {
    override fun fail(msg: String) {
        toast(msg)
    }

    private var viewPager: ViewPager? = null

    private var mainAdapter: MainAdapter? = null

    private var fragments = mutableListOf<Fragment>()

    private val presenter: MainPresenter by lazy {
        MainPresenter(MainModel(), this, SchedulerProvider.getInstatnce()!!)
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        viewPager = vp_content
        fragments.add(StarBaseFragment())
        fragments.add(MeBaseFragment())

        mainAdapter = MainAdapter(supportFragmentManager, fragments)

        viewPager.run {
            this!!.adapter = mainAdapter
            addOnPageChangeListener(PagerChangeListener(this@MainActivity))
            offscreenPageLimit = fragments.size
        }

        rb_cat_base.setOnClickListener({ v ->
            viewPager!!.setCurrentItem(0, true)
        })

        rb_my_base.setOnClickListener({ v ->
            viewPager!!.setCurrentItem(1, true)
        })

        iv_return.setOnClickListener { v ->
            finish()
        }

        rb_cat_base.isChecked = true

        presenter.queryUserInfo()
    }


    class PagerChangeListener(activity: MainActivity) : ViewPager.OnPageChangeListener {

        private var weakActivity: WeakReference<MainActivity>? = null

        init {
            this.weakActivity = WeakReference(activity)
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            var activity: MainActivity? = weakActivity?.get()
            if (activity != null) {
                activity.selectByIndex(position)
            }
        }
    }

    private fun selectByIndex(position: Int) {
        MLog.log("selectIndex-->$position")
        when (position) {
            0 -> {
                rb_cat_base.isChecked = true
            }
            1 -> {
                rb_my_base.isChecked = true
            }
        }

    }


    override fun queryUserInfoSuccess(datas: User) {
        MyApplication.instance!!.user = datas

        fragments.forEach { fragment: Fragment ->
            (fragment as BaseFragment).notifyUserInfo()
        }
    }

}