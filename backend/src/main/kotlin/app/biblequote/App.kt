package app.biblequote

import app.biblequote.Bible.Companion.Bible
import me.bazhenov.groovysh.spring.GroovyShellServiceBean
import org.springframework.beans.factory.annotation.Value
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

  @Value("")

  @Bean
  fun russianSynodalTranslation(): Bible {
    return Bible(
      "/bible/rst.html",
      rstMarkup(),
      "Русский синодальный перевод. " +
        "Наиболее распространенный, стандартный, хорошо выполненный перевод Библии на русский язык. " +
        "Осуществлен синодом православной церкви в 1873ем году."
    )
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
    return Bible(
      "/bible/cars.html",
      rstMarkup(),
      "Так называемый восточный перевод, центрально-азиатская русская Библия. " +
        "Идея современная и весьма сомнительная, " +
        "так как выглядит попыткой продать Библию под соусом мусульманской терминологии. " +
        "Продается под брендом 'Смысловой перевод Таурата, Книги Пророков, Забура и Инжила'. " +
        "Осуществлен International Bible Society (BIBLICA). " +
        "В нашем приложении он используется для улучшенной лемматизации."
    )
  }


  @Bean
  fun jubileeBible(): Bible {
    return Bible(
      "/bible/jbl.html",
      rstMarkup(),
      "Юбилейное издание к 2000-летию Рождества Христова. Издано организацией «Свет на Востоке». " +
        "Представляет осовремененный синодальный перевод. " +
        "В частности, из хороших изменений, древнееврейское слово \uD802\uDD09\uD802\uDD04\uD802\uDD05\uD802\uDD04 " +
        "(буквы ЙХВХ, справа налево, произносится ЯХВЕ), " +
        "корректно переведено как Господь, а не " +
        "<a href='https://www.bible.in.ua/Doc/yh.htm'>устаревшей транслитерацией 'Иегова'</a>"
    )
  }


  fun kassianNewTestamentTranslation(): Bible {
    return Bible(
      "/bible/knt.html",
      rstMarkup(),
      "Хороший перевод Нового Завета епископом Кассианом (Безобразовым), по качеству сравним с синодальным"
    )
  }


  fun modernBible(): Bible {
    return Bible(
      "/bible/mdr.html",
      rstMarkup(),
      "Очередной современный перевод Библии осуществленный World Bible Translation Center"
    )
  }

  fun newRussianTranslation(): Bible {
    return Bible(
      "/bible/nrt.html",
      rstMarkup(),
      "Еще один современный перевод библии осуществленный International Bible Society (BIBLICA)"
    )
  }

  fun openTranslationNewTestament(): Bible {
    return Bible(
      "/bible/otnt.html",
      rstMarkup(),
      "Современный перевод Библии под лицензией Creative Commons. " +
        "См. <a href='http://biblelamp.ru/openbible'>сайт проекта</a>"
    )
  }


  fun russianBibleSociety2015(): Bible {
    return Bible(
      "/bible/rbo2015.html",
      rstMarkup(),
      "Современный перевод от Российского Библейского Общества 2015ого года"
    )
  }


  fun russianBibleSociety2011(): Bible {
    return Bible(
      "/bible/rbo2011.html",
      rstMarkup(),
      "Современный перевод от Российского Библейского Общества 2011ого года"
    )
  }


  fun russianModernTranslation(): Bible {
    return Bible(
      "/bible/rsp.html",
      rstMarkup(), "" +
        "Современный перевод от международной библейской лиги"
    )
  }


  @Bean
  fun sternNewTestament(): Bible {
    return Bible(
      "/bible/stern.html",
      rstMarkup(),
      "Новый Завет в переводе Давида Стерна для мессианских евреев"
    )
  }


  fun zbBible(): Bible {
    return Bible(
      "/bible/zb.html",
      rstMarkup(),
      "Совместное издание Института перевода Библии при Заокской духовной академии " +
        "и Библейско-богословского института св. апостола Андрея"
    )
  }

  @Bean
  fun api(): Api {
    return Api(
      mapOf(
        "rst" to russianSynodalTranslation(),
      )
    )
  }

  /**
   * Разметка синодального перевода (эталон)
   */
  @Bean
  fun rstMarkup(): Markup {
    return javaClass.getResource("rst-markup.json").let(::Markup)
  }

  /**
   * Шелл с доступом к бинам. Подключиться можно с backend, например
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
}

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
