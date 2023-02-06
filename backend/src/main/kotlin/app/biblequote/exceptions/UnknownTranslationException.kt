package app.biblequote.exceptions

class UnknownTranslationException(abbr: String) :
  UnknownReferenceException("Перевод $abbr не найден")