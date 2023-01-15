package edu.miu.myquiz

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.miu.myquiz.dao.AnswerDao
import edu.miu.myquiz.dao.QuestionDao
import edu.miu.myquiz.entity.Answer
import edu.miu.myquiz.entity.Question

@Database(entities = [Question::class, Answer::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
}