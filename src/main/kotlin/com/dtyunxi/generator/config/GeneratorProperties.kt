package com.dtyunxi.generator.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author luo.qianhong
 */
@ConfigurationProperties("api.generator")
class GeneratorProperties {

    /**
     * 模板位置
     */
    var templateLocation = "templates/codeTemplate"

    /**
     * 模板与生成代码对应路径
     */
    var fileMap: Map<String, String>? = HashMap()

    /**
     * 文件系统资源加载类
     */
    var fileResourceLoaderClass: Class<*>? = null

    var classResourceLoaderClass: Class<*>? = null

    /**
     * 宏位置
     */
    var velocityLibrary: String? = null

    /**
     * 资源加载方式: file/class
     */
    var resourceLoader = RESOURCE_LOADER_CLASS

    companion object {

        /**
         * 模板加载方式:类路径
         *
         */
        const val RESOURCE_LOADER_CLASS = "class"

        /**
         * 模板加载方式:文件
         */
        const val RESOURCE_LOADER_FILE = "file"
    }

}
