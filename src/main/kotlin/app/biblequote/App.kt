package app.biblequote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class App {
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
