package app.biblequote

import app.biblequote.exceptions.UnknownTranslationException

/**
 * Сущность обеспечивает доступ ко множеству переводов Библии по их аббревиатурам.
 * @param abbrToBible набор пар (аббревиатура + перевод), например:
 * ```
 * "rst" to getRstBible(),
 * "kjv" to getKjvBible()
 * ```
 */
class AbbrsResolver(vararg abbrToBible: Pair<String, Bible>) {

  private val bibles = abbrToBible.toMap()

  fun translation(abbr: String): Bible {
    return bibles[abbr] ?: throw UnknownTranslationException(abbr)
  }
}