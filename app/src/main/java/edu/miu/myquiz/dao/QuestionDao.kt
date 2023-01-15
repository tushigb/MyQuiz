package edu.miu.myquiz.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.miu.myquiz.entity.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM _question")
    fun getAll(): List<Question>

    @Insert
    fun insertAll(vararg questions: Question)

    @Query("DELETE FROM _question")
    fun nukeTable()

}