package app.biblequote

import app.biblequote.io.HtmlBibleReader
import app.biblequote.markup.BibleMarkup
import app.biblequote.markup.MarkupChecker
import java.net.URL

/**
 * Библия с произвольным доступом к тексту по общепринятой нумерации глав и стихов.
 * @param url ресурс по ссылке должен удовлетворять требованиям [HtmlBibleReader]
 * @param markup разметка для проверки
 */
class Bible(url: URL, markup: BibleMarkup) {

  companion object {

    /**
     * @param name ресурс должен удовлетворять требованиям [HtmlBibleReader]
     * @param markup разметка для проверки
     */
    fun Bible(name: String, markup: Markup): Bible {
      val bufferedReader = Bible::class.java.getResourceAsStream(name)!!.bufferedReader()
      val htmlReader = HtmlBibleReader(bufferedReader, markup)
      htmlReader.use {
        return Bible(htmlReader)
      }
    }
  }

  private val booksToChapters = mutableMapOf<String, MutableList<MutableList<String>>>()

  init {
    while (reader.hasNext) {
      val verse = reader.nextVerse()
      val book = verse.book
      val chapter = verse.chapter.toInt()
      val number = verse.number.toInt()
      val text = verse.text
      val bookChapters = booksToChapters.computeIfAbsent(book) { mutableListOf() }
      if (number == 1) {
        // если это первый стих, значит предыдущих глав должно быть на одну меньше
        bookChapters.add(mutableListOf(text))
      } else {
        // если это не первый стих, значит глава уже должна быть в списке
        // и при этом предыдущих стихов в ней должно быть на один меньше
        bookChapters[chapter - 1].add(text)
      }
    }
  }

  val booksNames get() = booksToChapters.keys.toList()

  /**
   * @param book название книги
   */
  fun chaptersCount(book: String) = booksToChapters[book]!!.size

  /**
   * @param chapter порядковый номер главы, начиная с `1`
   */
  fun versesCount(book: String, chapter: Int): Int {
    val chapterIdx = chapter - 1
    return booksToChapters[book]!![chapterIdx].size
  }

  /**
   * @param chapter порядковый номер главы, начиная с `1`
   * @param verse порядковый номер стиха, начиная с `1`
   */
  fun text(book: String, chapter: Int, verse: Int): String {
    val chapterIdx = chapter - 1
    val verseIdx = verse - 1
    return booksToChapters[book]!![chapterIdx][verseIdx]
  }
}