package app.biblequote

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BibleReaderTest {

  lateinit var reader: BibleReader

  @BeforeEach
  fun init() {
    reader = javaClass.getResourceAsStream("/bible.html")!!.bufferedReader().let(::BibleReader)
  }

  @AfterEach
  fun close() {
    reader.close()
  }

  @Test
  fun test() {
    assertThat(reader.hasNext).isTrue()
    assertThat(reader.nextVerse()).isEqualTo(
      Verse(
        "Бытие",
        1,
        1,
        1,
        "В начале сотворил  Бог небо и землю."
      )
    )
  }
}