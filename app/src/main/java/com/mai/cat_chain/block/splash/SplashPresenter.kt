package com.mg.axechen.wanandroid.block.login

import com.mai.cat_chain.block.splash.SplashContract
import com.mai.cat_chain.net.respone.ResponseTransformer
import com.mai.cat_chain.utils.Key
import com.mai.cat_chain.utils.SharedPreferencesUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.mai.cat_chain.net.schedules.BaseSchedulerProvider

/**
 * Created by maijuntian on 2018/3/20.
 */
class SplashPresenter(splashModel: SplashModel, loginModel: LoginModel, view: SplashContract.View, schedulerProvider: BaseSchedulerProvider) : SplashContract.Presenter {

    var splashModel: SplashModel? = null

    var loginModel: LoginModel? = null

    var view: SplashContract.View? = null

    var scheduler: BaseSchedulerProvider? = null

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    init {
        this.splashModel = splashModel
        this.loginModel = loginModel
        this.view = view
        this.scheduler = schedulerProvider
    }

    override fun unSubscribe() {
        disposable.dispose()
    }


    override fun session() {

        var userName: String = SharedPreferencesUtils.getString(Key.USERNAME)
        var password: String = SharedPreferencesUtils.getString(Key.PASSWORD)


        var observable: Observable<String>? = splashModel?.session()
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())

        if (!password.isEmpty()) {
            var compose: Disposable? = observable?.flatMap { t ->
                SharedPreferencesUtils.putString(Key.SESSION_ID, t)
                loginModel!!.login(userName, password)?.compose(scheduler?.applySchedulers())
            }?.compose(ResponseTransformer.handleResult())
                    ?.subscribe(
                            { t ->
                                SharedPreferencesUtils.putString(Key.USER_KEY, t)
                                view?.loginSuccess()
                            },
                            { throwable -> view?.sessionFail(throwable.message!!) }
                    )
            disposable.add(compose!!)
        } else {
            var compose: Disposable? = observable?.subscribe(
                    { t ->
                        SharedPreferencesUtils.putString(Key.SESSION_ID, t)
                        view?.sessionSuccess()
                    },
                    { throwable -> view?.sessionFail(throwable.message!!) }
            )
            disposable.add(compose!!)
        }

    }


}