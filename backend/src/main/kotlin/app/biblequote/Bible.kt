package app.biblequote

import app.biblequote.dto.Verse
import app.biblequote.exceptions.UnknownBookException
import app.biblequote.exceptions.UnknownChapterException
import app.biblequote.exceptions.UnknownVerseException
import app.biblequote.utils.HtmlBibleReader
import java.net.URL

/**
 * Библия с произвольным доступом к тексту по общепринятой нумерации глав и стихов.
 * @param markup разбивка стихов
 * @param url формат текста по ссылке _должен_ быть таким:
 * ```
 *  <h3>Книга Примеров</h3>
 *  <h4>1</h4>
 *  <p><sup>1</sup> собственно текст стиха 1
 *  <p><sup>2</sup> собственно текст стиха 2
 *  ...
 *  ...
 *  И так далее для всех последующих глав и стихов по порядку.
 * ```
 * В примере выше описана книга, содержащая одну главу с двумя стихами.
 * Тег `<sup>` может быть опущен. Главное — наличие номера стиха.
 * Текст может состоять из нескольких и более книг.
 * Главы и стихи в книгах следуют друг за другом в порядке возрастания.
 */
class Bible(markup: Markup, url: URL) {

  private val booksToChapters = mutableMapOf<String, MutableList<MutableList<String>>>()

  constructor(markup: Markup, resourceName: String) : this(
    markup,
    Bible::class.java.getResource(resourceName)!!
  )

  init {
    val reader = url.openStream().bufferedReader().let(::HtmlBibleReader)
    val checker = markup.checker()
    reader.use {
      while (reader.hasNext) {
        val v = reader.nextVerse()
        checker.checkNext(v)
        cache(v)
      }
    }
  }

  /**
   * Кеширует прочитанный стих в [booksToChapters]
   */
  private fun cache(verse: Verse) {
    verse.apply {
      val chapters = booksToChapters.computeIfAbsent(book) { mutableListOf() }
      if (number == 1) {
        chapters.add(mutableListOf(text))
      } else {
        chapters[chapter - 1].add(text)
      }
    }
  }

  private fun chapters(book: String): MutableList<MutableList<String>> {
    return booksToChapters[book] ?: throw UnknownBookException(book)
  }

  private fun verses(book: String, chapter: Int): MutableList<String> {
    val chapters = chapters(book)
    val chapterIdx = chapter - 1
    return chapters.getOrNull(chapterIdx) ?: throw UnknownChapterException(book, chapter)
  }

  /**
   * Список доступных книг по порядку
   */
  fun booksNames(): List<String> {
    return booksToChapters.keys.toList()
  }

  /**
   * Количество глав в книге
   * @param book название книги
   */
  fun chaptersCount(book: String): Int {
    return chapters(book).count()
  }

  /**
   * Количество стихов в главе книги
   * @param chapter порядковый номер главы, начиная с `1`
   */
  fun versesCount(book: String, chapter: Int): Int {
    return verses(book, chapter).count()
  }

  /**
   * Текст выбранного стиха
   * @param book название книги
   * @param chapter порядковый номер главы, начиная с `1`
   * @param verse порядковый номер стиха, начиная с `1`
   */
  fun verseText(book: String, chapter: Int, verse: Int): String {
    val verses = verses(book, chapter)
    val verseIdx = verse - 1
    return verses.getOrNull(verseIdx) ?: throw UnknownVerseException(book, chapter, verse)
  }
}