package de.hdmstuttgart.thelaendofadventure.data.offlinerepository

import de.hdmstuttgart.thelaendofadventure.data.dao.QuestDao
import de.hdmstuttgart.thelaendofadventure.data.dao.datahelper.LocationGoal
import de.hdmstuttgart.thelaendofadventure.data.dao.datahelper.Progress
import de.hdmstuttgart.thelaendofadventure.data.entity.ActionEntity
import de.hdmstuttgart.thelaendofadventure.data.entity.QuestEntity
import de.hdmstuttgart.thelaendofadventure.data.repository.QuestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OfflineQuestRepository(private val questDao: QuestDao) : QuestRepository {

    override fun getAcceptedQuestsByUserID(userID: Int): Flow<List<QuestEntity>> =
        questDao.getAcceptedQuestsByUserID(userID)

    override fun getUnacceptedQuestsByUserID(userID: Int): Flow<List<QuestEntity>> =
        questDao.getUnacceptedQuestsByUserID(userID)

    override fun getProgressForQuestByUserID(userID: Int, questID: Int):
            Flow<Progress> =
        questDao.getProgressForQuestByUserID(userID, questID)

    override fun getCompletedGoalsForQuestByUserID(userID: Int, questID: Int):
            Flow<List<ActionEntity>> =
        questDao.getCompletedGoalsForQuestByUserID(userID, questID)

    override fun getUncompletedGoalsForQuestByUserID(userID: Int, questID: Int):
            Flow<List<ActionEntity>> =
        questDao.getUncompletedGoalsForQuestByUserID(userID, questID)

    override suspend fun getQuestForBadgeByUserID(userID: Int, badgeID: Int): Flow<List<Int>> =
        questDao.getQuestForBadgeByUserID(userID, badgeID)

    override suspend fun updateAndCheckQuestProgressByUserID(
        userID: Int,
        questID: Int,
        goalNumber: Int
    ): Boolean {
        questDao.updateQuestProgressByUserID(userID, questID, goalNumber)
        return isQuestCompleted(userID, questID)
    }

    private suspend fun isQuestCompleted(userID: Int, questID: Int): Boolean {
        val questProgress = questDao.getProgressForQuestByUserID(userID, questID)

        var isCompleted = false

        withContext(Dispatchers.IO) {
            questProgress.collect { progress ->
                val currentGoalNumber = progress.currentGoalNumber
                val targetGoalNumber = progress.targetGoalNumber
                isCompleted = currentGoalNumber == targetGoalNumber
            }
        }

        return isCompleted
    }

    override suspend fun assignQuestToUser(userID: Int, questID: Int) {
        questDao.assignQuestToUser(userID, questID)
    }

    override fun getLocationForAcceptedQuestsByUserID(userID: Int): Flow<List<LocationGoal>> {
        return questDao.getLocationForAcceptedQuestsByUserID(userID)
    }
}
