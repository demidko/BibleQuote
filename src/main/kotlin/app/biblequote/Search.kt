package app.biblequote

import app.biblequote.utils.Verse

class Search(bible: Bible) {

  init {
    // todo тут проводим поиск по каждому слову Библии -- и кешируем результаты
  }

  fun searchPhrase(phrase: String): Set<Verse> {
    val result = mutableSetOf<Verse>()
    val words = splitPhrase(phrase)
    for (w in words) {
      val verses = searchWord(w)
      cacheSearch(w, verses)
      result.addAll(verses)
    }
    return result
  }

  private fun splitPhrase(phrase: String): Set<String> {
    TODO()
  }

  private fun searchWord(word: String): Set<Verse> {
    TODO()
  }

  private fun cacheSearch(word: String, verses: Set<Verse>) {
    TODO()
  }
}