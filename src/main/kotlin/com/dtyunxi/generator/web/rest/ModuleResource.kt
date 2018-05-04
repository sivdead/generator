package com.dtyunxi.generator.web.rest

import com.dtyunxi.generator.constant.UpdateMarkEnum.*
import com.dtyunxi.generator.domain.Field
import com.dtyunxi.generator.domain.Module
import com.dtyunxi.generator.domain.Relationship
import com.dtyunxi.generator.generator.ICodeGenerator
import com.dtyunxi.generator.model.R
import com.dtyunxi.generator.repository.FieldRepository
import com.dtyunxi.generator.repository.ModuleRepository
import com.dtyunxi.generator.repository.RelationshipRepository
import org.apache.commons.lang3.StringUtils
import org.apache.tools.zip.ZipOutputStream
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.NotNull

/**
 * @author luo.qianhong
 */
@Controller
@RequestMapping("/modules")
class ModuleResource @Autowired
constructor(private val moduleRepository: ModuleRepository,
            private val fieldRepository: FieldRepository,
            private val codeGenerator: ICodeGenerator,
            private val relationshipRepository: RelationshipRepository,
            private val generator: ICodeGenerator) {

    @GetMapping
    @ResponseBody
    fun list(@RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int,
             @RequestParam(defaultValue = "admin") username: String,
             @RequestParam(defaultValue = "") keyword: String): ResponseEntity<Page<Module>> {
        return if (StringUtils.isNotEmpty(keyword)) {
            ResponseEntity.ok(moduleRepository.findByNameLikeAndCreateBy(keyword, username, PageRequest.of(page, size)))
        } else {
            ResponseEntity.ok(moduleRepository.findAllByCreateBy(username, PageRequest.of(page, size)))
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun findDetail(@NotNull @PathVariable("id") id: Long): ResponseEntity<*> {
        val optional = moduleRepository.findById(id)
        if (optional.isPresent) {
            val module = optional.get()
            val list = fieldRepository.findByPidOrderByRowno(module.id)
            module.fields = list

            val relationships = relationshipRepository.findAllByModuleId(module.id)
            module.relationships = relationships

            return ResponseEntity.ok(module)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到指定的模块")
    }

    @PostMapping
    @ResponseBody
    fun create(@RequestBody module: Module,
               @RequestParam(defaultValue = "admin") username: String): ResponseEntity<Module> {
        module.createBy = username
        module.createTime = Date()
        moduleRepository.save(module)

        return ResponseEntity.ok(module)
    }

    @PutMapping("/{id}")
    @ResponseBody
    fun update(@NotNull @PathVariable("id") id: Long,
               @RequestBody module: Module,
               @RequestParam(defaultValue = "admin") username: String): ResponseEntity<*> {

        val optional = moduleRepository.findById(id)
        if (!optional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到指定的模块")
        }

        module.updateBy = username
        module.updateTime = Date()
        module.id = id
        moduleRepository.save(module)

        //更新字段
        val deleteList = mutableListOf<Field>()
        var saveList = mutableListOf<Field>()

        module.fields.forEach {
            when (it.mark) {
                ADD -> {
                    it.pid = module.id
                    it.createBy = username
                    it.createTime = Date()
                    saveList.add(it)
                }
                DELETE -> deleteList.add(it)
                UPDATE -> {
                    it.updateBy = username
                    it.updateTime = Date()
                    saveList.add(it)
                }
                else -> {
                }
            }
        }
        saveList = fieldRepository.saveAll(saveList)
        if (!deleteList.isEmpty()) {
            fieldRepository.deleteAll(deleteList)
        }

        /*
          更新关系
         */
        var updates = mutableListOf<Relationship>()
        val deletes = mutableListOf<Relationship>()
        module.relationships.forEach {
            when (it.mark) {
                ADD -> {
                    it.moduleId = module.id
                    it.createBy = username
                    it.createTime = Date()
                    updates.add(it)
                }
                DELETE -> deletes.add(it)
                UPDATE -> {
                    it.moduleId = module.id
                    it.updateBy = username
                    it.updateTime = Date()
                    updates.add(it)
                }
                else -> {
                }
            }
        }
        if (!deletes.isEmpty()) {
            relationshipRepository.deleteAll(deletes)
        }
        updates = relationshipRepository.saveAll(updates)

        moduleRepository.save(module)
        module.fields = saveList
        module.relationships = updates
        return ResponseEntity.ok(module)
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    fun delete(@PathVariable("id") @NotNull id: Long): R<Boolean> {
        moduleRepository.deleteById(id)
        fieldRepository.deleteByPid(id)
        return R(true)
    }

    @GetMapping("/{id}/export")
    fun export(@PathVariable("id") @NotNull id: Long, resp: HttpServletResponse) {
        val module = moduleRepository.findById(id)
        if (module.isPresent) {
            resp.reset()
            val zip = ZipOutputStream(resp.outputStream)
            zip.encoding = System.getProperty("sun.jnu.encoding")
            resp.setHeader("Content-Disposition", "attachment; filename=\"${URLEncoder.encode(module.get().name,"UTF-8")}.zip\"")
            resp.contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
            codeGenerator.generateCode(module.get(), zip)
        } else {
            resp.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
            resp.writer.write(JSONObject(ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到指定的模块")).toString())
        }
    }
}
