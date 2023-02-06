package app.biblequote

import app.biblequote.dto.Verse
import app.biblequote.exceptions.UnknownReferenceException
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Api(private val resolver: AbbrsResolver) {

  private val log = getLogger(javaClass)

  /**
   * Список доступных книг
   * @param abbr аббревиатура перевода Библии
   */
  @GetMapping("/api/books")
  fun books(abbr: String): List<String> {
    return resolver.translation(abbr).booksNames()
  }

  /**
   * Список глав для выбранной книги
   * @param abbr аббревиатура перевода Библии
   */
  @GetMapping("/api/chapters")
  fun chapters(abbr: String, book: String): Int {
    return resolver.translation(abbr).chaptersCount(book)
  }

  /**
   * Количество стихов для выбранной главы определенной книги
   * @param abbr аббревиатура перевода Библии
   */
  @GetMapping("/api/verses")
  fun verses(abbr: String, book: String, chapter: Int): Int {
    return resolver.translation(abbr).versesCount(book, chapter)
  }

  /**
   * Поиск по Библии (благодаря синхронной лемматизации,
   * все найденные ссылки должны быть применимы к любому переводу)
   * @param q запрос в произвольном виде
   */
  @GetMapping("/api/search")
  fun search(q: String): List<Verse> {
    TODO()
  }

  @ExceptionHandler(UnknownReferenceException::class)
  fun handle(e: UnknownReferenceException): ResponseEntity<String> {
    log.warn(e.message, e)
    return status(NOT_FOUND).body(e.message)
  }
}