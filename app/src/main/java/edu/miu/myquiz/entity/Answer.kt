package edu.miu.myquiz.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "_answer", foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("qid"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class Answer(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,

    val qid: Long,

    @ColumnInfo(name = "question") val option: String?,

    val correct: Boolean

)
