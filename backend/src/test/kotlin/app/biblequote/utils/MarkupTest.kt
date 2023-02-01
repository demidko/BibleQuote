package app.biblequote.utils

import org.junit.jupiter.api.Test

class MarkupTest {

  @Test
  fun markupTest() {
    val url = javaClass.getResource("/rst-markup.json")!!
    val markup = Markup(url)
  }
}