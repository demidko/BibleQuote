package app.biblequote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class App {

  @Bean
  fun russianSynodalTranslation(): Bible {
    return javaClass.getResource("/bible/rst.html").let(::Bible)
  }

  @Bean
  fun translations(): Map<String, Bible> {
    return mapOf(
      "rst" to russianSynodalTranslation()
    )
  }
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
