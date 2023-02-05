package app.biblequote.io

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.BufferedReader
import java.io.Closeable

/**
 * Класс для потокового чтения Библии из html стих за стихом.
 * @param reader формат текста _должен_ быть таким:
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
class HtmlBibleReader(private val reader: BufferedReader) : Closeable {

  private var line = 0uL
  private var bookName = "?"
  private var chapterNumber = 0.toUShort()
  private var verseNumber = 0.toUShort()
  private var currentBuffer = null as Document?
  private var currentBody = null as Element?

  init {
    reloadBuffer()
  }

  private fun reloadBuffer() {
    currentBuffer = when (val l = reader.readLine()) {
      null -> null
      else -> parse(l)
    }
    currentBody = when (currentBuffer) {
      null -> null
      else -> currentBuffer!!.body()
    }
    ++line
  }

  val hasNext get() = currentBuffer != null

  private fun currentText() = currentBuffer!!.text()

  private fun isCurrentTag(tag: String) = currentBody!!.firstElementChild()!!.tagName() == tag

  private val isNewBook get() = isCurrentTag("h3")

  private fun loadNewBook(): Verse {
    chapterNumber = 0u
    bookName = currentText()
    reloadBuffer()
    return loadNewChapter()
  }

  private val isNewChapter get() = isCurrentTag("h4")

  private val isNewVerse get() = isCurrentTag("p")

  private fun loadNewChapter(): Verse {
    ++chapterNumber
    verseNumber = 0u
    val text = currentText()
    check(chapterNumber.toString() == text) {
      "Номер главы $chapterNumber не найден на линии $line: $text"
    }
    reloadBuffer()
    return loadNewVerse()
  }

  private fun loadNewVerse(): Verse {
    ++verseNumber
    val verseNumberAsText = verseNumber.toString()
    val verseText = currentText()
    val verseNumberIdx = verseText.indexOf(verseNumberAsText)
    check(verseNumberIdx >= 0) {
      "Номер стиха $bookName $chapterNumber:$verseNumber не найден на линии $line: $verseText"
    }
    val pureTextIdx = verseNumberIdx + verseNumberAsText.length
    val pureText = verseText.substring(pureTextIdx).trim()
    check(pureText.isNotEmpty()) {
      "Текст стиха $bookName $chapterNumber:$verseNumber отсутствует на линии $line"
    }
    reloadBuffer()
    return Verse(bookName, chapterNumber, verseNumber, pureText)
  }

  fun nextVerse() = when {
    isNewBook -> loadNewBook()
    isNewChapter -> loadNewChapter()
    isNewVerse -> loadNewVerse()
    else -> error("Не распознан html на линии $line")
  }

  override fun close() {
    reader.close()
  }
}