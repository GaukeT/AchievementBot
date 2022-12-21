package nl.gauket.achievementbot

import java.time.Duration
import java.time.LocalDateTime

class Achievement {
    constructor(event: String, type: AchievementType) {
        this.event = event
        this.type = type
        this.text = type.label
    }
    constructor(event: String, type: AchievementType, count: Int, text: String, since: LocalDateTime, last: LocalDateTime) {
        this.event = event
        this.type = type
        this.count = count
        this.text = text
        this.since = since
        this.last = last
    }

    var event: String
    var type: AchievementType
    var count: Int = 0
    var text: String = ""
    var since: LocalDateTime? = LocalDateTime.now()
    var last: LocalDateTime? = null

    fun update() {
        // basic type just counts
        if (this.type == AchievementType.BASIC) {
            this.count++
        }

        // daily type counts on streak otherwise it resets
        if (this.type == AchievementType.DAILY) {
            if (this.last == null || isStreak()) {
                this.count++
            } else {
                this.since = LocalDateTime.now()
                this.count = 1
            }
        }

        // anniversary type counts on predefined time spans
        if (this.type == AchievementType.ANNIVERSARY) {
            val today = LocalDateTime.now()
            val daysPassed =  Duration.between(this.since, today).toDays()

            this.text = isAnniversary(daysPassed)
            this.count = 1

            if (daysPassed >= 60) {
                this.count = Integer.parseInt((daysPassed / 30).toString())
            }
            if (daysPassed >= 365) {
                this.count = Integer.parseInt((daysPassed / 365).toString())
            }
        }

        // always update last used
        this.last = LocalDateTime.now()
    }

    private fun isStreak(): Boolean {
        val today = LocalDateTime.now()
        val daysPassed = Duration.between(this.last, today).toDays()
        return daysPassed == 1L
    }

    private fun isAnniversary(daysPassed: Long): String {
        val lookUp = arrayOf("Day", "Week", "Month", "Months", "Year", "Years")

        var index = 0
        if (daysPassed >= 7)
            index = 1
        if (daysPassed >= 30)
            index = 2
        if (daysPassed >= 60)
            index = 3
        if (daysPassed >= 365)
            index = 4
        if (daysPassed >= (365 * 2))
            index = 5

        return lookUp[index]
    }
}
