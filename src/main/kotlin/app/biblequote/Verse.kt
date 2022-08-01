package app.biblequote

data class Verse(
  val bookName: String,
  val bookNumber: Int,
  val chapterNumber: Int,
  val verseNumber: Int,
  val verseText: String
)