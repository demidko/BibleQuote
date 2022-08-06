package app.biblequote

class BibleSearch(private val bible: RandomAccessBible) {

  private val lemmasIdxToVersesIdx = mutableMapOf<Long, Long>()

  fun lookupForVerse(by: String): Set<Verse> {
    val keywords = by.trim().split(' ')
    TODO()
  }
}