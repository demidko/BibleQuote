package app.biblequote

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class VersesReaderTest {

  private lateinit var reader: VersesReader

  @BeforeEach
  fun init() {
    reader = javaClass.getResourceAsStream("/bible.html")!!.bufferedReader().let(::VersesReader)
  }

  @AfterEach
  fun close() {
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