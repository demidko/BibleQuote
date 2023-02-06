package app.biblequote.utils

import app.biblequote.dto.Verse
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

object HtmlBibleReaderTest {

  private lateinit var rst: HtmlBibleReader

  @JvmStatic
  @BeforeAll
  fun init() {
    rst =
      javaClass.getResourceAsStream("/bible/rst.html")!!
        .bufferedReader()
        .let(::HtmlBibleReader)
  }

  @JvmStatic
  @AfterAll
  fun close() {
    rst.close()
  }

  @Test
  fun rstShouldBeCorrectlyLoaded() {
    assertThat(rst.hasNext).isTrue()
    val firstVerse = rst.nextVerse()
    assertThat(firstVerse).isEqualTo(
      Verse(
        "Бытие",
        1,
        1,
        "В начале сотворил Бог небо и землю."
      )
    )
    lateinit var lastVerse: Verse
    while (rst.hasNext) {
      lastVerse = rst.nextVerse()
    }
    assertThat(lastVerse).isEqualTo(
      Verse(
        "Откровение",
        22,
        21,
        "Благодать Господа нашего Иисуса Христа со всеми вами. Аминь."
      )
    )
  }
}