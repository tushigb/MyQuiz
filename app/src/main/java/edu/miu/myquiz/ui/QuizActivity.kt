package edu.miu.myquiz.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import edu.miu.myquiz.AppDatabase
import edu.miu.myquiz.R
import edu.miu.myquiz.dao.AnswerDao
import edu.miu.myquiz.entity.Answer
import edu.miu.myquiz.entity.Question
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.quiz_activity.*
import kotlinx.android.synthetic.main.quiz_activity.badge
import kotlinx.android.synthetic.main.quiz_activity.img_badge
import kotlinx.android.synthetic.main.quiz_activity.question_count


class QuizActivity : AppCompatActivity() {

    var count = 0;
    var correctCount = 0
    val chosen = arrayListOf<String>()
    val correct = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.quiz_activity)

        startTest()

        button_start.setOnClickListener {
            startTest()
        }
    }

    private fun startTest() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "quiz"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        count = 0
        correctCount = 0

        val questionDao = db.questionDao()
        val answerDao = db.answerDao()

        val questions: List<Question> = questionDao.getAll()
        val answers: List<Answer> = answerDao.getAllByQuestion(questions.get(count).id)

        question_count.setText("Question " + (count + 1) + "/15");
        question.setText(questions.get(count).question);

        createButtonDynamically(answers)

        card_quiz.visibility = View.VISIBLE
        finish_card.visibility = View.GONE
        button_start.visibility = View.GONE
        button_result.visibility = View.GONE

        object : CountDownTimer(1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                counter.setText("Seconds Remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                answer_layout.removeAllViews()
                count++
                if (count <= 14) {
                    chosen.add(text_answer.text.toString())
                    text_answer.text = ""
                    question_count.text = "Question " + (count + 1) + "/15"
                    question.text = questions.get(count).question
                    val answers: List<Answer> = answerDao.getAllByQuestion(questions.get(count).id)
                    createButtonDynamically(answers)
                    start()
                } else {
                    createAnalysisDynamically(questions)
                    counter.setText("Finish!")
                    card_quiz.visibility = View.GONE
                    finish_card.visibility = View.VISIBLE
                    button_start.visibility = View.VISIBLE
                    button_result.visibility = View.VISIBLE

                    var percent = correctCount * 100 / 15
                    if (percent >= 80) {
                        message.text = "Congratulations! You have scored"
                        badge.visibility = View.VISIBLE
                        img_badge.visibility = View.VISIBLE
                    } else {
                        message.text = "Sorry! You did not make it"
                        badge.visibility = View.GONE
                        img_badge.visibility = View.GONE
                    }
                    percentage.text = percent.toString() + "%"
                }
            }

        }.start()
    }

    private fun createAnalysisDynamically(questions: List<Question>) {
        questions.forEachIndexed { idx, q ->
            val row = LinearLayout(this)
            row.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val text = TextView(this)

            text.text = q.question
            text.setTextColor(Color.BLACK)
            text.textSize = 8f

            row.addView(text)
            results.addView(row)
        }
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

            if (answer.correct) answer.option?.let { correct.add(it) }

            button.setOnClickListener {
                if (answer.correct) correctCount++;
                text_answer.text = answer.option
            }

            row.addView(button)

            answer_layout.addView(row)
        }
    }

}