package app.biblequote.dto

/**
 * Один стих из Библии. Нумерация числовых свойств в этом объекте начинается с `1`
 */
data class Verse(
  val book: String,
  val chapter: Int,
  val number: Int,
  val text: String
) {

  override fun toString() = "$text — $book $chapter:$number"
}