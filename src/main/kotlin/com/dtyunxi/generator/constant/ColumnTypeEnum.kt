package com.dtyunxi.generator.constant

/**
 * @author luo.qianhong
 */

enum class ColumnTypeEnum(value: String) {
    NUMBER("number"),
    STRING("string"),
    BOOLEAN("boolean"),
    DATE("date"),
    DATETIME("datetime"),
    ENUM("enum");

    private val value: String = value.toLowerCase()

    override fun toString(): String {
        return value
    }
}
