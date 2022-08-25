package app.biblequote

import app.biblequote.utils.BibleReader

/**
 * Русская синодальная библия с произвольным доступом к тексту по общепринятой нумерации глав и стихов.
 */
object Bible {

  private val booksToChapters = mutableMapOf<String, MutableList<MutableList<String>>>()

  init {
    val reader = javaClass.getResourceAsStream("/bible.html")!!.bufferedReader().let(::BibleReader)
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

  val booksCount get() = booksToChapters.size.toUShort()

  /**
   * @param book порядковый номер книги, начиная с `1`
   */
  fun nameOf(book: UShort) = booksToChapters.keys.elementAt(book.toInt() - 1)

  /**
   * @return порядковый номер книги, начиная с `1`
   */
  fun numberOf(book: String): UShort {
    val idx = booksToChapters.keys.indexOf(book)
    check(idx >= 0) {
      "Не найдена книга '$book'"
    }
    return (idx + 1).toUShort()
  }

  /**
   * @param book название книги
   */
  fun chaptersCount(book: String) = booksToChapters[book]!!.size.toUShort()

  /**
   * @param book порядковый номер книги, начиная с `1`
   */
  fun chaptersCount(book: UShort) = chaptersCount(nameOf(book))

  /**
   * @param chapter порядковый номер главы, начиная с `1`
   */
  fun versesCount(book: String, chapter: UShort): UShort {
    val chapterIdx = (chapter - 1u).toInt()
    return booksToChapters[book]!![chapterIdx].size.toUShort()
  }

  /**
   * @param book порядковый номер книги, начиная с `1`
   * @param chapter порядковый номер главы, начиная с `1`
   */
  fun versesCount(book: UShort, chapter: UShort) = versesCount(nameOf(book), chapter)

  /**
   * @param chapter порядковый номер главы, начиная с `1`
   * @param verse порядковый номер стиха, начиная с `1`
   */
  fun text(book: String, chapter: UShort, verse: UShort): String {
    val chapterIdx = (chapter - 1u).toInt()
    val verseIdx = (verse - 1u).toInt()
    return booksToChapters[book]!![chapterIdx][verseIdx]
  }

  /**
   * @param book порядковый номер книги, начиная с `1`
   * @param chapter порядковый номер главы, начиная с `1`
   * @param verse порядковый номер стиха, начиная с `1`
   */
  fun text(book: UShort, chapter: UShort, verse: UShort): String {
    return text(nameOf(book), chapter, verse)
  }

  /**
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun text(bookName: String, chapterNumber: UShort) = buildString {
    val versesCount = versesCount(bookName, chapterNumber).toInt()
    for (verseIntNumber in 1..versesCount) {
      val verseNumber = verseIntNumber.toUShort()
      append(text(bookName, chapterNumber, verseNumber))
    }
  }

  /**
   * @param bookNumber порядковый номер книги, начиная с `1`
   * @param chapterNumber порядковый номер главы, начиная с `1`
   */
  fun text(bookNumber: UShort, chapterNumber: UShort) = text(nameOf(bookNumber), chapterNumber)
}