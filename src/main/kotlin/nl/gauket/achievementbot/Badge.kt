package nl.gauket.achievementbot

import java.time.format.DateTimeFormatter

class Badge {
    companion object {
        fun getBadge(achievement: Achievement): String {
            val size = if (achievement.count.toString().length > 1) 42 else 60

            var span = ""
            if (achievement.event.length <= 11) {
                span += addTSpan(achievement.event, 0)
            } else {
                val splitted = achievement.event.split(" ")

                if (splitted.size == 1) {
                    // TODO wrap word
                    span += addTSpan(achievement.event, 0)
                } else {
                    var dy = -1 * splitted.size
                    for (i in 0..splitted.size - 1) {
                        span += addTSpan(splitted[i], dy)
                        dy += 10
                    }
                }
            }

            // GOLD
            var color = "rgba(201, 176, 55, 1)"
            var sub = "rgba(120, 102, 23, 1)"
            when {
                achievement.count <= 3 -> {
                    // BRONZE
                    color = "rgba(173, 138, 86, 1)"
                    sub = "rgba(121, 98, 63, 1)"
                }
                achievement.count <= 7 -> {
                    // SILVER
                    color = "rgba(215, 215, 215, 1)"
                    sub = "rgba(146, 146, 146, 1)"
                }
            }

            if (achievement.type == AchievementType.ANNIVERSARY) {
                when (achievement.text) {
                    "Year", "Years" -> {
                        // GOLD
                        color = "rgba(201, 176, 55, 1)"
                        sub = "rgba(120, 102, 23, 1)"
                    }
                    "Month", "Months" -> {
                        // SILVER
                        color = "rgba(215, 215, 215, 1)"
                        sub = "rgba(146, 146, 146, 1)"
                    }
                    else -> {
                        color = "rgba(173, 138, 86, 1)"
                        sub = "rgba(121, 98, 63, 1)"
                    }
                }
            }

            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedSince = achievement.since?.format(formatter)

            val gradientID = "gradient-" + getID(10)

            return "<svg viewBox=\"0 0 64 64\">" +
                    "<defs>" +
                        "<linearGradient gradientUnits=\"userSpaceOnUse\" x1=\"325\" y1=\"197.804\" x2=\"325\" y2=\"250.97\" id=\"" + gradientID + "\" spreadMethod=\"reflect\" gradientTransform=\"matrix(1.661548, 0, 0, 1.791682, -215.004238, -178.129272)\">" +
                            "<stop offset=\"0\" style=\"stop-color: " + color + "\"></stop>" +
                            "<stop offset=\"1\" style=\"stop-color: " + sub + "\"></stop>" +
                        "</linearGradient>" +
                        "<style bx:fonts=\"Black Han Sans\">@import url(https://fonts.googleapis.com/css2?family=Black+Han+Sans%3Aital%2Cwght%400%2C400&amp;display=swap);</style>" +
                        "<style data-bx-fonts=\"Abel\">@import url(https://fonts.googleapis.com/css2?family=Abel%3Aital%2Cwght%400%2C400&amp;display=swap);</style>" +
                    "</defs>" +
                    "<path d=\"M 317.585 199.432 Q 325 196.176 332.415 199.432 L 342.301 203.773 Q 349.716 207.029 351.547 214.344 L 353.989 224.098 Q 355.82 231.414 350.689 237.281 L 343.848 245.103 Q 338.716 250.97 330.487 250.97 L 319.513 250.97 Q 311.284 250.97 306.152 245.103 L 299.311 237.281 Q 294.18 231.414 296.011 224.098 L 298.453 214.344 Q 300.284 207.029 307.699 203.773 Z\" style=\"fill: url(#" + gradientID + "); fill-opacity: 0.69; stroke-miterlimit: 6; paint-order: stroke; stroke: " + color + ";\" transform=\"matrix(0.982629, 0.185583, -0.185583, 0.982629, -245.893842, -248.565304)\" bx:shape=\"n-gon 325 225 31.613 28.824 7 0.3 1@7f76b11c\"></path>" +
                    "<text style=\"fill: url(#" + gradientID + "); fill-opacity: 0.77; font-family: &quot;Black Han Sans&quot;; font-size: " + size + "px; paint-order: stroke; stroke-width: 0px; white-space: pre;\" x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\">" + achievement.count + "</text>" +
                    "<text style=\"fill: rgb(51, 51, 51); fill-opacity: 0.7; font-family: &quot;Black Han Sans&quot;; font-size: 7.4px; text-transform: capitalize; white-space: pre-wrap; \" x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\">" +
                        span +
                    "</text>" +
                    "<text style=\"fill: rgb(51, 51, 51); font-family: Abel; font-size: 5px; text-transform: capitalize; white-space: pre;\" x=\"50%\" y=\"75%\" dominant-baseline=\"middle\" text-anchor=\"middle\">" + achievement.text + "</text>" +
                    "<text style=\"fill: rgb(51, 51, 51); font-family: Abel; font-size: 5px; text-transform: capitalize; white-space: pre;\" x=\"50%\" y=\"83%\" dominant-baseline=\"middle\" text-anchor=\"middle\">" + formattedSince + "</text>" +
                   "</svg>"
        }

        fun addTSpan(txt: String, dy: Int): String {
            return "<tspan x=\"50%\" dy=\"" + dy + "\" >" + txt + "</tspan>"
        }

        fun getID(length: Int) : String {
            val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
            return (1..length)
                .map { charset.random() }
                .joinToString("")
        }
    }
}
