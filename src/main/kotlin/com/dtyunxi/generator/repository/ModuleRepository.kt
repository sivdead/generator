package com.dtyunxi.generator.repository

import com.dtyunxi.generator.domain.Module
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author luo.qianhong
 */
interface ModuleRepository : JpaRepository<Module, Long> {

    /**
     * 根据id集合删除
     * @param id ids
     * @return 删除结果
     */
    fun deleteByIdIn(id: Collection<Long>): Boolean

    /**
     * 查询
     * @param name 名称
     * @param createBy 创建人
     * @param pageable 分页
     * @return 查询数据
     */
    fun findByNameLikeAndCreateBy(name: String, createBy: String, pageable: Pageable): Page<Module>

    /**
     * 分页查询
     * @param createBy 创建人
     * @param pageable 分页
     * @return 查询数据
     */
    fun findAllByCreateBy(createBy: String, pageable: Pageable): Page<Module>

}
