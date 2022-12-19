package nl.gauket.performer

import java.time.Duration
import java.time.LocalDateTime

class Achievement {
    constructor(event: String, type: AchievementType) {
        this.event = event
        this.type = type
    }
    constructor(event: String, type: AchievementType, count: Int, since: LocalDateTime, last: LocalDateTime) {
        this.event = event
        this.type = type
        this.count = count
        this.since = since
        this.last = last
    }

    var event: String
    var type: AchievementType
    var count: Int = 0
    var since: LocalDateTime? = LocalDateTime.now()
    var last: LocalDateTime? = null;

    fun update() {
        // basic type just counts
        if (this.type == AchievementType.BASIC) {
            this.count++
        }

        // daily type count on streak otherwise it resets
        if (this.type == AchievementType.DAILY) {
            if (this.last == null || isStreak()) {
                this.count++;
            } else {
                this.count = 1
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
}
