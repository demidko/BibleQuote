package app.biblequote

class BibleSearch(private val bible: Bible) {

  fun lookupForVerse(by: String): Verse {
    val keywords = by.trim().split(' ')
    TODO()
  }
}