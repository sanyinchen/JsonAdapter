@file:JvmName("JsonAdapter")

package com.sanyinchen.json

import com.google.gson.Gson
import com.sanyinchen.json.curry.FunctionWrap
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Created by sanyinchen on 19-11-13.
 *
 * @author sanyinchen
 * @version v0.1
 * @since 19-11-13
 */

object JsonAdapter {


    fun <T> fromJson(
        content: String,
        classz: Class<T>,
        parserAdapter: Any? = null,
        ignoreException: Boolean = true
    ): T? {

        val moshiBuild = Moshi.Builder()
        moshiBuild.add(KotlinJsonAdapterFactory())
        if (parserAdapter != null) {
            moshiBuild.add(parserAdapter)
        }

        val parserChain = mutableListOf<FunctionWrap<T>>()

        parserChain.add { next ->
            {
                try {
                    val jsonAdapter = moshiBuild.build().adapter<T>(classz)
                    jsonAdapter.fromJson(content)
                } catch (ex: Exception) {
                    if (!ignoreException) {
                        ex.printStackTrace()
                    }
                    null
                } ?: next(it)
            }

        }

        return parserChain.asReversed().fold(
            { str: String ->
                try {
                    Gson().fromJson<T>(str, classz)
                } catch (ex: Exception) {
                    if (!ignoreException) {
                        ex.printStackTrace()
                    }
                    null
                }
            }, { next, functionWrap ->
                functionWrap(next)
            })(content)
    }


}