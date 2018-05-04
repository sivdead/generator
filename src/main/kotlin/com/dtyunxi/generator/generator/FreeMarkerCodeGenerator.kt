package com.dtyunxi.generator.generator

import com.dtyunxi.generator.config.GeneratorProperties
import com.dtyunxi.generator.domain.Module
import org.apache.tools.zip.ZipEntry
import org.apache.tools.zip.ZipOutputStream

class FreeMarkerCodeGenerator(properties: GeneratorProperties) : AbstractCodeGenerator(properties) {
    override fun generateCode(module: Module, zip: ZipOutputStream) {
        zip.putNextEntry(ZipEntry("test.java"))
        zip.write("helloworld".toByteArray())
        zip.closeEntry()
        zip.flush()
        zip.finish()
    }

}