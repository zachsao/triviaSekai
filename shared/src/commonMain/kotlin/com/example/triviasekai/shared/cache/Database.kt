package com.example.triviasekai.shared.cache

import com.example.triviasekai.shared.model.Difficulty
import com.example.triviasekai.shared.model.HighScore

internal class Database(factory: DatabaseDriverFactory) {
    private val database = AppDatabase(factory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getHighScores(): List<HighScore> {
        return dbQuery.selectAllHighScores(::mapHighScores).executeAsList()
    }

    internal fun insertHighScore(highScore: HighScore) {
        dbQuery.transaction {
            dbQuery.insertHighScore(
                highScore.score.toLong(),
                highScore.category,
                highScore.difficulty.name
            )
        }
    }

    internal fun updateHighScore(highScore: HighScore) {
        dbQuery.transaction {
            dbQuery.updateHighScore(
                highScore.score.toLong(),
                highScore.category,
                highScore.difficulty.name
            )
        }
    }

    private fun mapHighScores(score: Long, category: String, difficulty: String): HighScore {
        return HighScore(category, score.toInt(), Difficulty.valueOf(difficulty))
    }
}