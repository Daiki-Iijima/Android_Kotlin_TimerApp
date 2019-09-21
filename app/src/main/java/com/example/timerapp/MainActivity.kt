package com.example.timerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val handler = Handler()

    var timeValue =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //  「activity_main」内に設置したUIを検索して参照をローカル変数に代入
        val timerText = findViewById(R.id.TimerText) as TextView
        val startButton = findViewById(R.id.StartButton) as Button
        val stopButton = findViewById(R.id.StopButton) as Button
        val resetButton = findViewById(R.id.ResetButton) as Button


        val runnable = object : Runnable{
            override fun run()
            {
                timeValue++

                // TextViewを更新
                // ?.letを用いて、nullではない場合のみ更新
                timeToText(timeValue)?.let {
                    // timeToText(timeValue)の値がlet内ではitとして使える
                    timerText.text = it
                }

                handler.postDelayed(this,1000)
            }
        }


        //  ボタンイベント

        startButton.setOnClickListener()
        {
            handler.post(runnable);
        }

        stopButton.setOnClickListener()
        {
            handler.removeCallbacks(runnable)
        }

        resetButton.setOnClickListener()
        {
            handler.removeCallbacks(runnable)

            timeValue=0

            timeToText()?.let{
                timerText.text = it
            }
        }
    }

    // 数値を00:00:00形式の文字列に変換する関数
    // 引数timeにはデフォルト値0を設定、返却する型はnullableなString?型
    private fun timeToText(time: Int = 0): String? {
        // if式は値を返すため、そのままreturnできる
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}
