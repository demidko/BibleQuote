package app.biblequote

import com.github.demidko.aot.WordformMeaning.lookupForMeanings
import com.google.common.truth.Truth.assertThat
import org.apache.commons.configuration2.INIConfiguration
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.slf4j.LoggerFactory.getLogger
import java.io.File

@Suppress("NonAsciiCharacters", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class BibleTest {

  // todo все остальные переводы нужно сверять с этим (по кол-ву книг и глав со стихами в них)
  private val bible = javaClass.getResource("/bible/rst.html").let(::Bible)

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

    val actualBooksOrder = bible.booksNames.toList()

    assertThat(actualBooksOrder).isEqualTo(expectedBooksOrder)
  }

  @Test
  fun compileZb() {
    val iniConfig = INIConfiguration()
    val file = File("src/main/resources/bible/zb/bibleqt.ini").bufferedReader()
    iniConfig.read(file)
    val logger = LoggerFactory.getLogger("lol")
    val paths = iniConfig.getProperty("PathName") as List<String>
    val names = iniConfig.getProperty("FullName") as List<String>
    logger.warn("{}", names)
    logger.warn("{}", paths)
    // todo осталось слить в один html с добавлением h3 имен в начале и удалением <book>
  }

  fun testLemmas() {
    /**
     * todo
     * Что если мы проведем лемматическую индексацию (словарь: лемма -> стих) дважды
     * Сначала на RST, потом на современном переводе?
     * Таким образом получим дополнительную точность для RST.
     *
     * todo Важно учесть, что при лемматизации стих надо ставить в соответствие всем леммам (всем вариантам лемм)
     */
    val lemmas = mutableSetOf<String>()
    val unknowns = mutableSetOf<String>()
    for (book in bible.booksNames) {
      for (chapter in 1..bible.chaptersCount(book)) {
        for (verse in 1..bible.versesCount(book, chapter)) {
          val verseText = bible.text(book, chapter, verse)
          val words = // todo проверить лишние пробелы перед и до этих символов в тексте
            verseText.split(' ', '-', '.', ',', '!', '?', '(', ')', '«', '»', ';', ':', '[', ']')
              .filter(String::isNotBlank)
          for (w in words.filter(String::isNotBlank)) {
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
    if (w.isBlank()) {
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