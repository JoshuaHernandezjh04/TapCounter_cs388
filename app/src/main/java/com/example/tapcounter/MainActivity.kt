package com.example.tapcounter

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var tapCount = 0
    private var tapValue = 1
    private var upgraded = false
    private var goal = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // restore if app was recreated (rotation, etc.)
        tapCount = savedInstanceState?.getInt("tapCount") ?: tapCount
        tapValue = savedInstanceState?.getInt("tapValue") ?: tapValue
        upgraded = savedInstanceState?.getBoolean("upgraded") ?: upgraded
        goal = savedInstanceState?.getInt("goal") ?: goal

        val tvCount = findViewById<TextView>(R.id.tvCount)
        val tvGoal = findViewById<TextView>(R.id.tvGoal)
        val tvTapValue = findViewById<TextView>(R.id.tvTapValue)

        val btnTap = findViewById<ImageButton>(R.id.btnTap)
        val btnUpgrade = findViewById<Button>(R.id.btnUpgrade)

        fun refreshUI() {
            tvCount.text = tapCount.toString()
            tvGoal.text = "Goal: $goal"
            tvTapValue.text = "Tap Value: $tapValue"

            btnUpgrade.isEnabled = !upgraded && tapCount >= 100
            btnUpgrade.text = if (upgraded) "Upgrade Purchased" else "Buy Upgrade (100 taps)"
        }

        btnTap.setOnClickListener {
            tapCount += tapValue

            // goal logic (increasing difficulty)
            if (tapCount >= goal) {
                Toast.makeText(this, "Goal reached! New goal: ${goal * 2}", Toast.LENGTH_SHORT).show()
                goal *= 2
            }

            refreshUI()
        }

        btnUpgrade.setOnClickListener {
            if (!upgraded && tapCount >= 100) {
                tapCount -= 100
                tapValue = 2
                upgraded = true
                Toast.makeText(this, "Upgrade purchased! Taps now count as 2.", Toast.LENGTH_SHORT).show()
                refreshUI()
            }
        }

        refreshUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tapCount", tapCount)
        outState.putInt("tapValue", tapValue)
        outState.putBoolean("upgraded", upgraded)
        outState.putInt("goal", goal)
    }
}
