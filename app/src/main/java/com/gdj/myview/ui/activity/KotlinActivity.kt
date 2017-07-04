package com.gdj.myview.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.gdj.myview.R


/**
 *
 */
class KotlinActivity : AppCompatActivity() {
    //数组
    val empty = emptyArray<Int>()
    val arr = arrayOf(1, 2, 3)
    val ascStr3 = arrayOf("2", "3", "5")
    val intArr = intArrayOf(R.string.app_name, 2, 3)    //同理还有 booleanArrayOf() 等
    val ascStr = Array(2, { i -> "2" + i })
    val ascStr2 = Array(2, { "2" })
    val asc = Array(5, { i -> i * i })  //0,1,4,9,16

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        main(ascStr)
    }

    //空值检查和转换，可以返回空值的函数
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    fun parseSum(a: String, b: String): Unit {//返回一个没有意义的值，Unit的返回值类型可以省略， 可以使：Int返回值为int
        val x = parseInt(a)
        val y = parseInt(b)
        if (x != null && y != null) {//空值检测
            print(x * y)
        } else {
            print("either ''")
        }

        print("sum of $a and $b is${a + b}")//parseInt()
        Toast.makeText(this, "sum of $a and $b is${a + b}" + parseInt("5").toString(), Toast.LENGTH_SHORT).show()
    }

    fun main(args: Array<String>) {
        parseSum("2", "5")
        /**------常量------**/
        val a: Int = 1;//立即初始化

        val b: Int;//没有初始化值时必须声明类型
        b = 10;//赋值

        val c = 10;

        /**------变量------**/
        var x = 5;//推导出int类型
        x += 1;
        var y = x;
        y = +x;
        Log.w("2333", "a=$a,b=$b,c=$c,x=$x,y=$y  '${y + x}' ")
    }


    //使用is操作符检查一个表达式是否是某个类型的实例。
    // 如果对不可变的局部变量或者属性，进行过了类型检查，就没必要明确转换
    fun gettStringlength(obj: Any): Int? {
        if (obj is String && obj.length > 0) {
            //obj将会在这个分支中自动转换成String类型
            return obj.length; }
        //obj在种类检查完仍然是any类型
        return null;
    }

    fun mains(args: Array <String>) {
        fun printLenght(obj: Any) {}
    }

    fun maxof(a: Int, b: Int) = if (a > b) a else b


    //循环  for
    fun mainInFor(args: Array<String>) {
        val items = listOf("1", "2", "3")
        var index = 0;

        for (item in items) {
            print(item)
        }
        for (index in items.indices) {
            print("item at $index is ${items[index]}")
        }
    }

    //循环  while
    fun mainInWhile(args: Array<String>) {
        val items = listOf("1", "2", "3")
        var index = 0;
        while (index < items.size) {
            println("item an $index is ${items[index]}")
        }
    }

    //循环 when
    fun describe(orgs: Any): String =
            when (orgs) {
                1 -> "one"
                "hhh" -> "Greeting"
                is Long -> "long"
                !is String -> "not String"
                else -> "unknown"
            }

    //使用ranges  检查in操作符检查数值是否在某个范围内

    fun mainIn(args: Array<String>) {

        val x = 10;
        val y = 100;
        if (x in 1..y + 1) {
            print("fits in range")
        }
    }

    fun mainLL(args: Array<String>) {
        val list = listOf("a", "b")

        //查询数值是否在范围外
        if (-1 !in 0..list.lastIndex) {
        }
        if (list.size !in list.indices) {
        }
        //范围内迭代
        for (x in 1..5) {
        }
        //使用步进
        for (x in 1..10 step 2) {
        }
        for (x in 9 downTo 0 step 3) {
        }

    }

    var list = listOf<Int>(1, -1)

    var map = mapOf<String, String>("a" to "A", "b" to "B")

    //函数默认值
    fun fooo(a: Int = 0, b: String = "") {}


    //使用一个集合，对集合进行迭代
    fun mainList(args: Array<String>) {
        val items = listOf("1222", "2222", "222", "222")
        for (item in items) {
            parseInt(item)
        }
        //过滤list
        val positives1 = list.filter {
            x ->
            x > 0
        }
        val positives2 = list.filter {
            it > 0
        }
        //遍历map集合
        for ((k, v) in map) {
            println("$k -> $v")
        }
        var value: String=""
        println(map["a"])
     //   map["B"] = value

        when {
            "1212" in items -> println("aaaa")
        }
//        val p:String by lazy { 懒属性，延迟加载
//            //生成string的值
//        }

        //实例检查
//    when(x){
//        is Foo ->...
//    }
        //使用lambda表达式过滤和映射集合
        items.filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }

    }

    fun load(){

    }


}