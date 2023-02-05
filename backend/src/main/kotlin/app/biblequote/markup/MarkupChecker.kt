package app.biblequote.markup

import app.biblequote.io.Verse

/**
 * Сущность для проверки потокового чтения из Библии
 * @param ref последовательные ссылки на стихи в указанном разметкой порядке
 */
class MarkupChecker(private val ref: Iterator<VerseRef>) {

  fun checkNext(verse: Verse) {
    require(ref.hasNext()) {
      """
        Больше стихов не ожидалось, но обнаружен приложенный:
          $verse
      """.trimIndent()
    }
    val expectedRef = ref.next()
    val actualRef = VerseRef(verse.book, verse.chapter, verse.number)
    require(actualRef == expectedRef) {
      """
        Ожидался номер стиха $expectedRef, но вместо него идёт стих:
          $verse
      """.trimIndent()
    }
  }
}