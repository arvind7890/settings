package zzub.arvind.buzz.linesettings;

import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
//import android.util.Log;


public class PatternDrawable extends Drawable {
    private Paint mPaint;
    private Path mPath;

    private int lineColor;
    private int lineAlpha;
    private int lineWidth;
    private float dashLength;
    private float gapLength;
    private float hue;
    private float saturation;
    private float value;


    public PatternDrawable(int lineColor, int lineAlpha, int lineWidth, float dashLength, float gapLength) {
        //Log.e("CONSTRUCT","PatternDrawable(int lineColor, int lineAlpha, int lineWidth, float dashLength, float gapLength)");
        this.lineColor = lineColor;
        this.lineAlpha = lineAlpha;
        this.lineWidth = lineWidth;
        this.dashLength = dashLength;
        this.gapLength = gapLength;
        initPaint();
    }
    public PatternDrawable() {
        this.lineColor=Color.BLUE;
        this.lineAlpha=155;
        this.lineWidth=30;
        this.dashLength=30f;
        this.gapLength=10f;

        //Log.e("CONSTRUCT","PatternDrawable");
        initPaint();
        setLineColor(Color.BLUE);
    }
    private void initPaint(){
        mPaint=new Paint();
        mPath=new Path();
        mPaint.setColor(this.lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAlpha(lineAlpha);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setPathEffect(new DashPathEffect(new float[]{this.dashLength,this.gapLength},0));
        mPaint.setAntiAlias(true);
    }
    @Override
    public void draw(Canvas canvas) {
        int height = getBounds().height();
        int width = getBounds().width();
        mPath.moveTo(0,height/2);
        mPath.lineTo(width,height/2);
        canvas.drawPath(mPath,mPaint);

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter( ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return lineAlpha;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        mPaint.setColor(this.lineColor);
        float[] hsv=new float[3];
        Color.colorToHSV(lineColor,hsv);
        this.hue=hsv[0];
        this.saturation=hsv[1];
        this.value=hsv[2];
    }

    public int getLineAlpha() {
        return lineAlpha;
    }

    public void setLineAlpha(int lineAlpha) {
        this.lineAlpha = lineAlpha;
        mPaint.setAlpha(lineAlpha);
    }

    public float getHue(){
        return hue;
    }
    public float getSaturation(){
        return saturation;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        mPaint.setStrokeWidth(lineWidth);
    }

    public float getDashLength() {
        return dashLength;
    }

    public void setDashLength(float dashLength) {
        this.dashLength = dashLength;
        //Log.e("ONSETDASH","setDashLength");
        mPaint.setPathEffect(new DashPathEffect(new float[]{this.dashLength,this.gapLength},0));
    }

    public float getGapLength() {
        return gapLength;
    }

    public void setGapLength(float gapLength) {
        this.gapLength = gapLength;
        mPaint.setPathEffect(new DashPathEffect(new float[]{this.dashLength,this.gapLength},0));
    }

    @Override
    public String toString() {
        return "PatternDrawable{" +
                "lineColor=" + lineColor +
                ", lineAlpha=" + lineAlpha +
                ", lineWidth=" + lineWidth +
                ", dashLength=" + dashLength +
                ", gapLength=" + gapLength +
                ", hue=" + hue +
                ", saturation=" + saturation +
                ", value=" + value +
                '}';
    }

}