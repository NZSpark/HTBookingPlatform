package com.seclib.htbp.sms.utils

import java.text.DecimalFormat
import java.util.*

object RandomUtil {
    private val random = Random()
    private val fourdf = DecimalFormat("0000")
    private val sixdf = DecimalFormat("000000")
    fun getFourBitRandom (): String = fourdf.format(random.nextInt(10000).toLong())
    fun getSixBitRandom (): String = sixdf.format(random.nextInt(1000000).toLong())

    /**
     * 给定数组，抽取n个数据
     * @param list
     * @param n
     * @return
     */
    fun getRandom(list: List<*>, n: Int): ArrayList<*> {
        val random = Random()
        val hashMap = HashMap<Int, Int>()

// 生成随机数字并存入HashMap
        for (i in list.indices) {
            val number = random.nextInt(100) + 1
            hashMap[number] = i
        }

// 从HashMap导入数组
        val robjs = hashMap.values.toIntArray()
        val r: ArrayList<*> = ArrayList<Any?>()

// 遍历数组并打印数据
        for (i in 0 until n) {
            r.add(list[robjs[i]] as Nothing)
            print(list[robjs[i]].toString() + "\t")
        }
        print("\n")
        return r
    }

}