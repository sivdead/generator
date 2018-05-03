package com.dtyunxi.generator.generator

import com.dtyunxi.generator.config.GeneratorProperties
import org.apache.commons.lang3.StringUtils
import org.apache.commons.text.WordUtils
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver

import java.io.IOException
import java.util.ArrayList

/**
 * @author luo.qianhong
 */
abstract class AbstractCodeGenerator(var properties: GeneratorProperties) : ICodeGenerator {

    protected val templates: List<String?>
        get() {
            val list = ArrayList<String?>()
            try {
                val resolver = PathMatchingResourcePatternResolver()
                val resources = resolver.getResources("classpath:" + properties.templateLocation + "/*.vm")
                for (resource in resources) {
                    list.add(resource.filename)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException("读取模板文件出错")
            }

            return list
        }

    /**
     * 获取文件名
     */
    protected fun getFileName(template: String, className: String, basePackage: String): String? {

        val packagePath = basePackage.replace(".", "/")
        val filepath = properties.fileMap!![template]
        return filepath?.replace("#{className}", className)?.replace("#{packagePath}", packagePath)

    }

    /**
     * 列名转换成Java属性名
     */
    protected fun columnToJava(columnName: String): String {
        var columnName = columnName
        if (columnName.contains("_")) {
            columnName = WordUtils.capitalizeFully(columnName, '_').replace("_", "")
        }
        columnName = WordUtils.uncapitalize(columnName)
        return columnName
    }

    /**
     * 表名转换成Java类名
     */
    protected fun tableToJava(tableName: String, tablePrefix: String): String {
        var tableName = tableName
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "")
        }
        return WordUtils.capitalizeFully(tableName, '_').replace("_", "")
    }

}
