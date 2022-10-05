package app.biblequote

import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import

@Import(App::class)
internal class AppTest : App() {

  @Test
  fun contextLoads() {
    russianSynodalTranslation();
  }
}