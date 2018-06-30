package com.mai.cat_chain.block.main.star

import com.mai.cat_chain.net.respone.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.mai.cat_chain.net.schedules.BaseSchedulerProvider

/**
 * Created by maijuntian on 2018/6/19.
 */
class StarBasePresenter(model: StarBaseModel, view: StarBaseContract.View, schedulerProvider: BaseSchedulerProvider) : StarBaseContract.Presenter {

    var model: StarBaseModel? = null

    var view: StarBaseContract.View? = null

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

    override fun queryM() {
        var compose: Disposable? = model?.queryUserNotCollectedM()
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.queryMSuccess(t)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }

    override fun queryRank(type: String) {
        var compose: Disposable? = model?.queryRank(type)
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            if (type == "coin") {
                                view?.queryMRankSuccess(t)
                            } else {
                                view?.queryHRankSuccess(t)
                            }
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }

    override fun collectedM(id: String, coinNumber: Float) {
        var compose: Disposable? = model?.collectedM(id, coinNumber)
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { _ ->
                            view?.collectedMSuccess(id, coinNumber)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }
}
