package gus.kartoffel.myquizzapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import gus.kartoffel.myquizzapp.databinding.ActivityQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuestionsBinding
    private var mCurrentPosition: Int = 0
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedPosition: Int = 0
    private var optionSelected = 0
    private var answerSubmitted = false
    private var correctlyAnswered = 0
    private var mUserName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserName = intent.getStringExtra(Constants.userName)
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
        mQuestionsList = Constants.getQuestions()
        setQuestion()

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            binding.tvOptionOne.id -> {
                selectedOptionView(binding.tvOptionOne, 1)
            }
            binding.tvOptionTwo.id -> {
                selectedOptionView(binding.tvOptionTwo, 2)
            }
            binding.tvOptionThree.id -> {
                selectedOptionView(binding.tvOptionThree, 3)
            }
            binding.tvOptionFour.id -> {
                selectedOptionView(binding.tvOptionFour, 4)
            }
            binding.btnSubmit.id -> {
                if(mSelectedPosition!=0 && optionSelected ==0){
                    val question = mQuestionsList?.get(mCurrentPosition)
                    if(question!!.correctAnswer != mSelectedPosition){
                        answerView(mSelectedPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        correctlyAnswered++
                    }
                        answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                        answerSubmitted = true

                    if(mCurrentPosition == mQuestionsList!!.size){
                        binding.btnSubmit.text = "FINISH"
                    }else{
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedPosition = 0
                    optionSelected ++
                    mCurrentPosition++

                }else if (optionSelected!=0){
                    when{
                        mCurrentPosition +1 <= mQuestionsList!!.size -> {
                            optionSelected = 0
                            answerSubmitted = false

                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.userName, mUserName)
                            intent.putExtra(Constants.correctAnswers, correctlyAnswered)
                            intent.putExtra(Constants.totalQuestions, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }

    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer){
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(this,
                    drawableView
                )
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(this,
                    drawableView
                )
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(this,
                    drawableView
                )
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(this,
                    drawableView
                )
            }
        }
    }



    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition]
        binding.ivImage.setImageResource(question.image)
        binding.progressBar.progress = mCurrentPosition + 1
        binding.tvProgress.text = "${mCurrentPosition + 1} / ${binding.progressBar.max}"
        binding.tvQuestion.text = question.questions
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
            binding.btnSubmit.text = "SUBMIT"
        }

    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        binding.tvOptionOne.let {
            options.add(0, it)
        }
        binding.tvOptionTwo.let {
            options.add(1, it)
        }
        binding.tvOptionThree.let {
            options.add(2, it)
        }
        binding.tvOptionFour.let {
            options.add(3, it)
        }

        for (i in options) {
            i.setTextColor(Color.parseColor("#7a8089"))
            i.typeface = Typeface.DEFAULT
            i.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        if (!answerSubmitted) {
            defaultOptionsView()
            mSelectedPosition = selectedOptionNum
            tv.setTextColor(Color.parseColor("#363a43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.selected_option_border_bg
            )
        }
    }
}
