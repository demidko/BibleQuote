package app.biblequote

/**
 * Нумерация всех числовых свойств в этом объекте начинается с `1`
 */
data class Verse(
  val bookName: String,
  val chapterNumber: Int,
  val verseNumber: Int,
  val verseText: String
)