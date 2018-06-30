package com.mai.cat_chain.javabean

/**
 * Created by maijuntian on 2018/6/26.
 */

data class RecordList(
        val income: List<Record>,
        val outcome: List<Record>,
        val detail: List<Record>
)
