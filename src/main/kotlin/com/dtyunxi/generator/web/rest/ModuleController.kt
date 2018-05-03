package com.dtyunxi.generator.web.rest

import com.dtyunxi.generator.domain.Module
import com.dtyunxi.generator.generator.ICodeGenerator
import com.dtyunxi.generator.model.R
import com.dtyunxi.generator.repository.FieldRepository
import com.dtyunxi.generator.repository.ModuleRepository
import com.dtyunxi.generator.repository.RelationshipRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.constraints.NotNull

/**
 * @author luo.qianhong
 */
@RestController
@RequestMapping("/modules")
class ModuleController @Autowired
constructor(private val moduleRepository: ModuleRepository,
            private val fieldRepository: FieldRepository,
            private val codeGenerator: ICodeGenerator,
            private val relationshipRepository: RelationshipRepository) {

    @GetMapping
    fun list(@RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int,
             @RequestParam(defaultValue = "admin") username: String,
             @RequestParam(defaultValue = "") keyword: String): R<Page<Module>> {
        return R(moduleRepository.findByNameLikeAndCreateBy(keyword, username, PageRequest.of(page, size)))
    }

    @GetMapping("/{id}")
    fun findDetail(@NotNull @PathVariable("id") id: Long): R<*> {
        val optional = moduleRepository.findById(id)
        if (optional.isPresent) {
            val module = optional.get()
            val list = fieldRepository.findByPidOrderByRowno(module.id)
            module.fields = list

            val relationships = relationshipRepository.findAllByModuleId(module.id)
            module.relationships = relationships

            return R(module)
        }
        return R.fail("找不到记录")
    }

    @PostMapping
    fun create(@RequestBody module: Module,
               @RequestParam(defaultValue = "admin") username: String): R<Module> {
        module.createBy = username
        module.createTime = Date()
        moduleRepository.save(module)

        return R(module)
    }
}
