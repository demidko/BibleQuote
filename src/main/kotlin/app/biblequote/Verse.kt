package app.biblequote

/**
 * Один стих из Библии. Нумерация всех числовых свойств в этом объекте начинается с `1`
 */
class Verse(
  val book: String,
  val chapter: UShort,
  val number: UShort,
  val text: String
)