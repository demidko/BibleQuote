package app.biblequote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class App {

  @Bean
  fun textUrl() = javaClass.getResource("/bible.html")!!

  fun bible() = Bible(textUrl())
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
