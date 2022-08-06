package app.biblequote

import com.github.demidko.bits.BitReader
import com.github.demidko.bits.BitWriter

/**
 * Нумерация всех числовых свойств в этом объекте начинается с `1`
 */
data class Verse(
  val book: String,
  val chapter: Int,
  val number: Int,
  val text: String
) {
  fun zip() =
    BitWriter()
      .writeShort(book.toShort())
      .writeShort(chapter.toShort())
      .writeShort(number.toShort())
      .toLong()

  companion object {
    fun unzip(bible: RandomAccessBible, id: Long): Verse {
      val reader = BitReader(id)
      val book = reader.readShort().toInt().let(bible::nameOf)
      val chapter = reader.readShort().toInt()
      val verse = reader.readShort().toInt()
      val text = bible.text(book, chapter, verse)
      return Verse(book, chapter, verse, text)
    }
  }
}