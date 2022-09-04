package gus.kartoffel.myquizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gus.kartoffel.myquizzapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var resultsBinding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         resultsBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultsBinding.root)
        resultsBinding.tvName.text = intent.getStringExtra(Constants.userName)
        resultsBinding.tvScore.text = "Your score is ${intent.getIntExtra(Constants.correctAnswers, 0)} out of ${intent.getIntExtra(Constants.totalQuestions, 0)}"
        resultsBinding.btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}