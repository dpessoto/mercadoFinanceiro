package pessoto.android.mercadofinanceiro.feature.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import br.com.pessoto.mercadofinanceiro.R
import pessoto.android.mercadofinanceiro.feature.recommendation.view.RecommendationActivity
import pessoto.android.mercadofinanceiro.util.view.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            startActivity(Intent(this, RecommendationActivity::class.java))
            finish()
        }, 3000)
    }
}