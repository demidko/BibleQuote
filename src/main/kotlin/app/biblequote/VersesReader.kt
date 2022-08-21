package app.biblequote

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.Closeable

/**
 * Класс для чтения Библии стих за стихом.
 */
class VersesReader(private val reader: BufferedReader) : Closeable {

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
    val verseNumberText = verseNumber.toString()
    val verseBodyText = currentText().substringAfter(verseNumberText)
    reloadBuffer()
    return Verse(bookName, chapterNumber, verseNumber, verseBodyText)
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