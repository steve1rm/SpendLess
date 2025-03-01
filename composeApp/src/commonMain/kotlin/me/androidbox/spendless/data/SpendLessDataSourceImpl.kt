package me.androidbox.spendless.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.data.SpendLessDatabase
import me.androidbox.spendless.data.Transaction

class SpendLessDataSourceImpl(
    private val database: SpendLessDatabase
): SpendLessDataSource {
    override suspend fun insertUser(user: User) {
        database.userDao().insertUser(user)
    }

    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return database.transactionDao().getAll()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        database.transactionDao().insertTransaction()
    }

<<<<<<< HEAD

=======
>>>>>>> 1f1e1d1 (set up CreateTransactionUseCase)
//    override suspend fun getTopicWithPrefix(title: String): List<Transaction> {
//        return database.transactionDao().getTransactionByCategory(title)
//    }

//    override suspend fun insertTopic(topic: Topic) {
//        database.topicDao().insertAll(topic)
//    }
//
//    override fun getAllJournal(): Flow<List<SpendLessUI>> {
//        return database.journalDao().getAll().map { listOfJournals ->
//            listOfJournals.map { journal ->
//                SpendLessUI(
//                    title = journal.title.orEmpty(),
//                    description = journal.description.orEmpty(),
//                    audioFilePath = journal.audioFilePath.orEmpty(),
//                    topics = journal.topics ?: listOf(),
//                    emotion = getEmotionMoodsFilled(journal.emotion.orEmpty()),
//                    audioDuration = journal.audioDuration,
//                    date = journal.createdAt,
//                )
//            }
//        }
//    }
//
//    override suspend fun insertJournal(journal: Journal) {
//        database.journalDao().insertAll(journal)
//    }
}