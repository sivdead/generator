package com.dtyunxi.generator.domain

import com.dtyunxi.generator.constant.RelationshipTypeEnum
import com.dtyunxi.generator.model.LinkField
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.io.IOException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Transient

/**
 * @author luo.qianhong
 */
@Entity
@Table(name = "`relationship`")
@SQLDelete(sql = "update module set dr = true where id = ?")
@Where(clause = "dr = false")
class Relationship : SuperEntity() {

    var moduleId: Long? = null

    var relatedTable: String? = null

    var relatedModule: String? = null

    var type: RelationshipTypeEnum? = null

    var name: String? = null

    @JsonIgnore
    @Column(columnDefinition = "json")
    var refField: String? = null

    @JsonIgnore
    @Column(columnDefinition = "json")
    var showField: String? = null

    @Transient
    @JsonProperty("refField")
    var refFieldJson: LinkField? = null
        set(value) {
            field = value
            try {
                this.refField = ObjectMapper().writeValueAsString(value)
            } catch (e: JsonProcessingException) {
                e.printStackTrace()
            }
        }
        get() {
            if (field == null && refField != null) {
                try {
                    field = ObjectMapper().readValue(refField, LinkField::class.java)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return field
        }

    @Transient
    @JsonProperty("showField")
    var showFieldJson: Array<LinkField>? = null
        get() {
            if (field == null && showField != null) {
                try {
                    field = ObjectMapper().readValue(showField, Array<LinkField>::class.java)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return field
        }
        set(value) {
            field = value
            try {
                this.showField = ObjectMapper().writeValueAsString(value)
            } catch (e: JsonProcessingException) {
                e.printStackTrace()
            }

        }
}
