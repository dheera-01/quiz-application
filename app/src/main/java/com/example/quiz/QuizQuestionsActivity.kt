package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.graphics.drawable.AdaptiveIconDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity() : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestion()
        //Log.i("There are","${mQuestionsList.size}")

        setQuestion()

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        tv_option_one.setOnClickListener(this)

        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        tv_option_two.setOnClickListener(this)

        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        tv_option_three.setOnClickListener(this)

        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        tv_option_four.setOnClickListener(this)

        val btn_submit = findViewById<TextView>(R.id.btn_submit)
        btn_submit.setOnClickListener(this)
    }

    private fun setQuestion(){

        defaultOptionsView()

        val question:Question? = mQuestionsList!![mCurrentPosition - 1]

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = mCurrentPosition
        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max

        val tv_question = findViewById<TextView>(R.id.tv_question)
        tv_question.text = question!!.question

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        tv_option_one.text = question.optionOne

        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        tv_option_two.text = question.optionTwo

        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        tv_option_three.text = question.optionThree

        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        tv_option_four.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        options.add(0,tv_option_one)
        options.add(1,tv_option_two)
        options.add(2,tv_option_three)
        options.add(3,tv_option_four)

        for(option in options){
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,R.drawable.default_option_border_bg
            )

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one -> {
                val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                selectedOptionView(tv_option_one,1)}

            R.id.tv_option_two -> {
                val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
                selectedOptionView(tv_option_two,2)
            }

            R.id.tv_option_three -> {
                val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
                selectedOptionView(tv_option_three,3)
            }

            R.id.tv_option_four -> {
                val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
                selectedOptionView(tv_option_four,4)
            }

            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }else -> {
                            val intent = Intent(this, resultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    mSelectedOptionPosition = 0
                }

            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 ->{
                val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
                tv_option_one.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }

            2 ->{
                val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
                tv_option_two.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }

            3 ->{
                val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
                tv_option_three.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }

            4 ->{
                val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
                tv_option_four.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv:TextView,selectedOption:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOption

        tv.typeface = Typeface.DEFAULT
        tv.background = ContextCompat.getDrawable(
            this,R.drawable.selected_option_border_bg
        )
    }
}