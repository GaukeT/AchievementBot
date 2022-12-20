package nl.gauket.achievementbot

import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter

@RestController
class AchievementController {
    @PostMapping("/achieve")
    fun achieve(@RequestBody message: AchieveMessage): Any {

        // decrypt achievement state
        var before = Achievement(message.event.uppercase(), message.type)
        if (message.key != "") {
            before = Processor.decrypt(message.key)
        }

        // validate event
        // TODO return readable validation errors
        Processor.validate(message.event, message.type, before)

        // update fields
        val after = Processor.update(before)

        // encrypt
        return Processor.encrypt(after)
    }

//    @CrossOrigin(origins = ["http://localhost:8083"])

    @PostMapping("/achievements")
    fun achievements(@RequestBody achieved: AchievementsMessage): Any {
        var ret = ""

        if (achieved.title != "") {
            ret = "<h1 style=\"font-family:Arial, Helvetica, sans-serif;margin: 5px;color:rgb(51, 51, 51)\">" + achieved.title.uppercase() + "'s badges</h1>"
        }

        achieved.keys.forEach {
            ret += Badge.getBadge(Processor.decrypt(it))
        }
        return ret
    }

    @GetMapping("/types")
    fun types(): List<AchievementType> = listOf(
        AchievementType.BASIC, AchievementType.DAILY, AchievementType.ANNIVERSARY
    )
}
