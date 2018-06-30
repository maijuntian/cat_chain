package com.mai.cat_chain.block.main.me

import android.view.View
import com.mai.cat_chain.MyApplication
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseFragment
import com.mai.cat_chain.block.login.LoginActivity
import com.mai.cat_chain.block.main.star.StarBaseModel
import com.mai.cat_chain.block.others.StarIntroduceActivity
import com.mai.cat_chain.block.record.RecordActivity
import com.mai.cat_chain.block.task.TaskActivity
import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.net.schedules.SchedulerProvider
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.fragment_me_base.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by maijuntian on 2018/6/15.
 */
class MeBaseFragment : BaseFragment() {

    override fun setLayoutId(): Int {
        return R.layout.fragment_me_base
    }

    override fun initView() {
        iv_setting.setOnClickListener { v ->
            if (iv_logout.visibility == View.VISIBLE)
                iv_logout.visibility = View.INVISIBLE
            else
                iv_logout.visibility = View.VISIBLE
        }


        iv_logout.setOnClickListener { v ->
            MyApplication.instance!!.logout()
            startActivity<LoginActivity>()
            activity.finish()
        }

        tv_up_speed.setOnClickListener { v ->
            startActivity<TaskActivity>()
        }

        iv_introduce.setOnClickListener {
            startActivity<StarIntroduceActivity>()
        }

        tv_my_property.setOnClickListener {
            startActivity<RecordActivity>()
        }

        notifyUserInfo()
    }

    override fun notifyUserInfo() {

        var data: User? = MyApplication.instance!!.user ?: return

        tv_username.text = data!!.catName
        tv_level.text = "LV." + data.level
    }

}