package mobileapps.mobile_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    override fun finish() {
        super.finish()
        onLeaveThisActivity()
    }

    private fun onLeaveThisActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        onStartNewActivity()
    }

    override fun startActivity(intent: Intent, options: Bundle?) {
        super.startActivity(intent, options)
        onStartNewActivity()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        onStartNewActivity()
    }

    override fun startActivityForResult(
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ) {
        super.startActivityForResult(intent, requestCode, options)
        onStartNewActivity()
    }

    private fun onStartNewActivity() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }
}