package app.biblequote.exceptions

class UnknownVerseException(book: String, chapter: Int, verse: Int) :
  UnknownReferenceException("Стих $book $chapter:$verse не найден")