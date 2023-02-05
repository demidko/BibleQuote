package app.biblequote.markup

/**
 * Ссылка на один стих из Библии. Нумерация числовых свойств в этом объекте начинается с `1`
 */
data class VerseRef(
  val book: String,
  val chapter: UShort,
  val number: UShort
) {

  override fun toString() = "$book $chapter:$number"
}
