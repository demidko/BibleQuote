package app.biblequote

import app.biblequote.utils.HtmlBibleReader
import app.biblequote.utils.Verse
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
class Bible(markup: BibleMarkup, url: URL) {

  private val booksToChapters = mutableMapOf<String, MutableList<MutableList<String>>>()

  /**
   * @param markup разбивка стихов
   * @param resource формат текста ресурса _должен_ быть таким:
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
  constructor(markup: BibleMarkup, resource: String) : this(
    markup,
    Bible::class.java.getResource(resource)!!
  )

  init {
    val checker = markup.checker()
    val reader = url.openStream().bufferedReader().let(::HtmlBibleReader)
    reader.use {
      while (reader.hasNext) {
        val verse = reader.nextVerse()
        checker.checkNext(verse)
        cache(verse)
      }
    }
  }

  private fun cache(verse: Verse) {
    verse.apply {
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