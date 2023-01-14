package app.biblequote

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Api(private val translations: Map<String, Bible>) {

  /**
   * Список доступных переводов Библии приводится в общепринятых английских аббревиатурах.
   */
  @GetMapping("/api/translations")
  fun translations(): Set<String> {
    return translations.keys
  }
}