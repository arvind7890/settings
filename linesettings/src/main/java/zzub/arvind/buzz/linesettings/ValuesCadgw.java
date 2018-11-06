package zzub.arvind.buzz.linesettings;

import android.view.ViewGroup;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

public class ValuesCadgw extends ViewGroup {
    interface OnPatternChangeListener{
        void onChangePatternParams(PatternDrawable pdnew);
    }
    BoxedText colorBox;
    BoxedText alphaBox;
    BoxedText dashBox;
    BoxedText gapBox;
    BoxedText widthBox;

    static PatternDrawable pd;

    private OnPatternChangeListener onPatternChangeListener;
    Context mcontext;
    AttributeSet mattrs;

    int layoutWidth=0;

    int hspace=0;

    public ValuesCadgw(Context context) {
        super(context);
        mcontext=context;
    }

    public ValuesCadgw(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext=context;
        mattrs=attrs;
    }

    public ValuesCadgw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ValuesCadgw(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setOnPatternChangeListener(OnPatternChangeListener onPatternChangeListener) {
        this.onPatternChangeListener = onPatternChangeListener;
    }
    private void init(){
        colorBox=(BoxedText)getChildAt(0);
        alphaBox=(BoxedText)getChildAt(1);
        dashBox=(BoxedText)getChildAt(2);
        gapBox=(BoxedText)getChildAt(3);
        widthBox=(BoxedText)getChildAt(4);
        setOnPatternChangeListener(new OnPatternChangeListener() {
            @Override
            public void onChangePatternParams(PatternDrawable pdnew) {
                Log.e("PATTERNDRAWABLE",pdnew.toString());
                String strColor = String.format("#%06X", 0xFFFFFF & pdnew.getLineColor());
                colorBox.setText(strColor);
                alphaBox.setText(pdnew.getLineAlpha() + "");
                dashBox.setText((int) pdnew.getDashLength() + "");
                gapBox.setText((int) pd.getGapLength() + "");
                widthBox.setText(pd.getLineWidth() + "");
            }
        });

        if (colorBox == null) {
            colorBox = (BoxedText) getChildAt(0);
            Log.e("COLORBOX","NULL");
        }
        if (alphaBox == null) {
            alphaBox = (BoxedText) getChildAt(1);
        }
        if (dashBox == null) {
            dashBox = (BoxedText) getChildAt(2);
        }
        if (gapBox == null) {
            gapBox = (BoxedText) getChildAt(3);
        }
        if (widthBox == null) {
            widthBox = (BoxedText) getChildAt(4);
        }
        String strColor = String.format("#%06X", 0xFFFFFF & pd.getLineColor());
        colorBox.setText(strColor);
        alphaBox.setText(pd.getLineAlpha() + "");
        dashBox.setText((int) pd.getDashLength() + "");
        gapBox.setText((int) pd.getGapLength() + "");
        widthBox.setText( pd.getLineWidth() + "");
    }
    @Override
    protected void onAttachedToWindow() {
        Log.e("EVENTC","onAttachedToWindow()");
        super.onAttachedToWindow();
        init();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("VALUESCADGW"," onMeasure("+ widthMeasureSpec+","+ heightMeasureSpec+")");

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        layoutWidth = MeasureSpec.getSize(widthMeasureSpec);

        int totalWidth=0;
        int totalHeight=0;

        int count =getChildCount();

        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            if(child.getVisibility()==GONE)return;

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int leftMargin=lp.leftMargin;
            int rightMargin=lp.rightMargin;
            int topMargin=lp.topMargin;
            int bottomMargin=lp.bottomMargin;

            int totalHorizontalMargin=leftMargin+rightMargin;
            int totalVerticalMargin=topMargin+bottomMargin;

            Log.e("PATTERNLAYOUT"," onMeasure i="+i+" leftMargin="+leftMargin+" rightMargin="+rightMargin+" topMargin="+topMargin
                    +" bottomMargin"+bottomMargin);

            switch (widthMode){
                case MeasureSpec.EXACTLY:
                    totalWidth+=child.getMeasuredWidth()+totalHorizontalMargin;
                    break;
                case MeasureSpec.AT_MOST:
                    totalWidth+=child.getMeasuredWidth()+totalHorizontalMargin;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    totalWidth+=child.getMeasuredWidth()+totalHorizontalMargin;
                    break;
            }
            switch (heightMode){
                case MeasureSpec.EXACTLY:
                    totalHeight=Math.max(totalHeight,heightSize+totalVerticalMargin);
                    break;
                case MeasureSpec.AT_MOST:
                    totalHeight=Math.max(totalHeight,child.getMeasuredHeight()+totalVerticalMargin);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    totalHeight=Math.max(totalHeight,heightSize+totalVerticalMargin);
                    break;
            }

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            child.setTag(String.valueOf(i));
            Log.e("EVENTM","i="+i+" childWidth="+child.getMeasuredWidth()+" totalWidth="+totalWidth+" layoutWidth="+layoutWidth);
            Log.e("EVENTCh","i="+i+" childWidth="+child.getMeasuredWidth()+" totalHeight="+totalHeight);
        }
        hspace=(layoutWidth-totalWidth)/(count-1);
        hspace--;
        Log.e("EVENTM","hspace="+hspace);
        setMeasuredDimension(layoutWidth,totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int prevChildRight = 0;
        int prevChildBottom = 0;
        int vspace=10;
        //int hspace=20;
        int childLeft;//hspace;
        int childTop;
        int childRight;
        int childBottom;
        int childWidth;
        int childheight;
        int prevChildWidth;
        int prevWidth=0;
        //prevChildRight +=hspace;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child.getVisibility()==GONE)return;
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            Log.e("Margins","i="+i+" leftMargin="+lp.leftMargin+" rightMargin="+lp.rightMargin+" topMargin="+lp.topMargin
                    +" bottomMargin"+lp.bottomMargin);

            childWidth=child.getMeasuredWidth();
            childheight=child.getMeasuredHeight();
            childLeft=prevWidth+lp.leftMargin;
            childTop=lp.topMargin;
            childRight=childLeft+childWidth+lp.rightMargin;
            childBottom=childTop+childheight+lp.bottomMargin;

            child.layout(childLeft,childTop,childRight,childBottom);
            prevWidth+=child.getMeasuredWidth()+hspace+lp.leftMargin+lp.rightMargin;
            Log.e("EVENTCh","i="+i+" prevWidth="+prevWidth);
            Log.e("EVENTCh","i="+i+" childLeft="+childLeft+" childTop="+childTop+" childRight="+childRight+" childBottom="+childBottom);


        }
    }

    public void setPd(PatternDrawable pd1) {
        pd = pd1;
        if(onPatternChangeListener!=null)
            onPatternChangeListener.onChangePatternParams(pd);
        pd.invalidateSelf();
        //invalidate();

    }
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }


    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;

        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;

        public int position = POSITION_MIDDLE;

        public LayoutParams(Context context, AttributeSet attr) {
            super(context,attr);
        }
        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}