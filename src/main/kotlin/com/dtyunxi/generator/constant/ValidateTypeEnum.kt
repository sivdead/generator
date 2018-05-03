package com.dtyunxi.generator.constant

/**
 * @author luo.qianhong
 */

enum class ValidateTypeEnum private constructor(val value: Int) {

    /**
     * 无验证
     */
    NO_VALIDATE(0),
    EMAIL(1),
    MOBILE(2),
    PHONE(3),
    CREDIT(4),
    PATTERN(5),
    URL(6),
    IP(7),
    POSTCODE(8)
}