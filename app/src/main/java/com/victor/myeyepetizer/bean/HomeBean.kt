package com.victor.myeyepetizer.bean

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
data class HomeBean(var itemList: ArrayList<Item>, var nextPageUrl: String, val nextPublishTime: Long, val refreshCount: Int, val lastStartId: Int, val date: Long, val adExist: Boolean, val count: Int, val total: Int, val dialog: Any)