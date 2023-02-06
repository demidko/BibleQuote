package app.biblequote.exceptions

class UnknownBookException(name: String) :
  UnknownReferenceException("Книга $name не найдена")