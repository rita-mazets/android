package com.example.ball.levels_data.level2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.example.ball.levels_data.IDrawable
import com.example.ball.levels_data.IMovable
import com.example.ball.levels_data.Level
import com.example.ball.levels_data.PlayerBall
import java.util.ArrayList

class Level3(context: Context) : Level(context) {
    override var isFinished: Boolean = false
    override var score: Float = 0f
    override var countDeath: Int = 0
    override var elapsedTime: Long = System.currentTimeMillis()
    private var cheatMode: Boolean = false
    private var cheatModeCount: Int = 0
    val max_X : Float
    val max_Y : Float
    var paint: Paint
    var playerBall: PlayerBall
    val iMovable = ArrayList<IMovable>()
    val iDrawable = ArrayList<IDrawable>()

    init {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        max_X = metrics.widthPixels.toFloat()
        max_Y = metrics.heightPixels.toFloat()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = 3f

        val paint = Paint()
        paint.color = Color.RED
        playerBall = PlayerBall(350f, max_Y-50, 50f, paint)

        iDrawable.add(MazeL3(max_X,max_Y))
        iDrawable.add(Enemy1L3(max_X, max_Y))
        iDrawable.add(PortalInL3(playerBall.r))
        iDrawable.add(PortalOutL3(playerBall.r))
        iDrawable.add(FinishL3(playerBall.r))
        iMovable.add(Enemy2L3(3f))
        iMovable.add(Enemy22L3(10f))

        iMovable.add(playerBall)
        iDrawable.addAll(iMovable)
    }

    override fun draw(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)
        paint.style = Paint.Style.STROKE
        this.iDrawable.forEach { it.draw(canvas) }
    }

    override fun update(x: Float, y: Float) {
        playerBall.move(0.5f * x, 0.5f * y, max_X, max_Y)
        iMovable.forEach{ it.move(max_X, max_Y) }
        iDrawable.forEach {
            if(it !is PlayerBall){
                if(it.contactPlayer(playerBall))
                    when(it){
                        is MazeL3 ->{
                            playerBall.move(-0.5f * x, -0.5f * y, max_X, max_Y)
                        }
                        is Enemy1L3,
                        is Enemy2L3,
                        is Enemy22L3 ->{
                            if(!cheatMode){
                                playerBall.restart()
                                countDeath++
                                if (it is Enemy1L2) {
                                    cheatModeCount++
                                    if(cheatModeCount >= 0){
                                        cheatMode = true
                                        var paint = Paint()
                                        paint.style = Paint.Style.FILL
                                        paint.color = Color.YELLOW
                                        playerBall.paint = paint
                                    }
                                }
                            }

                        }
                        is PortalInL3 ->{
                            playerBall.xc = 300f
                            playerBall.yc = 500f
                        }
                        is FinishL3 ->{
                            elapsedTime = System.currentTimeMillis() - elapsedTime
                            val maxScore = 200f
                            score = (1f/((elapsedTime/1000f + 10f*countDeath)/60f))*maxScore
                            if(score>maxScore)
                                score = maxScore
                            isFinished = true
                        }
                    }
            }
        }
    }
}