package app.biblequote.utils

/**
 * Один стих из Библии. Нумерация числовых свойств в этом объекте начинается с `1`
 */
data class Verse(
  val book: String,
  val chapter: UShort,
  val number: UShort,
  val text: String
)