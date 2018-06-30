package com.mai.cat_chain.javabean

/**
 * Created by maijuntian on 2018/6/19.
 */


data class Rank(
        var topRank: List<TopRank>,
        var myRank: MyRank,
        var time: String
)

data class TopRank(
        var userId: String,
        var ranking: Int,
        var headImgUrl: String,
        var userName: String,
        var level: String,
        var score: Float
)

data class MyRank(
        var userId: String,
        var ranking: Int,
        var headImgUrl: String,
        var userName: String,
        var level: String,
        var score: Float
)