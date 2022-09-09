package app.biblequote

import com.github.demidko.aot.WordformMeaning.lookupForMeanings
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory.getLogger

@Suppress("NonAsciiCharacters", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class BibleTest {

  private val bible = javaClass.getResource("/bible/rst.html.gz").let(::Bible)

  @Test
  fun `Проверяем что стихи внутри книг загружены в правильном порядке`() {
    assertThat(
      bible.text(
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
      bible.text(
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

    assertThat((1..66).map(bible::nameOf)).isEqualTo(expectedBooksOrder)
  }

  @Test
  fun testLemmas() {
    /**
     * todo
     * Что если мы проведем лемматическую индексацию (словарь: лемма -> стих) дважды
     * Сначала на RST, потом на современном переводе?
     * Таким образом получим дополнительную точность для RST.
     * Нужно узнать соотношение известных/неизвестных слов для новых переводов (50/50 для RST).
     */
    val lemmas = mutableSetOf<String>()
    val unknowns = mutableSetOf<String>()
    for (book in bible.booksNames) {
      for (chapter in 1..bible.chaptersCount(book)) {
        for (verse in 1..bible.versesCount(book, chapter)) {
          val words =
            bible.text(book, chapter, verse).split(' ').map(String::trim).map(this::clean).filter(String::isNotBlank)
          for (w in words) {
            val lemma = lookupForMeanings(w)
            if (lemma.isEmpty()) {
              unknowns.add(w)
            } else {
              lemmas.add(w)
            }
          }
        }
      }
    }
    getLogger(javaClass).warn("lemmas: ${lemmas.size}")
    getLogger(javaClass).warn("unknowns: ${unknowns.size}")
    getLogger(javaClass).warn("{}", unknowns.take(100))
  }

  tailrec fun clean(w: String): String {
    if(w.isBlank()) {
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