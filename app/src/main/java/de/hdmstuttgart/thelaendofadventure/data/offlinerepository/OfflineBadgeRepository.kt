package de.hdmstuttgart.thelaendofadventure.data.offlinerepository

import de.hdmstuttgart.thelaendofadventure.data.dao.BadgeDao
import de.hdmstuttgart.thelaendofadventure.data.dao.datahelper.Progress
import de.hdmstuttgart.thelaendofadventure.data.entity.ActionEntity
import de.hdmstuttgart.thelaendofadventure.data.entity.BadgeEntity
import de.hdmstuttgart.thelaendofadventure.data.repository.BadgeRepository
import kotlinx.coroutines.flow.Flow

class OfflineBadgeRepository(private val badgeDao: BadgeDao) : BadgeRepository {

    override fun getAcceptedBadgesByUserID(userID: Int): Flow<BadgeEntity> =
        badgeDao.getAcceptedBadgesByUserID(userID)

    override fun getUnacceptedBadgesByUserID(userID: Int): Flow<BadgeEntity> =
        badgeDao.getUnacceptedBadgesByUserID(userID)

    override fun getProgressForBadgeByUserID(userID: Int, badgeID: Int):
        Flow<Progress> = badgeDao.getProgressForBadgeByUserID(userID, badgeID)

    override fun getCompletedGoalsForBadgeByUserID(userID: Int, badgeID: Int):
        Flow<ActionEntity> = badgeDao.getCompletedGoalsForBadgeByUserID(userID, badgeID)

    override fun getUncompletedGoalsForBadgeByUserID(userID: Int, badgeID: Int):
        Flow<ActionEntity> = badgeDao.getUncompletedGoalsForBadgeByUserID(userID, badgeID)

    override suspend fun updateBadgeProgressByUserID(userID: Int, badgeID: Int, goalNumber: Int) =
        badgeDao.updateBadgeProgressByUserID(userID, badgeID, goalNumber)
}
