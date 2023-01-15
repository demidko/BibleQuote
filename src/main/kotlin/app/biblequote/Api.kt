package app.biblequote

import app.biblequote.utils.Verse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.print.Book

@RestController
class Api() {

  /**
   * Список доступных переводов Библии приводится с общепринятыми английских аббревиатурами.
   * Первый в этом списке перевод, является переводом по умолчанию.
   */
  @GetMapping("/api/translations")
  fun translations(): List<String> {
    TODO()
  }

  @GetMapping("/api/books")
  fun books(translation: String): List<String> {
    TODO()
  }

  @GetMapping("/api/chapters")
  fun chapters(translation: String, book: String): UShort {
    TODO()
  }

  @GetMapping("/api/verses")
  fun verses(translation: String, book: String, chapter: UShort): List<String> {
    TODO()
  }

  @GetMapping("/api/search")
  fun search(translation: String, query: String): List<Verse> {
    TODO()
  }
}