package edu.miu.myquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.room.Room
import edu.miu.myquiz.entity.Answer
import edu.miu.myquiz.entity.Question
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "quiz"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

        val questionDao = db.questionDao()
        val answerDao = db.answerDao()

        questionDao.nukeTable()
        answerDao.nukeTable()

        val q1 = Question(1, "What is a correct syntax to output \"Hello World\" in Kotlin?")
        val q1a1 = Answer(1, 1, "System.out.printline(\"Hello World\")", false)
        val q1a2 = Answer(2, 1, "println(\"Hello World\")", true)
        val q1a3 = Answer(3, 1, "Console.WriteLine(\"Hello World\");", false)

        val q2 = Question(2, "How do you insert COMMENTS in Kotlin code?")
        val q2a1 = Answer(4, 2, "/* This is a comment", false)
        val q2a2 = Answer(5, 2, "-- This is a comment", false)
        val q2a3 = Answer(6, 2, "// This is a comment  ", true)

        val q3 = Question(3, "Which keyword is used to declare a function?")
        val q3a1 = Answer(7, 3, "fun", true)
        val q3a2 = Answer(8, 3, "function", false)
        val q3a3 = Answer(9, 3, "decl", true)

        val q4 = Question(4, "In Kotlin, code statements must end with a semicolon (;)")
        val q4a1 = Answer(10, 4, "TRUE", false)
        val q4a2 = Answer(11, 4, "FALSE", true)

        val q5 = Question(5, "How can you create a variable with the numeric value 5?")
        val q5a1 = Answer(12, 5, "num = 5 int", false)
        val q5a2 = Answer(13, 5, "val num = 5  ", true)
        val q5a3 = Answer(14, 5, "num = 5  ", true)

        val q6 = Question(6, "How can you create a variable with the floating number 2.8?")
        val q6a1 = Answer(15, 6, "float num = 2.8", false)
        val q6a2 = Answer(16, 6, "num = 2.8 float", false)
        val q6a3 = Answer(17, 6, "val num = 2.8", true)

        val q7 = Question(7, "Which operator is used to add together two values?")
        val q7a1 = Answer(18, 7, "The + sign", true)
        val q7a2 = Answer(19, 7, "The * sign", false)
        val q7a3 = Answer(20, 7, "The & sign", false)

        val q8 = Question(8, "What is the output of the following code: println(5 > 3 && 5 < 10)")
        val q8a1 = Answer(21, 8, "true", true)
        val q8a2 = Answer(22, 8, "5", false)
        val q8a3 = Answer(23, 8, "2", false)

        val q9 = Question(9, "The value of a string variable must be surrounded by single quotes.")
        val q9a1 = Answer(24, 9, "FALSE", true)
        val q9a2 = Answer(25, 9, "TRUE", false)

        val q10 = Question(10, "Which operator can be used to compare two values?")
        val q10a1 = Answer(26, 10, "==", true)
        val q10a2 = Answer(27, 10, "><", false)
        val q10a3 = Answer(28, 10, "=", false)

        val q11 = Question(11, "Which property can be used to find the length of a string?")
        val q11a1 = Answer(29, 11, "The length property", true)
        val q11a2 = Answer(30, 11, "The sizeof property", false)
        val q11a3 = Answer(31, 11, "The len property", false)

        val q12 = Question(12, "Which symbol is used for string templates/interpolation?")
        val q12a1 = Answer(32, 12, "*", false)
        val q12a2 = Answer(33, 12, ".", false)
        val q12a3 = Answer(34, 12, "$", true)

        val q13 = Question(13, "To create an array in Kotlin, use")
        val q13a1 = Answer(35, 13, "None of the above", false)
        val q13a2 = Answer(36, 13, "The arrayOf() function", true)
        val q13a3 = Answer(37, 13, "{}", false)

        val q14 = Question(14, "Array indexes start with:")
        val q14a1 = Answer(38, 14, "0", true)
        val q14a2 = Answer(39, 14, "1", false)

        val q15 = Question(15, "How do you call a function in Kotlin?")
        val q15a1 = Answer(40, 15, "myFunction()", true)
        val q15a2 = Answer(41, 15, "myFunction[]", false)
        val q15a3 = Answer(42, 15, "(myFunction)", false)

        questionDao.insertAll(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15)
        answerDao.insertAll(
            q1a1, q1a2, q1a3,
            q2a1, q2a2, q2a3,
            q3a1, q3a2, q3a3,
            q4a1, q4a2,
            q5a1, q5a2, q5a3,
            q6a1, q6a2, q6a3,
            q7a1, q7a2, q7a3,
            q8a1, q8a2, q8a3,
            q9a1, q9a2,
            q10a1, q10a2, q10a3,
            q11a1, q11a2, q11a3,
            q12a1, q12a2, q12a3,
            q13a1, q13a2, q13a3,
            q14a1, q14a2,
            q15a1, q15a2, q15a3
        )

        val questions: List<Question> = questionDao.getAll()
        val answers: List<Answer> = answerDao.getAll()

        println("::::" + questions.size)
        println("::::" + answers.size)

        for (q in questions) {
            println("-----")
            println(q)
        }

//        object : CountDownTimer(30000, 1000) {
//
//            override fun onTick(millisUntilFinished: Long) {
//                hello_world.setText("seconds remaining: " + millisUntilFinished / 1000)
//            }
//
//            override fun onFinish() {
//                hello_world.setText("done!")
//            }
//        }.start()
    }
}