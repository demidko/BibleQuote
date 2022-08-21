package app.biblequote

import app.biblequote.TestConstants.bible
import app.biblequote.Verse.Companion.decode
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

@Suppress("NonAsciiCharacters")
internal class VerseTest {

  @Test
  fun `Убеждаемся, что идентификаторы стихов срабатывают как надо`() {
    val originalVerse = Verse(
      "Иеремия",
      23u, 11u,
      "ибо и пророк и священник - лицемеры; даже в доме Моем Я нашел нечестие их, говорит Господь."
    )
    val encodedVerse = originalVerse.encode(bible)
    assertThat(encodedVerse).isInstanceOf(java.lang.Long::class.java)
    assertThat(decode(encodedVerse, bible)).isEqualTo(originalVerse)
  }
}