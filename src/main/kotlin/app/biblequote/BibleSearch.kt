package app.biblequote

class BibleSearch(private val bible: RandomAccessBible) {

  fun lookupForVerse(by: String): Verse {
    val keywords = by.trim().split(' ')
    TODO()
  }
}