package zzub.arvind.buzz.linesettings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BoxedText extends View {
    //int hspace=5;
    //int vspace=5;

    float startX =20;
    float startY = 100;
    int textWidth=0;
    int textHeight=0;

    int numBox=0;
    String textMain="My Example Text";
    //int textSize=70;
    int strokeWidth=1;

    int boxTop=0;
    int boxBottom=0;
    int maxHeight=0;

    Context mcontext;
    AttributeSet mattrs;
    TextPaint mTextPaint;
    Paint mRectPaint;
    Paint mtopPaint;
    Paint mbottomPaint;
    Paint mstartyPaint;
    Paint mstopyPaint;

    public void log(String TAG, String method){

       // Log.e(TAG," calmethod "+method);
    }

    public BoxedText(Context context) {
        super(context);
        mcontext=context;
        init();
    }

    public BoxedText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext=context;
        mattrs=attrs;
        log("CONS"," BoxedText(Context context, AttributeSet attrs)");
        init();
    }

    public BoxedText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);
        mcontext=context;
        mattrs=attrs;
        init();
    }

    public BoxedText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, 0, 0);
        mcontext=context;
        mattrs=attrs;
        init();
    }

    private void initTextSize(){
        log("initTextSize","initTextSize()");
        if(maxHeight==0)return;
        int textSize=getMaxSize(maxHeight);
        mTextPaint.setTextSize(textSize);
        textWidth=(int)mTextPaint.measureText(textMain);
        //Log.e("FIT WIDTH","textWidth="+textWidth);

        Paint.FontMetrics fm=mTextPaint.getFontMetrics();
        startX=5;
        startY=(-1)*fm.top;
        boxTop=(int)(startY+fm.top);
        boxBottom=(int)(startY+fm.bottom);
        textHeight=(int)(fm.bottom - fm.top + fm.leading);
        //Log.e("FMETRIC","top="+fm.top);
        //Log.e("FMETRIC","bottom="+fm.bottom);
        //Log.e("FMETRIC","boxTop="+boxTop);
        //Log.e("FMETRIC","boxBottom="+boxBottom);
        //Log.e("FMETRIC","startX="+startX);
        //Log.e("FMETRIC","startY="+startY);
    }
    private void init() {

        log("Log","init()");
        mTextPaint=new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);

        mRectPaint=new Paint();
        mRectPaint.setColor(Color.BLACK);
        mRectPaint.setStrokeWidth(4);
        mRectPaint.setStyle(Paint.Style.STROKE);

        mtopPaint=new Paint();
        mtopPaint.setColor(Color.RED);
        mtopPaint.setStrokeWidth(strokeWidth);
        mtopPaint.setStyle(Paint.Style.STROKE);

        mbottomPaint=new Paint();
        mbottomPaint.setColor(Color.GREEN);
        mbottomPaint.setStrokeWidth(strokeWidth);
        mbottomPaint.setStyle(Paint.Style.STROKE);

        mstartyPaint=new Paint();
        mstartyPaint.setColor(Color.RED);
        mstartyPaint.setStrokeWidth(strokeWidth+5);
        mstartyPaint.setStyle(Paint.Style.STROKE);

        mstopyPaint=new Paint();
        mstopyPaint.setColor(Color.GREEN);
        mstopyPaint.setStrokeWidth(strokeWidth+5);
        mstopyPaint.setStyle(Paint.Style.STROKE);
    }

    public int getMaxSize(int maxSize){
        log("GETMAXSZ","getMaxSize("+maxSize+")");
        int tsize=maxSize;

        TextPaint tp=new TextPaint();
        tp.setTextSize(tsize);
        Paint.FontMetrics fm=tp.getFontMetrics();
        int bh=(int)(fm.bottom-fm.top);
        //Log.e("GETMAXSZ","size="+tsize+"/bh="+bh+"/maxSize="+maxSize);
        for(tsize=maxSize;bh>maxSize;tsize--){
            tp.setAntiAlias(true);
            tp.setTextSize(tsize);
            tp.setColor(Color.BLACK);
            fm=tp.getFontMetrics();
            bh=(int)(fm.bottom-fm.top);
          //  Log.e("FOR","size="+tsize+"/bh="+bh+"/maxSize="+maxSize);
        }

        return tsize--;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 200;
        int height = 200;
        log("GETMAXSZ","onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthRequirement = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthRequirement;
        } else if (widthMode == MeasureSpec.AT_MOST ) {
            width=(int)mTextPaint.measureText(textMain);
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightRequirement = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightRequirement;
        } else if (heightMode == MeasureSpec.AT_MOST ) {
            height = 100;
        }

        setMeasuredDimension(width, height);
        maxHeight=height;
        initTextSize();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        log("ONLAYOUT","onLAYOUT");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w=getWidth();
        canvas.drawText(textMain, (w-textWidth)/2, startY, mTextPaint); // x=0, y=0
        canvas.drawRect(0, boxTop,w, boxBottom,mRectPaint);
        //Log.e("EVENTCh box draw"," bleft=0"+"boxTop="+boxTop+" width="+getWidth()+" bbottom="+boxBottom);


    }

    public void setText(String textMain) {
        this.textMain = textMain;
        invalidate();
        //requestLayout();
    }


    public float getMeasuredTextWidth() {
        return mTextPaint.measureText(textMain);
    }


}
