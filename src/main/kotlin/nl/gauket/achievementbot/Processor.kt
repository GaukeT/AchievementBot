package nl.gauket.achievementbot

import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

class Processor {
    companion object {
        private const val delimiter = "&&"

        fun decrypt(state: String): Achievement {
            // do some more magic //
            val dataString = String(Base64.getDecoder().decode(state))

            // return values from dataString
            val split = dataString.split(delimiter)
            val name = split[0]
            val type = AchievementType.valueOf(split[1])
            val count = split[2].toInt()
            val text = split[3]
            val since = LocalDateTime.parse(split[4])
            val last = LocalDateTime.parse(split[5])

            return Achievement(name, type, count, text, since, last)
        }

        fun encrypt(achievement: Achievement) : Key {
            // do some more magic //
            println(achievement.event + " - " + achievement.count + " - " + achievement.text)
            val dataString = achievement.event.plus(delimiter)
                .plus(achievement.type).plus(delimiter)
                .plus(achievement.count).plus(delimiter)
                .plus(achievement.text).plus(delimiter)
                .plus(achievement.since).plus(delimiter)
                .plus(achievement.last)
            return Key(Base64.getEncoder().encodeToString(dataString.toByteArray()))
        }

        fun validate(event: String, type: AchievementType, achievement: Achievement) {
            if (event.uppercase() != achievement.event) {
                throw IllegalArgumentException("achievement does not match state")
            }

            if (type != achievement.type) {
                throw IllegalArgumentException("type does not match state")
            }
        }

        fun update(achievement: Achievement): Achievement {
            achievement.update()
            return achievement
        }
    }
}