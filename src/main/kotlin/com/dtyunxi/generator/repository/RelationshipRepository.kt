package com.dtyunxi.generator.repository

import com.dtyunxi.generator.domain.Relationship
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author luo.qianhong
 */
interface RelationshipRepository : JpaRepository<Relationship, Long> {

    /**
     * 查找模块下的所有关系
     * @param moduleId 模块id
     * @return 关系集合
     */
    fun findAllByModuleId(moduleId: Long?): List<Relationship>

    /**
     * 根据id集合删除
     * @param ids ids
     * @return 删除结果
     */
    fun deleteByIdIn(ids: Collection<Long>)

    /**
     * 删除某个模块的所有关系
     * @param moduleId 模块主键
     * @return 删除结果
     */
    fun deleteByModuleId(moduleId: Long?): Boolean
}
