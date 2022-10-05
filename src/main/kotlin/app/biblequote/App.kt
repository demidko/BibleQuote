package app.biblequote

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootConfiguration
@EnableAutoConfiguration
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class App {

  /**
   * Русский синодальный перевод.
   * Наиболее распространенный, стандартный, хорошо выполненный перевод Библии на русский язык.
   * Осуществлен синодом православной церкви в 1873ем году.
   */
  @Bean
  fun russianSynodalTranslation(): Bible {
    return javaClass.getResource("/bible/rst.html").let(::Bible)
  }

  /**
   * Так называемый восточный перевод, центрально-азиатская русская Библия.
   * Идея современная и весьма сомнительная,
   * так как выглядит попыткой продать Библию под соусом мусульманской терминологии.
   * Подается под брендом "Смысловой перевод Таурата, Книги Пророков, Забура и Инжила".
   * Осуществлен International Bible Society (BIBLICA).
   * В нашем приложении он используется только для улучшенной лемматизации.
   */
  @Bean
  fun centralAsianRussianScriptures(): Bible {
    return javaClass.getResource("/bible/cars.html").let(::Bible)
  }

  /**
   * Юбилейное издание к 2000-летию Рождества Христова. Издано организацией «Свет на Востоке».
   * Представляет осовремененный синодальный перевод.
   * В частности, из хороших изменений, древнееврейское слово 𐤉𐤄𐤅𐤄 (буквы ЙХВХ, справа налево, произносится ЯХВЕ),
   * корректно переведено как Господь, а не [устаревшей транслитерацией "Иегова"](https://www.bible.in.ua/Doc/yh.htm).
   */
  @Bean
  fun jubileeBible(): Bible {
    return javaClass.getResource("/bible/jbl.html").let(::Bible)
  }

  /**
   * Хороший перевод Нового Завета епископом Кассианом (Безобразовым), по качеству сравним с синодальным.
   */
  @Bean
  fun kassianNewTestamentTranslation(): Bible {
    return javaClass.getResource("/bible/knt.html").let(::Bible)
  }

  /**
   * Очередной современный перевод Библии осуществленный World Bible Translation Center.
   */
  @Bean
  fun modernBible(): Bible {
    return javaClass.getResource("/bible/mdr.html").let(::Bible)
  }

  /**
   * Еще один современный перевод библии осуществленный International Bible Society (BIBLICA).
   */
  @Bean
  fun newRussianTranslation(): Bible {
    return javaClass.getResource("/bible/nrt.html").let(::Bible)
  }

  /**
   * Современный перевод Библии под лицензией Creative Commons.
   * См. [сайт проекта](http://biblelamp.ru/openbible/).
   */
  @Bean
  fun openTranslationNewTestament(): Bible {
    return javaClass.getResource("/bible/otnt.html").let(::Bible)
  }

  /**
   * Современный перевод от Российского Библейского Общества 2015ого года.
   */
  @Bean
  fun russianBibleSociety2015(): Bible {
    return javaClass.getResource("/bible/rbo2015.html").let(::Bible)
  }

  /**
   * Современный перевод от Российского Библейского Общества 2011ого года.
   */
  @Bean
  fun russianBibleSociety2011(): Bible {
    return javaClass.getResource("/bible/rbo2011.html").let(::Bible)
  }

  /**
   * Современный перевод от международной библейской лиги.
   */
  @Bean
  fun russianModernTranslation(): Bible {
    return javaClass.getResource("/bible/rsp.html").let(::Bible)
  }

  /**
   * Новый Завет в переводе Давида Стерна для мессианских евреев.
   */
  @Bean
  fun sternNewTestament(): Bible {
    return javaClass.getResource("/bible/stern.html").let(::Bible)
  }

  /**
   * Совместное издание Института перевода Библии при Заокской духовной академии и Библейско-богословского института св. апостола Андрея.
   */
  @Bean
  fun zbBible(): Bible {
    return javaClass.getResource("/bible/zb.html").let(::Bible)
  }


  @Bean
  fun translations(): Map<String, Bible> {
    return mapOf(
      "rst" to russianSynodalTranslation(),
    )
  }

  @Bean
  fun api(): Api {
    return Api(translations())
  }
}


fun main(args: Array<String>) {
  runApplication<App>(*args)
}
