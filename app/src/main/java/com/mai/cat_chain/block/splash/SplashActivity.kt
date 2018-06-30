package com.mai.cat_chain.block.splash

import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.block.login.LoginActivity
import com.mai.cat_chain.block.main.MainActivity
import com.mai.cat_chain.block.others.WayActivity
import com.mg.axechen.wanandroid.block.login.LoginModel
import com.mg.axechen.wanandroid.block.login.SplashModel
import com.mg.axechen.wanandroid.block.login.SplashPresenter
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import com.mai.cat_chain.net.schedules.SchedulerProvider
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

/**
 * Created by maijuntian on 2018/4/18.
 */
class SplashActivity : BaseActivity(), SplashContract.View {
    override fun loginSuccess() {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(Consumer { t ->
            startActivity<WayActivity>()
            finish()
        })
    }

    private val presenter: SplashPresenter by lazy {
        SplashPresenter(SplashModel(), LoginModel(), this, SchedulerProvider.getInstatnce()!!)
    }

    override fun sessionSuccess() {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(Consumer { t ->
            startActivity<LoginActivity>()
            finish()
        })
    }

    override fun sessionFail(errorMsg: String) {
        toast(errorMsg)
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        presenter.session()
    }

}