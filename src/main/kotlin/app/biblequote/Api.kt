package app.biblequote

import org.springframework.web.bind.annotation.RestController

@RestController
class Api(private val bible: Bible) {

}