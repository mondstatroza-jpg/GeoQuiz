package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Массив вопросов
    private val questionBank = listOf(
        "Canberra is the capital of Australia.",
        "The Pacific Ocean is larger than the Atlantic Ocean.",
        "The Suez Canal connects the Red Sea and the Indian Ocean.",
        "The source of the Nile River is in Egypt.",
        "The Amazon River is the longest river in the Americas.",
        "Lake Baikal is the world's oldest and deepest freshwater lake."
    )

    // Массив ответов (true = TRUE, false = FALSE)
    private val answerBank = listOf(
        true, true, true, false, false, true
    )

    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btnTrue = findViewById<Button>(R.id.btnTrue)
        val btnFalse = findViewById<Button>(R.id.btnFalse)
        val btnNext = findViewById<Button>(R.id.btnNext)

        // 1. Функция показа результата (ПЕРЕСТАВЛЕНА НАВЕРХ!)
        fun showResult() {
            Snackbar.make(
                findViewById(R.id.rootLayout),
                "Правильных ответов: $score из ${questionBank.size}",
                Snackbar.LENGTH_LONG
            ).show()
        }

        // 2. Функция обновления вопроса
        fun updateQuestion() {
            if (currentIndex < questionBank.size) {
                tvQuestion.text = questionBank[currentIndex]
                // Показываем кнопки ответов
                btnTrue.visibility = View.VISIBLE
                btnFalse.visibility = View.VISIBLE
                // Показываем кнопку Next, но делаем её неактивной
                btnNext.visibility = View.VISIBLE
                btnNext.isEnabled = false
            }
        }

        // 3. Функция проверки ответа
        fun checkAnswer(userAnswer: Boolean) {
            val correctAnswer = answerBank[currentIndex]
            if (userAnswer == correctAnswer) {
                score++
            }

            // Скрываем кнопки TRUE и FALSE
            btnTrue.visibility = View.GONE
            btnFalse.visibility = View.GONE

            // Активируем кнопку Next
            btnNext.isEnabled = true

            // Если это последний вопрос — скрываем Next и показываем результат
            if (currentIndex == questionBank.size - 1) {
                btnNext.visibility = View.GONE
                showResult() // Теперь ошибки не будет, функция уже объявлена выше
            }
        }

        // Назначаем обработчики нажатий
        btnTrue.setOnClickListener { checkAnswer(true) }
        btnFalse.setOnClickListener { checkAnswer(false) }

        btnNext.setOnClickListener {
            currentIndex++
            updateQuestion()
        }

        // Запускаем первый вопрос при старте
        updateQuestion()
    }
}