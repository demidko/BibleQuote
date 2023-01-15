package app.biblequote.utils

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.Closeable

/**
 * Класс для чтения Библии из html стих за стихом.
 * @param reader формат текста должен быть таким:
 * ```
 *  <h3>Книга (т. е. название)</h3>
 *  <h4>Глава (т. е. число)</h4>
 *  <p><sup>Номер стиха, например 1</sup> собственно текст стиха 1
 *  <p><sup>Номер стиха, например 2</sup> собственно текст стиха 2
 *  И так далее для всех последующих глав и стихов
 * ```
 * Текст может состоять из нескольких и более книг.
 * Главы и стихи в книгах следуют друг за другом в порядке возрастания.
 */
class HtmlBibleReader(private val reader: BufferedReader) : Closeable {

  private var bookName = "?"
  private var chapterNumber: UShort = 0u
  private var verseNumber: UShort = 0u
  private var currentBuffer = null as Document?

  init {
    reloadBuffer()
  }

  private fun reloadBuffer() {
    currentBuffer = when (val l = reader.readLine()) {
      null -> null
      else -> parse(l)
    }
  }

  val hasNext get() = currentBuffer != null

  private fun currentText() = currentBuffer!!.wholeText()

  private fun isCurrentTag(tag: String) = currentBuffer!!.body().firstElementChild()!!.tagName() == tag

  private val isNewBook get() = isCurrentTag("h3")

  private fun loadNewBook(): Verse {
    chapterNumber = 0u

    bookName = currentText()
    reloadBuffer()
    return loadNewChapter()
  }

  private val isNewChapter get() = isCurrentTag("h4")

  private fun loadNewChapter(): Verse {
    ++chapterNumber
    verseNumber = 0u
    reloadBuffer()
    return loadNewVerse()
  }

  private fun loadNewVerse(): Verse {
    ++verseNumber
    val verseNumberAsText = verseNumber.toString()
    val verseText = currentText()
    val verseNumberIdx = verseText.indexOf(verseNumberAsText)
    check(verseNumberIdx >= 0) {
      "Стих $bookName $chapterNumber:$verseNumber не найден в тексте: $verseText"
    }
    val pureTextIdx = verseNumberIdx + verseNumberAsText.length
    val pureText = verseText.substring(pureTextIdx).trim()
    check(pureText.isNotEmpty()) { "Стих $bookName $chapterNumber:$verseNumber отсутствует" }
    reloadBuffer()
    return Verse(bookName, chapterNumber, verseNumber, pureText)
  }

  fun nextVerse() = when {
    isNewBook -> loadNewBook()
    isNewChapter -> loadNewChapter()
    else -> loadNewVerse()
  }

  override fun close() {
    reader.close()
  }
}