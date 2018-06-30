package com.mai.cat_chain.block.record

import com.mai.cat_chain.net.respone.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.mai.cat_chain.net.schedules.BaseSchedulerProvider

/**
 * Created by maijuntian on 2018/6/19.
 */
class RecordPresenter(model: RecordModel, view: RecordContract.View, schedulerProvider: BaseSchedulerProvider) : RecordContract.Presenter {


    var model: RecordModel? = null

    var view: RecordContract.View? = null

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

    override fun queryHRecord(beginTime: String, endTime: String) {
        var compose: Disposable? = model?.queryHRecord(beginTime, endTime)
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.queryHRecordSuccess(t)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }

    override fun queryMRecord(beginTime: String, endTime: String) {
        var compose: Disposable? = model?.queryMRecord(beginTime, endTime)
                ?.compose(scheduler?.applySchedulers())
                ?.compose(ResponseTransformer.handleResult())
                ?.subscribe(
                        { t ->
                            view?.queryMRecordSuccess(t)
                        },
                        { throwable ->
                            throwable.printStackTrace()
                            view?.fail(throwable.message!!)
                        }
                )
        disposable.add(compose!!)
    }

}
