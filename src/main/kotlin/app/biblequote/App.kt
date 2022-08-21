package app.biblequote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class App {
  @Bean
  fun bible(): Bible {
    val url = javaClass.getResource("/bible.html")!!
    return Bible(url)
  }
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
