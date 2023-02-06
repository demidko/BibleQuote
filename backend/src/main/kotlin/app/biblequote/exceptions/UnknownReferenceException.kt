package app.biblequote.exceptions

/**
 * Исключение, порождаемое нераспознанной ссылкой на место из Библии
 */
abstract class UnknownReferenceException(s: String) : IllegalArgumentException(s)