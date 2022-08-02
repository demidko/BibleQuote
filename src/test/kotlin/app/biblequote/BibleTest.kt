package app.biblequote

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class BibleTest {

  private val bible = javaClass.getResource("/bible.html")!!.let(::Bible)

  @Test
  fun versesShouldBeCorrectlyLoaded() {
    assertThat(
      bible.verseText(
        "Откровение",
        13,
        10
      )
    ).isEqualTo(
      "Кто ведет в плен, тот сам пойдет в плен; " +
        "кто мечом убивает, тому самому надлежит быть убиту мечом. " +
        "Здесь терпение и вера святых."
    )
    assertThat(
      bible.verseText(
        "Исаия",
        65,
        12
      )
    ).isEqualTo(
      "вас обрекаю Я мечу, и все вы преклонитесь на заклание: " +
        "потому что Я звал, и вы не отвечали; говорил, и вы не слушали, " +
        "но делали злое в очах Моих и избирали то, что было неугодно Мне."
    )
  }
}