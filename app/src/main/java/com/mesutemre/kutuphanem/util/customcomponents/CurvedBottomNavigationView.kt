package com.mesutemre.kutuphanem.util.customcomponents

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.*
import androidx.core.content.ContextCompat
import com.mesutemre.kutuphanem.R

class CurvedBottomNavigationView(context: Context, attrs: AttributeSet):BottomNavigationView(context,attrs) {

    private lateinit var mPath: Path;
    private lateinit var mPaint: Paint;

    private val CURVE_CIRCLE_RADIUS = 110 / 2;

    private var mFirstCurveStartPoint: Point = Point();
    private var mFirstCurveEndPoint: Point = Point();
    private var mFirstCurveControlPoint1: Point = Point();
    private var mFirstCurveControlPoint2: Point = Point();

    private var mSecondCurveStartPoint: Point = Point();
    private var mSecondCurveEndPoint: Point = Point();
    private var mSecondCurveControlPoint1: Point = Point();
    private var mSecondCurveControlPoint2: Point = Point();

    private var mNavigationBarWidth:Int = 0;
    private var mNavigationBarHeight:Int = 0;

    init {
        this.init();
    }

    private fun init():Unit{
        mPath = Path();
        mPaint = Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        val colors = IntArray(3);
        colors[0] = ContextCompat.getColor(context,
            R.color.bottom_end_color);
        colors[1] =  ContextCompat.getColor(context,
            R.color.bottom_center_color);
        colors[2] = ContextCompat.getColor(context,
            R.color.bottom_start_color);

        val positions = FloatArray(3); //floatArrayOf(0f, 0.3f, 0.6f);
        positions[0] = 0f;
        positions[1] = 0.2f;
        positions[2] = 0.4f;
        mPaint.setShader(LinearGradient(0f, 0f, measuredWidth.toFloat(),0f,
            colors,
            positions,
            Shader.TileMode.CLAMP));

        mPaint.setShader(LinearGradient(0f, 0f, 0f,250f,
            colors,positions,
            Shader.TileMode.MIRROR));
        //mPaint.setColor(ContextCompat.getColor(getContext(), R.color.gri));

        setBackgroundColor(Color.TRANSPARENT);

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh);

        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();

        mFirstCurveStartPoint.set((mNavigationBarWidth / 2) - (CURVE_CIRCLE_RADIUS * 2) - (CURVE_CIRCLE_RADIUS / 3), 0);
        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS /4));
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint;
        mSecondCurveEndPoint.set((mNavigationBarWidth / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3), 0);

        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4), mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(mFirstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS *2) + CURVE_CIRCLE_RADIUS, mFirstCurveEndPoint.y);

        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS, mSecondCurveStartPoint.y);
        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)), mSecondCurveEndPoint.y);

        mPath.reset();
        mPath.moveTo(0F, 0F);
        mPath.lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat());

        mPath.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat());
        mPath.cubicTo(mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(), mSecondCurveEndPoint.y.toFloat());

        mPath.lineTo(mNavigationBarWidth.toFloat(), 0F);
        mPath.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat());
        mPath.lineTo(0F, mNavigationBarHeight.toFloat());
        mPath.close();
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom);
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas);
        canvas?.drawPath(mPath, mPaint);
    }
}