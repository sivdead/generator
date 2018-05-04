package com.dtyunxi.generator.domain


import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.json.JSONArray

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Transient

/**
 * 实体表
 * @author luo.qianhong
 */
@Entity
@Table(name = "`module`")
@SQLDelete(sql = "update module set dr = true where id = ?")
@Where(clause = "dr = false")
class Module : SuperEntity() {

    /**
     * 名称
     */
    var name: String? = null

    /**
     * 描述
     */
    @Column(name = "`desc`")
    var desc: String? = null

    /**
     * 表名
     */
    var tableName: String? = null

    /**
     * 包路径
     */
    var basePackage: String? = null

    /**
     * 支持语言
     */
    @Column(columnDefinition = "json")
    @JsonIgnore
    var locales: String? = null

    @Transient
    var fields: List<Field> = ArrayList()

    @Transient
    var relationships: List<Relationship> = ArrayList()

    @Transient
    @JsonProperty("locales")
    var localesJson: List<String>? = null
        get() {
            if (field == null && locales != null) {
                field = JSONArray(locales).toList() as List<String>?
            }
            return field
        }
        set(value) {
            field = value
            this.locales = JSONArray(value).toString()
        }
}
