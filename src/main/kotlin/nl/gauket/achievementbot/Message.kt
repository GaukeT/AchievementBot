package nl.gauket.achievementbot

data class Message(val event: String, val type: AchievementType, val state: String = "")
