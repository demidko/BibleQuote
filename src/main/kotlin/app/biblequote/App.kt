package app.biblequote

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootConfiguration
@EnableAutoConfiguration
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class App {

  @Bean
  fun russianSynodalTranslation(): Bible {
    return javaClass.getResource("/bible/rst.html").let(::Bible)
  }

  @Suppress("SpellCheckingInspection")
  @Bean
  fun ukrainianBibleByIlarionOhienko(): Bible {
    return javaClass.getResource("/bible/ubio.html").let(::Bible)
  }

  @Bean
  fun translations(): Map<String, Bible> {
    return mapOf(
      "rst" to russianSynodalTranslation(),
      "ubio" to ukrainianBibleByIlarionOhienko()
    )
  }

  @Bean
  fun api(): Api {
    return Api(translations())
  }
}


fun main(args: Array<String>) {
  runApplication<App>(*args)
}
