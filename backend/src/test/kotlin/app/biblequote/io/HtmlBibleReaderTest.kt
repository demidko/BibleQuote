package app.biblequote.io

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal object HtmlBibleReaderTest {

  private lateinit var reader: HtmlBibleReader

  @JvmStatic
  @BeforeAll
  fun init(): Unit {
    val markup = javaClass.getResource("/rst-markup.json")!!.let(::Markup)
    val bufferedReader = javaClass.getResourceAsStream("/bible/rst.html")!!.bufferedReader()
    reader = HtmlBibleReader(bufferedReader, markup)
  }

  @JvmStatic
  @AfterAll
  fun close(): Unit {
    reader.close()
  }

  @Test
  fun allBooksShouldBeCorrectlyLoaded() {
    assertThat(reader.hasNext).isTrue()
    val firstVerse = reader.nextVerse()
    assertThat(firstVerse).isEqualTo(
      Verse(
        "Бытие",
        1u,
        1u,
        "В начале сотворил Бог небо и землю."
      )
    )
    lateinit var lastVerse: Verse
    while (reader.hasNext) {
      lastVerse = reader.nextVerse()
    }
    assertThat(lastVerse).isEqualTo(
      Verse(
        "Откровение",
        22u,
        21u,
        "Благодать Господа нашего Иисуса Христа со всеми вами. Аминь."
      )
    )
  }
}