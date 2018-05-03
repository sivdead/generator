package com.dtyunxi.generator.domain

import com.dtyunxi.generator.constant.UpdateMarkEnum
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*
import java.util.Date

@MappedSuperclass
open class SuperEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    var id: Long? = null

    /**
     * 创建时间
     */
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    var createTime = Date()

    /**
     * 创建人
     */
    @JsonIgnore
    var createBy: String? = null

    /**
     * 最后修改时间
     */
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    var updateTime: Date? = null

    /**
     * 最后修改人
     */
    @JsonIgnore
    var updateBy: String? = null

    /**
     * 删除标记 true: 删除，false: 未删除 默认未删除
     */
    @JsonIgnore
    @Column(columnDefinition = "boolean")
    var dr: Boolean? = false

    @Transient
    var mark = UpdateMarkEnum.NONE
}
