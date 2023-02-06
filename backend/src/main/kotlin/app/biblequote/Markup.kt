package app.biblequote

import app.biblequote.dto.VerseRef
import app.biblequote.utils.MarkupChecker
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.URL

/**
 * Сущность для проверки перевода на соответствие указанной разбивке.
 * Проверяются названия книг, количество глав и стихов. Содержимое стихов не проверяется.
 * Это нужно для удобства последующей лемматизации
 * (чтобы было легче соотнести одни участки текста с аналогичными в других переводах).
 * Индексация, как и везде в Библии, начинается с единицы.
 * @param url путь к файлу с json-разметкой. Пример формата файла:
 *
 * ```json
 * {
 *   "Книга Примеров": [10, 4, 6],
 *   "Книга Примеров 2": [7, 8]
 * }
 * ```
 * Здесь описана разметка содержащая две книги,
 * где первая содержит три главы (по 10, 4 и 6 стихов соответственно),
 * а вторая две главы (по 7 и 8 стихов соответственно).
 */
class Markup(url: URL) : Iterable<VerseRef> {

  /**
   * Словарь ставит в соответствие названию книги список количества стихов для каждой главы.
   */
  private val booksToChaptersVerses = mutableMapOf<String, List<Int>>()

  init {
    val json = jacksonObjectMapper().readTree(url).fields()
    for ((book, verses) in json) {
      booksToChaptersVerses[book] = verses.map(JsonNode::asInt)
    }
  }

  /**
   * @return одноразовая сущность для итерируемой проверки разметки стихов
   */
  fun checker(): MarkupChecker {
    return MarkupChecker(iterator())
  }

  override fun iterator(): Iterator<VerseRef> {
    return iterator {
      for ((book, chapters) in booksToChaptersVerses) {
        for ((chapterIdx, versesCount) in chapters.withIndex()) {
          val chapterNumber = chapterIdx + 1
          for (verseNumber in 1..versesCount) {
            yield(VerseRef(book, chapterNumber, verseNumber))
          }
        }
      }
    }
  }
}