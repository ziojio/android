package zhuj.android.test

import android.os.Bundle
import com.google.android.material.button.MaterialButton
import zhuj.android.test.base.viewmodel.ViewModelActivity

class KotlinActivity : ViewModelActivity() {
    private var testViewModel: TestViewModel? = null
    private var materialButton: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        setSupportActionBar(findViewById(R.id.toolbar))
        materialButton = findViewById(R.id.button)
        testViewModel = getActivityViewModel(TestViewModel::class.java)
        testViewModel?.stringLiveData?.observe(this, { s -> materialButton?.text = s }
        )
        doing()
    }

    private fun doing() {
        val str = """ afaf f
      fasdg
        """.trimMargin()
        materialButton!!.text = "hello world!~"
    }
}