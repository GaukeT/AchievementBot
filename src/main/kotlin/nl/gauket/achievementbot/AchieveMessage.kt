package nl.gauket.achievementbot

data class AchieveMessage(val event: String, val type: AchievementType, val key: String = "")
