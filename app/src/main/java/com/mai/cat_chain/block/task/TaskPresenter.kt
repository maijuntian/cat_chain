package com.mai.cat_chain.block.task

import com.mai.cat_chain.net.respone.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.mai.cat_chain.net.schedules.BaseSchedulerProvider

/**
 * Created by maijuntian on 2018/6/19.
 */
class TaskPresenter(model: TaskModel, view: TaskContract.View, schedulerProvider: BaseSchedulerProvider) : TaskContract.Presenter {


    var model: TaskModel? = null

    var view: TaskContract.View? = null

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

    override fun getAdvert() {
        var compose: Disposable? = model?.getAdvert()
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.getAdvertSuccess(t)
                        },
                        { throwable -> throwable.printStackTrace() }
                )
        disposable.add(compose!!)
    }

    override fun getTodayTask() {
        var compose: Disposable? = model?.getTodayTask()
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.getTodayTaskSuccess(t)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }

    override fun getBaseTask() {
        var compose: Disposable? = model?.getBaseTask()
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.getBaseTaskSuccess(t)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }


}
