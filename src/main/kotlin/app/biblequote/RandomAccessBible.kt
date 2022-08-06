package app.biblequote

import org.slf4j.LoggerFactory.getLogger
import java.net.URL


@Suppress("MemberVisibilityCanBePrivate")
class RandomAccessBible(url: URL) {

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
          val bookChapters = booksToChapters.computeIfAbsent(book) { mutableListOf() }
          if (number == 1) {
            // если это первый стих, значит предыдущих глав должно быть на одну меньше
            check(bookChapters.size == chapter - 1)
            bookChapters.add(mutableListOf(text))
          } else {
            // если это не первый стих, значит глава уже должна быть в списке
            check(bookChapters.size == chapter)
            val lastChapter = bookChapters[chapter - 1]
            // и при этом предыдущих стихов в ней должно быть на один меньше
            check(lastChapter.size == number - 1)
            lastChapter.add(text)
          }
        }
      }
    }
    getLogger(javaClass.simpleName).info("all books loaded.")
  }

  val booksNames get() = booksToChapters.keys.toSet()

  val booksCount get() = booksToChapters.size

  /**
   * @param book порядковый номер книги, начиная с `1`
   */
  fun nameOf(book: Int) = booksToChapters.keys.elementAt(book - 1)

  /**
   * @return порядковый номер книги, начиная с `1`
   */
  fun numberOf(book: String): Int {
    val idx = booksToChapters.keys.indexOf(book)
    check(idx >= 0) {
      "Не найдена книга '$book'"
    }
    return idx + 1
  }

  /**
   * @param book название книги
   */
  fun chaptersCount(book: String) = booksToChapters[book]!!.size

  /**
   * @param book порядковый номер книги, начиная с `1`
   */
  fun chaptersCount(book: Int) = chaptersCount(nameOf(book))

  /**
   * @param chapter порядковый номер главы, начиная с `1`
   */
  fun versesCount(book: String, chapter: Int) = booksToChapters[book]!![chapter - 1].size

  /**
   * @param book порядковый номер книги, начиная с `1`
   * @param chapter порядковый номер главы, начиная с `1`
   */
  fun versesCount(book: Int, chapter: Int) = versesCount(nameOf(book), chapter)

  /**
   * @param chapter порядковый номер главы, начиная с `1`
   * @param verse порядковый номер стиха, начиная с `1`
   */
  fun text(book: String, chapter: Int, verse: Int): String {
    return booksToChapters[book]!![chapter - 1][verse - 1]
  }

  /**
   * @param book порядковый номер книги, начиная с `1`
   * @param chapter порядковый номер главы, начиная с `1`
   * @param verse порядковый номер стиха, начиная с `1`
   */
  fun text(book: Int, chapter: Int, verse: Int): String {
    return text(nameOf(book), chapter, verse)
  }

  /**
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun text(bookName: String, chapterNumber: Int) = buildString {
    for (verseNumber in 1..versesCount(bookName, chapterNumber)) {
      append(text(bookName, chapterNumber, verseNumber))
    }
  }

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun text(bookNumber: Int, chapterNumber: Int) = text(nameOf(bookNumber), chapterNumber)
}