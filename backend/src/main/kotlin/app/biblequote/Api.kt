package app.biblequote

import app.biblequote.utils.Verse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Api(private val abbrToBible: Map<String, Bible>) {

  /**
   * Список доступных переводов Библии приводится с общепринятыми английских аббревиатурами.
   * Первый в этом списке перевод, является переводом по умолчанию.
   */
  @GetMapping("/api/translations")
  fun translations(): List<String> {
    TODO()
  }

  /**
   * Список доступных книг для выбранного перевода
   */
  @GetMapping("/api/books")
  fun books(translation: String): List<String> {
    TODO()
  }

  /**
   * Список глав для выбранной книги определенного перевода
   */
  @GetMapping("/api/chapters")
  fun chapters(translation: String, book: String): Int {
    TODO()
  }

  /**
   * Список стихов для выбранной главы определенной книги и перевода
   */
  @GetMapping("/api/verses")
  fun verses(translation: String, book: String, chapter: Int): List<String> {
    TODO()
  }

  /**
   * Поиск по выбранному переводу
   */
  @GetMapping("/api/search")
  fun search(translation: String, query: String): List<Verse> {
    TODO()
  }
}