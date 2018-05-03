package com.dtyunxi.generator.config

import com.dtyunxi.generator.generator.FreeMarkerCodeGenerator
import com.dtyunxi.generator.generator.ICodeGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author luo.qianhong
 */
@Configuration
class GeneratorConfiguration {

    @Bean
    fun codeGenerator(properties: GeneratorProperties): ICodeGenerator {
        return FreeMarkerCodeGenerator(properties)
    }
}
