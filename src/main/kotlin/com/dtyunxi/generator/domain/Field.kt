package com.dtyunxi.generator.domain

import com.dtyunxi.generator.constant.ColumnTypeEnum
import com.dtyunxi.generator.constant.ValidateTypeEnum
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.ArrayUtils
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Transient

/**
 * @author luo.qianhong
 */
@Table(name = "`field`")
@Entity
@SQLDelete(sql = "update field set dr = true where id = ?")
@Where(clause = "dr = false")
class Field : SuperEntity() {

    /**
     * 实体id
     */

    var pid: Long? = null

    /**
     * 名称
     */
    var name: String? = null

    /**
     * 字段类型
     */
    var type: ColumnTypeEnum? = null

    /**
     * 是否为主键
     */
    @Column(columnDefinition = "boolean")
    var pk: Boolean? = false

    /**
     * 枚举值
     */
    @JsonIgnore
    @Column(columnDefinition = "json")
    var enumValues: String? = null

    /**
     * 是否支持多语
     */
    @Column(columnDefinition = "boolean")
    var multiLanguage: Boolean = false

    /**
     * 校验类型
     */
    @JsonIgnore
    var validateType = ValidateTypeEnum.NO_VALIDATE

    /**
     * 校验正则
     */
    var validatePattern: String? = null

    /**
     * 校验信息
     */
    @Column(columnDefinition = "json")
    @JsonIgnore
    var validateMessage: String? = null

    /**
     * 标题 json
     */
    @Column(columnDefinition = "json")
    @JsonIgnore
    var title: String? = null

    /**
     * 是否可为空
     */
    @Column(columnDefinition = "boolean")
    var nullable: Boolean? = true

    /**
     * 是否展示
     */
    @Column(columnDefinition = "boolean")
    var displayable: Boolean? = true

    /**
     * 可否编辑
     */
    @Column(columnDefinition = "boolean")
    var editable: Boolean? = true

    /**
     * 是否查询条件
     */
    @Column(columnDefinition = "boolean")
    var queriable: Boolean? = true

    /**
     * 行号
     */
    var rowno: Int? = null

    @Transient
    @JsonProperty("title")
    var titleJson: Map<String, Any>? = null
        get() {
            if (field == null && title != null) {
                field = JSONObject(title).toMap()
            }
            return field
        }
        set(value) {
            field = value
            this.title = JSONObject(value).toString()
        }

    @Transient
    @JsonProperty("validateMessage")
    var validateMessageJson: Map<String, Any>? = null
        get() {
            if (field == null && validateMessage != null) {
                field = JSONObject(validateMessage).toMap()
            }
            return field
        }
        set(value) {
            field = value
            this.validateMessage = JSONObject(value).toString()
        }

    @Transient
    @JsonProperty("enumValues")
    var enumValuesJson: Array<EnumValue>? = null
        get() {
            if (field == null && enumValues != null) {
                try {
                    field = ObjectMapper().readValue(enumValues, Array<EnumValue>::class.java)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return field
        }
        set(value) {
            field = value
            if (ArrayUtils.isNotEmpty(field)) {
                this.enumValues = JSONArray(floatArrayOf()).toString()
            }
        }

    class EnumValue {
        var display: String? = null
        var value: String? = null
    }
}
