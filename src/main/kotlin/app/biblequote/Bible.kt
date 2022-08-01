package app.biblequote

import java.net.URL

class Bible(url: URL) {
  private val booksNames = mutableListOf<String>()
  private val versesTexts = mutableListOf<String>()
  private val chaptersOffsets = mutableListOf<Int>()
  private val booksOffsets = mutableListOf<Int>()


  init {
    val reader = url.openStream().bufferedReader().let(::BibleReader)
    reader.use {
      while (reader.hasNext) {

      }
    }

  }

  fun listAvailableBooks(): List<String> {
    TODO()
  }

  fun lo
}