package app.biblequote.markup

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.URL

/**
 * Объект для проверки перевода на соответствие указанной разбивке.
 * Проверяются названия книг, количество глав и стихов. Содержимое стихов не проверяется.
 * Это нужно для удобства последующей лемматизации (чтобы было легче соотнести одни участки текста с аналогичными в других переводах).
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
class BibleMarkup(url: URL) {

  /**
   * Словарь ставит в соответствие названию книги список количества стихов для каждой главы.
   */
  private val booksToChaptersVerses = mutableMapOf<String, List<UShort>>()

  init {
    val json = jacksonObjectMapper().readTree(url).fields()
    for ((book, verses) in json) {
      booksToChaptersVerses[book] = verses.map(JsonNode::asInt).map(Int::toUShort)
    }
  }

  /**
   * @return одноразовая сущность для потоковой проверки разметки
   */
  fun checker(): MarkupChecker {
    return MarkupChecker(iterator())
  }

  private fun iterator(): Iterator<VerseRef> {
    return iterator {
      for ((book, chapters) in booksToChaptersVerses) {
        for ((chapterIdx, versesCount) in chapters.withIndex()) {
          val chapterNumber = chapterIdx.plus(1).toUShort()
          val versesNumbers = 1.toUShort().rangeTo(versesCount).map(UInt::toUShort)
          for (verseNumber in versesNumbers) {
            yield(VerseRef(book, chapterNumber, verseNumber))
          }
        }
      }
    }
  }
}