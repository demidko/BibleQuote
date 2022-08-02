package app.biblequote

import java.net.URL


@Suppress("MemberVisibilityCanBePrivate")
class Bible(url: URL) {

  /**
   * Каждой книге соответствует список глав, где каждая глава состоит из списка стихов.
   * Этот словарь хранит ключи в порядке поступления, что и позволяет сохранить также и номера книг.
   */
  private val booksToChapters = mutableMapOf<String, MutableList<MutableList<String>>>()

  init {
    val reader = url.openStream().bufferedReader().let(::BibleReader)
    reader.use {
      while (reader.hasNext) {
        reader.nextVerse().apply {
          val bookChapters = booksToChapters.computeIfAbsent(bookName) { mutableListOf() }
          if (verseNumber == 1) {
            // если это первый стих, значит предыдущих глав должно быть на одну меньше
            check(bookChapters.size == chapterNumber - 1)
            bookChapters.add(mutableListOf(verseText))
          } else {
            // если это не первый стих, значит глава уже должна быть в списке
            check(bookChapters.size == chapterNumber)
            val lastChapter = bookChapters[chapterNumber - 1]
            // и при этом предыдущих стихов в ней должно быть на один меньше
            check(lastChapter.size == verseNumber - 1)
            lastChapter.add(verseText)
          }
        }
      }
    }
  }

  val booksNames get() = booksToChapters.keys.toSet()

  val booksCount get() = booksToChapters.size

  /**
   * @param number порядковый номер книги, начиная с `1`
   */
  fun bookName(number: Int) = booksToChapters.keys.elementAt(number - 1)

  /**
   * @return  порядковый номер книги, начиная с `1`
   */
  fun bookNumber(name: String) = booksToChapters.keys.indexOf(name) + 1

  fun chaptersCount(bookName: String) = booksToChapters[bookName]!!.size

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   */
  fun chaptersCount(bookNumber: Int) = chaptersCount(bookName(bookNumber))

  /**
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun versesCount(bookName: String, chapterNumber: Int) = booksToChapters[bookName]!![chapterNumber - 1].size

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun versesCount(bookNumber: Int, chapterNumber: Int) = versesCount(bookName(bookNumber), chapterNumber)

  /**
   * @param chapterNumber порядковый номер главы, начиная с `1`
   * @param verseNumber порядковый номер стиха, начиная с `1`
   */
  fun verseText(bookName: String, chapterNumber: Int, verseNumber: Int): String {
    return booksToChapters[bookName]!![chapterNumber - 1][verseNumber - 1]
  }

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   * @param chapterNumber порядковый номер главы, начиная с `1`
   * @param verseNumber порядковый номер стиха, начиная с `1`
   */
  fun verseText(bookNumber: Int, chapterNumber: Int, verseNumber: Int): String {
    return verseText(bookName(bookNumber), chapterNumber, verseNumber)
  }

  /**
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun chapterText(bookName: String, chapterNumber: Int) = buildString {
    for (verseNumber in 1..versesCount(bookName, chapterNumber)) {
      append(verseText(bookName, chapterNumber, verseNumber))
    }
  }

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun chapterText(bookNumber: Int, chapterNumber: Int) = chapterText(bookName(bookNumber), chapterNumber)
}