package nl.gauket.performer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

@SpringBootApplication
class ThePerformerApplication

fun main(args: Array<String>) {
    runApplication<ThePerformerApplication>(*args)
}

@RestController
class MessageResource {
    @PostMapping("/achieve")
    fun post(@RequestBody message: Message): Any {

        // decrypt achievement state
        var before = Achievement(message.event.uppercase(), message.type)
        if (!message.state.equals("")) {
            before = Processor.decrypt(message.state)
        }

        // validate event
        // TODO return readable validation errors
        Processor.validate(message.event, message.type, before)

        // update fields
        val after = Processor.update(before)

        // encrypt
        return Processor.encrypt(after)
    }

    @PostMapping("/achievements")
    fun achievements(@RequestBody achieved: List<String>): Any {
        val ret = ArrayList<Achievement>()

        achieved.forEach {
            // TODO create badges and add to list
            ret.add(Processor.decrypt(it))
        }

        return ret
    }

    @GetMapping("/types")
    fun types(): List<AchievementType> = listOf(
        AchievementType.BASIC, AchievementType.DAILY, AchievementType.ANNIVERSARY
    )
}
