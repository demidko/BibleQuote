package app.biblequote

import java.net.URL

class Bible(url: URL) {

  init {
    val reader = url.openStream().bufferedReader().let(::BibleReader)
    reader.use {
      while (reader.hasNext) {
        reader.nextVerse()
      }
    }
  }

  fun listAvailableBooks(): List<String> {

    TODO()
  }
}