package com.example.ball.levels_data.level2

import android.graphics.*
import com.example.ball.levels_data.IDrawable
import com.example.ball.levels_data.IMovable
import com.example.ball.levels_data.PlayerBall
import java.util.ArrayList
import kotlin.math.max

class MazeL3(val max_X: Float, val max_Y: Float) : IDrawable {
    val rects = ArrayList<RectF>()
    init {
        rects.add(RectF(0f, 1220f, 300f, max_Y))
        rects.add(RectF(300f, 1320f, 700f, 1420f))
        rects.add(RectF(max_X-100, 1420f, max_X, 890f))
        rects.add(RectF(0f, 1000f, 500f, 900f))
        rects.add(RectF(max_X-300, 1100f, max_X-400, 300f))
        rects.add(RectF(max_X-300, 300f, max_X-200, 400f))
        rects.add(RectF(300f, 0f, 500f, 400f))
        rects.add(RectF(150f, 400f, 400f, 200f))
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
        rects.forEach { canvas.drawRect(it, paint) }
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        rects.forEach {
            if(it.intersects(playerBall.hitBox.left,playerBall.hitBox.top, playerBall.hitBox.right, playerBall.hitBox.bottom))
                return true
        }
        return false
    }
}

class Enemy1L3(max_X: Float, max_Y: Float): IDrawable {
    val points = ArrayList<PointF>()
    val hitBox: RectF
    init {
        points.add(PointF(500f,0f))
        points.add(PointF(600f,100f))
        points.add(PointF(700f,25f))
        points.add(PointF(800f,75f))
        points.add(PointF(900f,25f))
        points.add(PointF(1000f,100f))
        points.add(PointF(max_X,25f))
        points.add(PointF(max_X,0f))
        points.add(PointF(500f,0f))
        hitBox = RectF(500f, 0f, max_X, 100f)
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.rgb(140,0,20)
        paint.style = Paint.Style.FILL
        val path = Path()
        path.reset()
        path.moveTo(points[0].x, points[0].y)
        path.lineTo(points[1].x, points[1].y)
        path.lineTo(points[2].x, points[2].y)
        path.lineTo(points[3].x, points[3].y)
        path.lineTo(points[4].x, points[4].y)
        path.lineTo(points[5].x, points[5].y)
        path.lineTo(points[6].x, points[6].y)
        path.lineTo(points[7].x, points[7].y)
        path.lineTo(points[8].x, points[8].y)
        canvas.drawPath(path, paint)
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        return hitBox.intersects(playerBall.hitBox.left,playerBall.hitBox.top, playerBall.hitBox.right, playerBall.hitBox.bottom)
    }
}

class Enemy2L3(speed: Float): IMovable {
    val hitBox: RectF
    private var speed = speed
    val points = ArrayList<PointF>()
    init {
        points.add(PointF(75f,475f))
        points.add(PointF(95f,525f))
        points.add(PointF(125f,550f))
        points.add(PointF(95f,575f))
        points.add(PointF(125f,600f))
        points.add(PointF(95f,625f))
        points.add(PointF(75f,675f))
        points.add(PointF(55f,625f))
        points.add(PointF(25f,600f))
        points.add(PointF(55f,575f))
        points.add(PointF(25f,550f))
        points.add(PointF(55f,525f))
        hitBox = RectF(25f,475f,125f, 675f)
    }

    override fun move(max_X: Float, max_Y: Float) {
        for (i in 0 until points.size)
            points[i].y += speed
        hitBox.top += speed
        hitBox.bottom += speed
        if(hitBox.top >= 0) speed = -speed
        if(hitBox.bottom  <= 900) speed= -speed
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.rgb(140,0,20)
        paint.style = Paint.Style.FILL
        val path = Path()
        path.reset()
        path.moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size)
            path.lineTo(points[i].x, points[i].y)
        path.lineTo(points[0].x, points[0].y)
        path.setLastPoint(points[0].x, points[0].y)
        canvas.drawPath(path, paint)
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        return hitBox.intersects(playerBall.hitBox.left,playerBall.hitBox.top, playerBall.hitBox.right, playerBall.hitBox.bottom)
    }
}

class Enemy22L3(speed: Float): IMovable {
    val hitBox: RectF
    private var speed = speed
    val points = ArrayList<PointF>()

    init {
        points.add(PointF(575f, 475f))
        points.add(PointF(595f, 525f))
        points.add(PointF(625f, 550f))
        points.add(PointF(595f, 575f))
        points.add(PointF(625f, 600f))
        points.add(PointF(595f, 625f))
        points.add(PointF(575f, 675f))
        points.add(PointF(555f, 625f))
        points.add(PointF(525f, 600f))
        points.add(PointF(555f, 575f))
        points.add(PointF(525f, 550f))
        points.add(PointF(555f, 525f))
        hitBox = RectF(525f, 475f, 625f, 675f)
    }

    override fun move(max_X: Float, max_Y: Float) {
        for (i in 0 until points.size)
            points[i].y += speed
        hitBox.top += speed
        hitBox.bottom += speed
        if (hitBox.top >= 75) speed = -speed
        if (hitBox.bottom <= 1320) speed = -speed
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        val path = Path()
        path.reset()
        path.moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size)
            path.lineTo(points[i].x, points[i].y)
        path.lineTo(points[0].x, points[0].y)
        path.setLastPoint(points[0].x, points[0].y)
        canvas.drawPath(path, paint)
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        return hitBox.intersects(
            playerBall.hitBox.left,
            playerBall.hitBox.top,
            playerBall.hitBox.right,
            playerBall.hitBox.bottom
        )
    }
}

class PortalInL3(playerR: Float): IDrawable {
    val hitBox: RectF
    private var x : Float
    private var y : Float
    private var r : Float
    init {
        x = 100f
        y = 1100f
        r = playerR + 20f
        hitBox = RectF(x-r,y-r, x+r, y+r)
    }
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, r, paint)
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        return hitBox.contains(playerBall.hitBox)
    }
}

class PortalOutL3(playerR: Float): IDrawable {
    val hitBox: RectF
    private var x : Float
    private var y : Float
    private var r : Float
    init {
        x = 300f
        y = 500f
        r = playerR + 20f
        hitBox = RectF(x-r,y-r, x+r, y+r)
    }
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, r, paint)
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        return false
    }
}

class FinishL3(playerR: Float): IDrawable {
    val hitBox: RectF
    private var x : Float
    private var y : Float
    private var r : Float
    init {
        x = 225f
        y = 100f
        r = playerR + 20f
        hitBox = RectF(x-r,y-r, x+r, y+r)
    }
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, r, paint)
    }

    override fun contactPlayer(playerBall: PlayerBall): Boolean {
        return hitBox.contains(playerBall.hitBox)
    }
}
