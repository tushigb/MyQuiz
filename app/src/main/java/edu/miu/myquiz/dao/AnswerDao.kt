package edu.miu.myquiz.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.miu.myquiz.entity.Answer

@Dao
interface AnswerDao {

    @Query("SELECT * FROM _answer")
    fun getAll(): List<Answer>

    @Insert
    fun insertAll(vararg answers: Answer)

    @Query("DELETE FROM _answer")
    fun nukeTable()

}