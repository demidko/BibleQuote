package app.biblequote.utils

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
class Markup(url: URL) {

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

  fun contains(name: String): Boolean {
    return booksToChaptersVerses.containsKey(name)
  }

  fun contains(bookName: String, chapterNumber: UShort): Boolean {
    require(chapterNumber > 0u)
    val chapters = booksToChaptersVerses[bookName] ?: return false
    return chapterNumber <= chapters.size.toUShort()
  }

  fun contains(bookName: String, chapterNumber: UShort, verseNumber: UShort): Boolean {
    require(chapterNumber > 0u)
    require(verseNumber > 0u)
    val chapters = booksToChaptersVerses[bookName] ?: return false
    val chapterIdx = chapterNumber.toInt() - 1
    val verses = chapters.getOrNull(chapterIdx) ?: return false
    return verseNumber <= verses
  }

  fun isLast(bookName: String, chapterNumber: UShort, verseNumber: UShort): Boolean {
    require(contains(bookName, chapterNumber, verseNumber))
    val nextVerseNumber = StrictMath.addExact(verseNumber.toInt(), 1)

    return !contains(bookName, chapterNumber, verseNumber + 1u) && !contains(bookName, )
  }
}