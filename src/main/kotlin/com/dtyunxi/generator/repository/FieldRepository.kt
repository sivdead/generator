package com.dtyunxi.generator.repository


import com.dtyunxi.generator.domain.Field
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author luo.qianhong
 */
interface FieldRepository : JpaRepository<Field, Long> {

    /**
     * 根据id集合删除
     * @param ids ids
     */
    fun deleteByIdIn(ids: Collection<Long>)

    /**
     * 删除某个模块的所有字段
     * @param pid 模块主键
     * @return 删除结果
     */
    fun deleteByPid(pid: Long?): Boolean

    /**
     * 查找某个模块下的所有字段
     * @param pid 模块主键
     * @return 字段列表
     */
    fun findByPidOrderByRowno(pid: Long?): List<Field>
}
