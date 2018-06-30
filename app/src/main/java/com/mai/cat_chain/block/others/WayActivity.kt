package com.mai.cat_chain.block.others

import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.R
import com.mai.cat_chain.block.main.MainActivity
import com.mai.cat_chain.block.task.TaskActivity
import kotlinx.android.synthetic.main.activity_cheats.*
import kotlinx.android.synthetic.main.activity_way.*
import org.jetbrains.anko.startActivity

/**
 * Created by maijuntian on 2018/6/13.
 */
class WayActivity : BaseActivity() {


    override fun setLayoutId(): Int {
        return R.layout.activity_way
    }

    override fun initView() {
        bt_type1.setOnClickListener { v ->
            startActivity<IndexActivity1>()
        }

        bt_type2.setOnClickListener { v ->
            startActivity<IndexActivity2>()
        }

        bt_type3.setOnClickListener { v ->
            startActivity<MainActivity>()
        }
    }

}