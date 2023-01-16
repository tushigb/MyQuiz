package edu.miu.myquiz.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.room.Room
import edu.miu.myquiz.AppDatabase
import edu.miu.myquiz.R
import edu.miu.myquiz.entity.Answer
import edu.miu.myquiz.entity.Question
import kotlinx.android.synthetic.main.quiz_activity.*


class QuizActivity : AppCompatActivity() {

    var count = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.quiz_activity)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "quiz"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        val questionDao = db.questionDao()
        val answerDao = db.answerDao()

        val questions: List<Question> = questionDao.getAll()
        val answers: List<Answer> = answerDao.getAllByQuestion(questions.get(count).id)

        question_count.setText("Question " + (count + 1) + "/15");
        question.setText(questions.get(count).question);

        createButtonDynamically(answers)

        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                counter.setText("Seconds Remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                answer_layout.removeAllViews()
                count++
                if (count <= 14) {
                    question_count.setText("Question " + (count + 1) + "/15")
                    question.setText(questions.get(count).question)
                    val answers: List<Answer> = answerDao.getAllByQuestion(questions.get(count).id)
                    createButtonDynamically(answers)
                    start()
                } else {
                    counter.setText("Finish!")
                }
            }

        }.start()

    }

    private fun createButtonDynamically(answers: List<Answer>) {


        for (answer in answers) {
            val row = LinearLayout(this)
            row.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = answer.option
            button.id = answer.id.toInt()

            button.setOnClickListener {
                println("Hello World")
            }

            row.addView(button)

            answer_layout.addView(row)
        }
    }

}