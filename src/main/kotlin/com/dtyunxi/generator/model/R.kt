package com.dtyunxi.generator.model

import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject

/**
 * @author luo.qianhong
 */
class R<T>() {

    var code: Int? = null

    var msg: String? = null

    var payload: T? = null

    init {
        this.code = 0
        this.msg = "ok"
    }

    constructor(payload: T) : this() {
        this.payload = payload
    }

    override fun toString(): String {
        return JSONObject(this).toString()
    }

    companion object {

        fun fail(msg: String): R<*> {
            val r = R(null)
            r.code = 1
            r.msg = msg
            return r
        }
    }
}
