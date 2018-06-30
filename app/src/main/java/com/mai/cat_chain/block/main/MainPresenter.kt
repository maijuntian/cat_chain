package com.mai.cat_chain.block.main

import com.mai.cat_chain.net.respone.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.mai.cat_chain.net.schedules.BaseSchedulerProvider

/**
 * Created by maijuntian on 2018/6/19.
 */
class MainPresenter(model: MainModel, view: MainContract.View, schedulerProvider: BaseSchedulerProvider) : MainContract.Presenter {


    var model: MainModel? = null

    var view: MainContract.View? = null

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

    override fun queryUserInfo() {

        var compose: Disposable? = model?.queryUserInfo()
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.queryUserInfoSuccess(t)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }


}
