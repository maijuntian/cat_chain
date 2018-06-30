package com.mg.axechen.wanandroid.block.login

import android.text.TextUtils
import com.mai.cat_chain.block.login.LoginContract
import com.mai.cat_chain.net.respone.ResponseTransformer
import com.mai.cat_chain.utils.Key
import com.mai.cat_chain.utils.SharedPreferencesUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.mai.cat_chain.net.schedules.BaseSchedulerProvider

/**
 * Created by maijuntian on 2018/3/20.
 */
class LoginPresenter(model: LoginModel, view: LoginContract.View, schedulerProvider: BaseSchedulerProvider) : LoginContract.Presenter {

    var model: LoginModel? = null

    var view: LoginContract.View? = null

    var scheduler: BaseSchedulerProvider? = null

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    init {
        this.model = model
        this.view = view
        this.scheduler = schedulerProvider
    }

    override fun unSubscribe() {
        disposable.dispose()
    }

    override fun login(userName: String, password: String) {
        if (TextUtils.isEmpty(userName)) {
            view?.userNameIsEmpty()
            return
        }

        if (TextUtils.isEmpty(password)) {
            view?.passwordIsEmpty()
            return
        }

        var compose: Disposable? = model?.login(userName, password)
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            SharedPreferencesUtils.putString(Key.USERNAME, userName)
                            SharedPreferencesUtils.putString(Key.PASSWORD, password)
                            SharedPreferencesUtils.putString(Key.USER_KEY, t)
                            view?.loginSuccess()
                        },
                        { throwable -> view?.loginFail(throwable.message!!) }
                )
        disposable.add(compose!!)
    }
}