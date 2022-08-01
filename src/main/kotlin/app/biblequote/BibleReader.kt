package app.biblequote

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.Closeable

class BibleReader(private val reader: BufferedReader) : Closeable {

  private var bookName = "?"
  private var bookNumber = 0
  private var chapterNumber = 0
  private var verseNumber = 0
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
    ++bookNumber
    chapterNumber = 0
    bookName = currentText()
    reloadBuffer()
    return loadNewChapter()
  }

  private val isNewChapter get() = isCurrentTag("h4")

  private fun loadNewChapter(): Verse {
    ++chapterNumber
    verseNumber = 0
    reloadBuffer()
    return loadNewVerse()
  }

  private fun loadNewVerse(): Verse {
    ++verseNumber
    val verseNumberText = verseNumber.toString()
    val verseBodyText = currentText().substringAfter(verseNumberText)
    reloadBuffer()
    return Verse(bookName, bookNumber, chapterNumber, verseNumber, verseBodyText)
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