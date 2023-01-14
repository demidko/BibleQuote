package app.biblequote

import com.github.demidko.aot.WordformMeaning.lookupForMeanings
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.slf4j.LoggerFactory.getLogger
import java.io.File

@Suppress("NonAsciiCharacters", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class BibleTest {


  @Test
  @Disabled
  fun jubileeBibleTest() {
    javaClass
      .getResource("/bible/jbl.html")
      .let(::Bible)
  }

  fun testLemmas() {
    /**
     * todo
     * Что если мы проведем лемматическую индексацию (словарь: лемма -> стих) дважды
     * Сначала на RST, потом на современном переводе?
     * Таким образом получим дополнительную точность для RST.
     *
     * todo Важно учесть, что при лемматизации стих надо ставить в соответствие всем леммам (всем вариантам лемм)
     */
    val lemmas = mutableSetOf<String>()
    val unknowns = mutableSetOf<String>()
/*    for (book in bible.booksNames) {
      for (chapter in 1..bible.chaptersCount(book)) {
        for (verse in 1..bible.versesCount(book, chapter)) {
          val verseText = bible.text(book, chapter, verse)
          val words = // todo проверить лишние пробелы перед и до этих символов в тексте
            verseText.split(' ', '-', '.', ',', '!', '?', '(', ')', '«', '»', ';', ':', '[', ']')
              .filter(String::isNotBlank)
          for (w in words.filter(String::isNotBlank)) {
            val lemma = lookupForMeanings(w)
            if (lemma.isEmpty()) {
              unknowns.add(w)
            } else {
              lemmas.add(w)
            }
          }
        }
      }
    }*/
    getLogger(javaClass).warn("lemmas: ${lemmas.size}")
    getLogger(javaClass).warn("unknowns: ${unknowns.size}")
    getLogger(javaClass).warn("{}", unknowns.take(100))
  }

  tailrec fun clean(w: String): String {
    if (w.isBlank()) {
      return w
    }
    if (!w.first().isLetter()) {
      return clean(w.drop(1))
    }
    if (!w.last().isLetter()) {
      return clean(w.dropLast(1))
    }
    return w
  }
}