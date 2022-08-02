package app.biblequote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class App {

  @Bean
  fun sourceUrl() = javaClass.getResource("/bible.html")!!

  @Bean
  fun randomAccessBible() = RandomAccessBible(sourceUrl())

  @Bean
  fun bibleSearch() = BibleSearch(randomAccessBible())
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
