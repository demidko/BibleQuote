package app.biblequote.utils

/**
 * Ссылка на один стих из Библии. Нумерация числовых свойств в этом объекте начинается с `1`
 */
data class VerseRef(
  val book: String,
  val chapter: Int,
  val number: Int
) {

  override fun toString() = "$book $chapter:$number"
}
