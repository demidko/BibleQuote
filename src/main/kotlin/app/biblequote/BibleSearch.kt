package app.biblequote

import com.github.demidko.aot.WordformMeaning
import com.github.demidko.aot.WordformMeaning.lookupForMeanings

class BibleSearch(private val bible: RandomAccessBible) {

  private val lemmasIdsToVersesIds = mutableMapOf<Long, Long>()

  init {
    // тут не забываем пропустить предлоги и союзы
  }

  fun lookupByPhrase(by: String): Set<Verse> {
    val keywords = by.trim().split(' ')
    TODO()
  }

  private fun lookupByWord(word: String): Set<Verse> {
    lookupForMeanings(word)
      .map(WordformMeaning::getLemma)
      .map(WordformMeaning::getId)
      .map(lemmasIdsToVersesIds::get).map {

      }
    TODO()
  }
}