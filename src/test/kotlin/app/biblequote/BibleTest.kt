package app.biblequote

import app.biblequote.TestConstants.bible
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

@Suppress("NonAsciiCharacters")
internal class BibleTest {

  @Test
  fun `Проверяем что стихи внутри книг загружены в правильном порядке`() {
    assertThat(
      bible.text(
        "Откровение",
        13u,
        10u
      )
    ).isEqualTo(
      "Кто ведет в плен, тот сам пойдет в плен; " +
        "кто мечом убивает, тому самому надлежит быть убиту мечом. " +
        "Здесь терпение и вера святых."
    )
    assertThat(
      bible.text(
        "Исаия",
        65u,
        12u
      )
    ).isEqualTo(
      "вас обрекаю Я мечу, и все вы преклонитесь на заклание: " +
        "потому что Я звал, и вы не отвечали; говорил, и вы не слушали, " +
        "но делали злое в очах Моих и избирали то, что было неугодно Мне."
    )
  }

  @Test
  fun `Убеждаемся что книги загружены в правильном порядке`() {
    val expectedBooksOrder = listOf(
      "Бытие",
      "Исход",
      "Левит",
      "Числа",
      "Второзаконие",

      "Иисус Навин",
      "Судьи",
      "Руфь",

      "1-я Царств",
      "2-я Царств",
      "3-я Царств",
      "4-я Царств",
      "1-я Паралипоменон",
      "2-я Паралипоменон",

      "Ездра",
      "Неемия",
      "Есфирь",

      "Иов",
      "Псалтирь",
      "Притчи",
      "Екклесиаст",
      "Песнь Песней",

      "Исаия",
      "Иеремия",
      "Плач Иеремии",
      "Иезекииль",
      "Даниил",
      "Осия",
      "Иоиль",
      "Амос",
      "Авдий",
      "Иона",
      "Михей",
      "Наум",
      "Аввакум",
      "Софония",
      "Аггей",
      "Захария",
      "Малахия",

      "По Матфею",
      "По Марку",
      "По Луке",
      "По Иоанну",

      "Деяния",

      "Иакова",
      "1-е Петра",
      "2-е Петра",
      "1-е Иоанна",
      "2-е Иоанна",
      "3-е Иоанна",
      "Иуды",
      "Римлянам",
      "1-е Коринфянам",
      "2-е Коринфянам",
      "Галатам",
      "Ефесянам",
      "Филлипийцам",
      "Колосянам",
      "1-е Фессалоникийцам",
      "2-е Фессалоникийцам",
      "1-е Тимофею",
      "2-е Тимофею",
      "Титу",
      "Филимону",
      "Евреям",

      "Откровение"
    )

    assertThat((1..66).map(Int::toUShort).map(bible::nameOf)).isEqualTo(expectedBooksOrder)
  }
}