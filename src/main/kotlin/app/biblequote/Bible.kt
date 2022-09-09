package app.biblequote

import app.biblequote.utils.HtmlBibleReader
import app.biblequote.utils.Verse
import java.net.URL
import java.util.zip.GZIPInputStream

/**
 * Библия с произвольным доступом к тексту по общепринятой нумерации глав и стихов.
 * @param url адрес по которому доступен текст Библии в HTML пожатом в GZIP. Структура должна быть такой:
 * ```
 *  <h3>Книга</h3>
 *  <h4>Номер главы</h4>
 *  <p><sup>1</sup> собственно текст стиха 1
 *  <p><sup>2</sup> собственно текст стиха 2
 *  И так далее для всех последующих глав и стихов
 * ```
 * Текст может состоять из нескольких и более книг.
 * Главы и стихи в книгах следуют друг за другом в порядке возрастания.
 */
class Bible(url: URL) {

  private val booksToChapters = mutableMapOf<String, MutableList<MutableList<String>>>()

  init {
    val reader =
      url.openStream()!!
        .let(::GZIPInputStream)
        .bufferedReader()
        .let(::HtmlBibleReader)
    reader.use {
      while (reader.hasNext) {
        add(reader.nextVerse())
      }
    }
  }

  /**
   * Добавить следующий по порядку стих. В случае нарушения порядка, произойдет исключение.
   */
  private fun add(verse: Verse) {
    val book = verse.book
    val chapter = verse.chapter.toInt()
    val number = verse.number.toInt()
    val text = verse.text
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
  fun versesCount(book: String, chapter: Int): Int {
    val chapterIdx = chapter - 1
    return booksToChapters[book]!![chapterIdx].size
  }

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
    val chapterIdx = chapter - 1
    val verseIdx = verse - 1
    return booksToChapters[book]!![chapterIdx][verseIdx]
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
    val versesCount = versesCount(bookName, chapterNumber)
    for (verseNumber in 1..versesCount) {
      append(text(bookName, chapterNumber, verseNumber))
    }
  }

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun text(bookNumber: Int, chapterNumber: Int) = text(nameOf(bookNumber), chapterNumber)
}