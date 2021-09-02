package pessoto.android.mercadofinanceiro.util.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.pessoto.mercadofinanceiro.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var layout: ConstraintLayout
    private var goingToBackground = true

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout

    }

    override fun onResume() {
        super.onResume()
        goingToBackground = true
        try {
            (window.decorView as ViewGroup).removeView(layout)
        } catch (e: Exception) {
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            if (goingToBackground)
                (window.decorView as ViewGroup).addView(layout)
        } catch (e: Exception) {
        }
    }

    override fun finish() {
        super.finish()
        goingToBackground = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goingToBackground = false
    }
}