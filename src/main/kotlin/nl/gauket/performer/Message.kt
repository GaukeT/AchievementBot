package nl.gauket.performer

data class Message(val event: String, val type: AchievementType, val state: String = "")
