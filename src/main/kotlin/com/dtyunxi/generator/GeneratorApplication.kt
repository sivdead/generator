package com.dtyunxi.generator

import com.dtyunxi.generator.config.GeneratorProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(GeneratorProperties::class)
class GeneratorApplication

fun main(args: Array<String>) {
    runApplication<GeneratorApplication>(*args)
}
