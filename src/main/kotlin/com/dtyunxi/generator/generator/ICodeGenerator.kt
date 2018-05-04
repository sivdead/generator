package com.dtyunxi.generator.generator


import com.dtyunxi.generator.domain.Module
import org.apache.tools.zip.ZipOutputStream


/**
 * @author luo.qianhong
 */
interface ICodeGenerator {

    /**
     * 生成代码
     * @param module 模块
     * @param zip zip输出流
     */
    fun generateCode(module: Module, zip: ZipOutputStream)
}
