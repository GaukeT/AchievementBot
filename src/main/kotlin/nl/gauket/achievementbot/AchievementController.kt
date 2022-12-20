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
            ret =
                "<h1 style=\"font-family:Verdana;margin-left: 10px;color:#006600\">" + achieved.title.uppercase() + "'s achievements</h1>"
        }

        achieved.keys.forEach {
            ret += getBadge(Processor.decrypt(it))
        }
        return ret
    }

    @GetMapping("/types")
    fun types(): List<AchievementType> = listOf(
        AchievementType.BASIC, AchievementType.DAILY, AchievementType.ANNIVERSARY
    )

    fun getBadge(achievement: Achievement): String {
        val p = 10
        val width = Integer.max((achievement.event.length * 30) + p, 300)
        val height = 150

        // nicely format date
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedSince = achievement.since?.format(formatter)

        var badge = "<svg height=\"" + (height) + "\" width=\"" + (width) + "\">" +
                "  <defs>" +
                "    <linearGradient id=\"grad1\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"0%\">" +
                "      <stop offset=\"0%\" style=\"stop-color:rgb(255,153,0);stop-opacity:1\" />" +
                "      <stop offset=\"100%\" style=\"stop-color:rgb(255,204,153);stop-opacity:1\" />" +
                "    </linearGradient>" +
                "  </defs>" +
                "  <rect rx=\"20\" ry=\"20\" x=\"" + p + "\" y=\"" + p + "\" width=\"" + (width - (2 * p)) + "\" height=\"" + (height - (2 * p)) + "\" fill=\"url(#grad1)\" />" +
                "  <text fill=\"#006600\" font-size=\"35\" font-family=\"Verdana\" x=\"" + (3 * p) + "\" y=\"" + (6 * p) + "\">" + achievement.event + "</text>"

        badge += if (achievement.type != AchievementType.ANNIVERSARY) {
            "  <text fill=\"#006600\" font-style=\"italic\" font-size=\"32\" font-family=\"Verdana\" x=\"50\" y=\"" + ((height * 0.65) + p) + "\">" + achievement.count + "X</text>"
        } else {
            "  <text fill=\"#006600\" font-style=\"italic\" font-size=\"32\" font-family=\"Verdana\" x=\"50\" y=\"" + ((height * 0.65) + p) + "\">" + achievement.text + "</text>"
        }
        badge += "  <text fill=\"#006600\" font-style=\"italic\" font-size=\"10\" font-family=\"Verdana\" x=\"" + (width - 150) + "\" y=\"" + ((height * 0.68) + p) + "\">since:</text>" +
                "  <text fill=\"#006600\" font-size=\"20\" font-family=\"Verdana\" x=\"" + (width - 150) + "\" y=\"" + ((height * 0.8) + p) + "\">" + formattedSince + "</text>" +
                "  Sorry, your browser does not support inline SVG." +
                "</svg>"

        return badge
    }
}
