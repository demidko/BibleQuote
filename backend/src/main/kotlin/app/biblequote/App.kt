package app.biblequote

import me.bazhenov.groovysh.spring.GroovyShellServiceBean
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

  @Bean
  fun api(): Api {
    return Api(abbrResolver())
  }

  /**
   * Резолвер общепринятой аббревиатуры перевода в экземпляр перевода Библии
   */
  @Bean
  fun abbrResolver(): Map<String, Bible> {
    TODO()
  }

  /**
   * Разметка синодального перевода (ВЗ + НЗ).
   * Используется как общая разметка для синхронизации лемматизации.
   * Без этого не получится легко соотнести места разных переводов друг с другом.
   */
  @Bean
  fun rstMarkup(): BibleMarkup {
    return javaClass.getResource("rst-all-markup.json").let(::BibleMarkup)
  }

  /**
   * Разметка синодального перевода (только НЗ).
   * Используется как общая разметка НЗ для синхронизации лемматизации.
   * Без этого не получится легко соотнести места разных переводов друг с другом.
   */
  @Bean
  fun rstNewTestamentMarkup(): BibleMarkup {
    return javaClass.getResource("rst-new-testament-markup.json").let(::BibleMarkup)
  }

  /**
   * Groovy шелл с доступом к бинам. Подключиться можно с backend, например
   * ```
   * ssh -o StrictHostKeyChecking=no 127.1 -p 6789
   * ```
   */
  @Bean
  fun groovyShell(): GroovyShellServiceBean {
    return GroovyShellServiceBean().apply {
      setPublishContextBeans(true)
      setPort(6789)
      setDisableImportCompletions(true)
      isLaunchAtStart = true
    }
  }

  /**
   * Русский синодальный перевод. Наиболее распространенный, стандартный,
   * хорошо выполненный перевод Библии на русский язык.
   * Осуществлен синодом православной церкви в 1873ем году.
   */
  @Bean
  fun russianSynodalTranslation(): Bible {
    return Bible(rstMarkup(), "/bible/rst.html")
  }

  /**
   * Так называемый восточный перевод, центрально-азиатская русская Библия.
   * Идея современная и весьма сомнительная,
   * так как выглядит попыткой продать Библию под соусом мусульманской терминологии.
   * Продается под брендом "Смысловой перевод Таурата, Книги Пророков, Забура и Инжила".
   * Осуществлен International Bible Society (BIBLICA).
   * В нашем приложении он используется для улучшенной лемматизации.
   */
  @Bean
  fun centralAsianRussianScriptures(): Bible {
    return Bible(rstMarkup(), "/bible/cars.html")
  }


  /**
   * Юбилейное издание к 2000-летию Рождества Христова. Издано организацией «Свет на Востоке».
   * Представляет осовремененный синодальный перевод.
   * В частности, из хороших изменений, древнееврейское слово 𐤉𐤄𐤅𐤄 (буквы ЙХВХ, справа налево, произносится ЯХВЕ, букв. "Сущий")
   * корректно переведено как Господь, а не [устаревшей](https://www.bible.in.ua/Doc/yh.htm) транслитерацией "Иегова"
   */
  @Bean
  fun jubileeBible(): Bible {
    return Bible(rstMarkup(), "/bible/jbl.html")
  }


  /**
   * Хороший перевод Нового Завета епископом Кассианом (Безобразовым), по качеству сравним с синодальным
   */
  @Bean
  fun kassianNewTestamentTranslation(): Bible {
    return Bible(rstMarkup(), "/bible/knt.html")
  }


  /**
   * Очередной современный перевод Библии осуществленный World Bible Translation Center
   */
  @Bean
  fun modernBible(): Bible {
    return Bible(rstMarkup(), "/bible/mdr.html")
  }


  /**
   * Еще один современный перевод библии осуществленный International Bible Society (BIBLICA)
   */
  @Bean
  fun newRussianTranslation(): Bible {
    return Bible(rstMarkup(), "/bible/nrt.html")
  }

  /**
   * Современный перевод Библии под лицензией Creative Commons.
   * См. [сайт проекта](http://biblelamp.ru/openbible)
   */
  @Bean
  fun openTranslationNewTestament(): Bible {
    return Bible(rstMarkup(), "/bible/otnt.html")
  }


  /**
   * Современный перевод от Российского Библейского Общества 2015ого года
   */
  @Bean
  fun russianBibleSociety2015(): Bible {
    return Bible(rstMarkup(), "/bible/rbo2015.html")
  }

  /**
   * Современный перевод от Российского Библейского Общества 2011ого года
   */
  @Bean
  fun russianBibleSociety2011(): Bible {
    return Bible(rstMarkup(), "/bible/rbo2011.html")
  }

  /**
   * Современный перевод от международной библейской лиги
   */
  @Bean
  fun russianModernTranslation(): Bible {
    return Bible(rstMarkup(), "/bible/rsp.html")
  }

  /**
   * Новый Завет в переводе Давида Стерна для мессианских евреев
   */
  @Bean
  fun sternNewTestament(): Bible {
    return Bible(rstNewTestamentMarkup(), "/bible/stern.html")
  }

  /**
   * Совместное издание Института перевода Библии при Заокской духовной академии
   * и Библейско-богословского института св. апостола Андрея
   */
  @Bean
  fun zbBible(): Bible {
    return Bible(rstMarkup(), "/bible/zb.html")
  }
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
