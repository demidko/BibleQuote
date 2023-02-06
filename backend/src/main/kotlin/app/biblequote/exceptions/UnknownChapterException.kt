package app.biblequote.exceptions

class UnknownChapterException(book: String, chapter: Int) :
  UnknownReferenceException("Глава $book $chapter не найдена")