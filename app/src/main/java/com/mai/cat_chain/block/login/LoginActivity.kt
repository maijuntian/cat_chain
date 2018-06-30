package com.mai.cat_chain.block.login

import android.app.ProgressDialog
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.R
import com.mai.cat_chain.block.main.MainActivity
import com.mai.cat_chain.block.others.WayActivity
import com.mg.axechen.wanandroid.block.login.LoginModel
import com.mg.axechen.wanandroid.block.login.LoginPresenter
import com.mai.cat_chain.utils.SharedPreferencesUtils
import com.mai.cat_chain.utils.Key
import kotlinx.android.synthetic.main.activity_login.*
import com.mai.cat_chain.net.schedules.SchedulerProvider
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by maijuntian on 2018/6/13.
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    var dialog: ProgressDialog? = null

    override fun loginSuccess() {
        if (dialog != null)
            dialog!!.dismiss()
        startActivity<WayActivity>()
    }

    override fun loginFail(errorMsg: String) {
        if (dialog != null)
            dialog!!.dismiss()
        toast(errorMsg)
    }

    override fun userNameIsEmpty() {
        if (dialog != null)
            dialog!!.dismiss()
        toast("用户名不能为空")
    }

    override fun passwordIsEmpty() {
        if (dialog != null)
            dialog!!.dismiss()
        toast("密码不能为空")
    }


    private val presenter: LoginPresenter by lazy {
        LoginPresenter(LoginModel(), this, SchedulerProvider.getInstatnce()!!)
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        et_username.setText(SharedPreferencesUtils.getString(Key.USERNAME))

        iv_login.setOnClickListener({
            dialog = indeterminateProgressDialog("正在登陆请稍等...")
            dialog!!.show()
            var username: String = et_username.text.toString()
            var password: String = et_password.text.toString()

            presenter.login(username, password)
        })

    }

}