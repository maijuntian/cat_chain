package com.mai.cat_chain.javabean

/**
 * Created by maijuntian on 2018/6/13.
 */

data class BaseTask(
        val taskId: String,
        val imageUrl: String,
        val hValue: Float,
        val pageUrl: String,
        val topic: String
)