package com.example.thirstyfriend

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, android.R.color.darker_gray)
        strokeWidth = 20f
        isAntiAlias = true
    }

    private val progressPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.progress_color)
        strokeWidth = 20f
        isAntiAlias = true
    }

    private val rectF = RectF()
    private var progress = 0f
    private var maxProgress = 1000f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = 40f
        rectF.set(padding, padding, width - padding, height - padding)

        // Dibuja el fondo del círculo
        canvas.drawArc(rectF, 0f, 360f, false, backgroundPaint)

        // Dibuja el progreso
        val sweepAngle = (progress / maxProgress) * 360f
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)
    }

    fun setProgress(value: Int) {
        progress = value.toFloat()
        invalidate() // Redibuja el círculo
    }

    fun setMax(value: Int) {
        maxProgress = value.toFloat()
    }
}
