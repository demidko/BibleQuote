package app.biblequote

object TestConstants {
  val bible = javaClass.getResource("/bible.html")!!.let(::Bible)
}