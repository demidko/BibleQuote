package app.biblequote

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@Suppress("NonAsciiCharacters")
internal class AppTest {

  @Autowired
  private lateinit var appContext: ApplicationContext

/*  @Test
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

    val actualBooksOrder = bible.booksNames.toList()

    assertThat(actualBooksOrder).isEqualTo(expectedBooksOrder)
  }*/

/*  @Test
  fun `Проверяем корректность синодального перевода`() {
    assertThat(
      russianSynodalTranslation().text(
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
      russianSynodalTranslation().text(
        "Исаия",
        65,
        12
      )
    ).isEqualTo(
      "вас обрекаю Я мечу, и все вы преклонитесь на заклание: " +
        "потому что Я звал, и вы не отвечали; говорил, и вы не слушали, " +
        "но делали злое в очах Моих и избирали то, что было неугодно Мне."
    )
  }*/

  @Test
  fun `Проверяем что последовательность книг и глав в разных переводах одинакова с синодальным переводом`() {
    println(appContext)
  }
}