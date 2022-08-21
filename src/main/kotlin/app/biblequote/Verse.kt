package app.biblequote

import com.github.demidko.bits.BitReader
import com.github.demidko.bits.BitWriter

/**
 * Один стих из Библии. Нумерация всех числовых свойств в этом объекте начинается с `1`
 */
data class Verse(val book: String, val chapter: UShort, val number: UShort, val text: String) {

  fun encode(with: Bible) =
    BitWriter()
      .writeShort(with.numberOf(book).toShort())
      .writeShort(chapter.toShort())
      .writeShort(number.toShort())
      .toLong()

  companion object {
    fun decode(encoded: Long, with: Bible): Verse {
      val reader = BitReader(encoded)
      val book = reader.readShort().toUShort().let(with::nameOf)
      val chapter = reader.readShort().toUShort()
      val verse = reader.readShort().toUShort()
      val text = with.text(book, chapter, verse)
      return Verse(book, chapter, verse, text)
    }
  }
}