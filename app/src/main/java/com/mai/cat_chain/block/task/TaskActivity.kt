package com.mai.cat_chain.block.task

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.base.BaseFragment
import com.mai.cat_chain.javabean.*
import com.mai.cat_chain.net.schedules.SchedulerProvider
import kotlinx.android.synthetic.main.activity_task.*
import android.widget.ImageView.ScaleType
import com.bigkoo.convenientbanner.ConvenientBanner
import com.mai.cat_chain.block.others.WebViewActivity
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by maijuntian on 2018/6/15.
 */
class TaskActivity : BaseActivity(), TaskContract.View {

    override fun fail(msg: String) {
        toast(msg)
    }

    override fun getAdvertSuccess(data: MutableList<String>) {

        (cb_advert as ConvenientBanner<String>)!!.setPages(object : CBViewHolderCreator {
            override fun createHolder(itemView: View?): Holder<String> {
                return object : Holder<String>(itemView) {


                    private var ivAdvert: ImageView? = null

                    override fun updateUI(data: String?) {

                        Glide.with(this@TaskActivity).load(data).into(ivAdvert)
                    }

                    override fun initView(itemView: View?) {
                        ivAdvert = itemView!!.find(R.id.iv_advert)
                    }
                }
            }

            override fun getLayoutId(): Int {
                return R.layout.item_advert
            }

        }, data).setPageIndicator(intArrayOf(R.drawable.record_radio_n, R.drawable.record_radio_s)).startTurning()
    }

    override fun getTodayTaskSuccess(data: MutableList<TodayTask>) {
        lv_today_task.adapter = object : BaseListViewAdapter<TodayTask>(data) {
            override fun bindLayoutId(position: Int): Int {
                return R.layout.lv_item_task
            }

            override fun initView(data: TodayTask?, viewHolder: BaseViewHolder?) {
                viewHolder!!.setText(R.id.tv_title, data!!.topic)
                        .setText(R.id.tv_content, data.taskIntrouction)
            }
        }

        lv_today_task.setOnItemClickListener { parent, view, position, id ->
            startActivity<WebViewActivity>("url" to data[position].pageUrl)
        }
    }

    override fun getBaseTaskSuccess(data: MutableList<BaseTask>) {
        gv_base_task.adapter = object : BaseListViewAdapter<BaseTask>(data) {
            override fun bindLayoutId(position: Int): Int {
                return R.layout.item_gv_task
            }

            override fun initView(data: BaseTask?, viewHolder: BaseViewHolder?) {
                viewHolder!!.setText(R.id.tv_content, data!!.topic + "\n+" + data.hValue + "h值")
                Glide.with(this@TaskActivity).load(data.imageUrl).into(viewHolder.findViewById(R.id.iv_icon))
            }

        }

    }

    override fun onDestroy() {
        (cb_advert as ConvenientBanner<String>).stopTurning()
        super.onDestroy()
    }

    private val presenter: TaskPresenter by lazy {
        TaskPresenter(TaskModel(), this, SchedulerProvider.getInstatnce()!!)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_task
    }

    override fun initView() {
        presenter.getAdvert()
        presenter.getBaseTask()
        presenter.getTodayTask()

        iv_return.setOnClickListener { v ->
            finish()
        }
    }
}