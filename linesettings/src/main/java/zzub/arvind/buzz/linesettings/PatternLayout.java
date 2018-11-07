package zzub.arvind.buzz.linesettings;

import android.view.ViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PatternLayout extends ViewGroup  {

    public Context mcontext;
    public AttributeSet mattrs;
    View topChild;
    float[] lineHSV;

    static PatternDrawable pd;
    private ValuesCadgw cadg;
    private int index=0;

    OnHierarchyChangeListener hierarchyChangeListener=null;
    CompoundButton.OnCheckedChangeListener selectListener;

    private CheckBox selectColor;
    private CheckBox selectWidths;

    private SeekBar colorChild;
    private SeekBar alphaChild;
    private SeekBar dashChild;
    private SeekBar gapChild;
    private SeekBar widthChild;

    private TextView titleChild;
    private TextView colorLabel;
    private TextView alphaLabel;
    private TextView dashLabel;
    private TextView gapLabel;
    private TextView widthLabel;

    public Button proceedButton;
    public OnProceedListener onProceedListener;
    public SeekBar.OnSeekBarChangeListener onSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int color;
            String tag=(String)seekBar.getTag();
            int seek=Integer.parseInt(tag);
            switch (seek){
                case 5:
                    lineHSV=new float[]{progress,1,1};
                    color=Color.HSVToColor(pd.getLineAlpha(),lineHSV);
                    pd.setLineColor(color);
                    break;
                case 7:
                    pd.setLineAlpha(progress);
                    break;
                case 9:
                    pd.setDashLength(progress);
                    break;
                case 11:
                    pd.setGapLength(progress);
                    break;
                case 13:
                    pd.setLineWidth(progress);
                    break;
                default:
                    break;
            }
            cadg.setPd(pd);
            cadg.invalidate();
           // Log.e("ONPROGRESS","progress changed");

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    public PatternLayout(Context context) {
        super(context);
        mcontext=context;
        setWillNotDraw(false);
       // Log.e("EVENT"," PatternLayout(Context context, AttributeSet attrs)");
        init();
    }

    public PatternLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext=context;
        mattrs=attrs;
        setWillNotDraw(false);
        //Log.e("EVENT"," PatternLayout(Context context, AttributeSet attrs)");
        init();

    }

    public PatternLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);
       // Log.e("EVENT"," PatternLayout(Context context, AttributeSet attrs, int defStyleAttr)");
        init();
    }

    public PatternLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, 0, 0);
        //Log.e("EVENT"," PatternLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)");
        init();
    }
    public  void setPd(PatternDrawable pd) {
        PatternLayout.pd = pd;
        cadg.setPd(pd);
        init();
    }
    private void init(){
        if(pd==null)pd=new PatternDrawable();
        selectListener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag = (String) buttonView.getTag();
                int childId = Integer.parseInt(tag);
                switch (childId) {
                    case 2:
                        if(isChecked){
                            colorLabel.setVisibility(VISIBLE);
                            colorChild.setVisibility(VISIBLE);
                            alphaLabel.setVisibility(VISIBLE);
                            alphaChild.setVisibility(VISIBLE);
                            requestLayout();
                            invalidate();
                        }else{
                            colorLabel.setVisibility(GONE);
                            colorChild.setVisibility(GONE);
                            alphaLabel.setVisibility(GONE);
                            alphaChild.setVisibility(GONE);
                            requestLayout();
                            invalidate();
                        }
                        break;
                    case 3:
                        if(isChecked){
                            dashLabel.setVisibility(VISIBLE);
                            dashChild.setVisibility(VISIBLE);
                            gapLabel.setVisibility(VISIBLE);
                            gapChild.setVisibility(VISIBLE);
                            widthLabel.setVisibility(VISIBLE);
                            widthChild.setVisibility(VISIBLE);
                            requestLayout();
                            invalidate();
                        }
                        else{
                            dashLabel.setVisibility(GONE);
                            dashChild.setVisibility(GONE);
                            gapLabel.setVisibility(GONE);
                            gapChild.setVisibility(GONE);
                            widthLabel.setVisibility(GONE);
                            widthChild.setVisibility(GONE);
                            requestLayout();
                            invalidate();
                        }
                        break;
                }
            }
        };
        if(hierarchyChangeListener==null){
            //Log.e("Hierarchy"," hierarchyChangeListener is null");
            hierarchyChangeListener=new OnHierarchyChangeListener(){

                @Override
                public void onChildViewAdded(View parent, View child) {
                   // Log.e("Hierarchy"," View tag=" +child.getTag() +" added");

                    String tag=index+"";
                    index++;
                    child.setTag(tag);
                    //if(tag==null)return;
                    int childId=Integer.parseInt(tag);
                    switch(childId){
                        case 0:
                            titleChild=(TextView)child;
                            titleChild.setTextSize(30);
                            titleChild.setTypeface(Typeface.DEFAULT_BOLD);
                            break;
                        case 1:
                            topChild= child;
                            lineHSV=new float[]{pd.getHue(),1,1};
                            topChild.setBackground(pd);
                            break;
                        case 2:
                            selectColor=(CheckBox)child;
                            selectColor.setChecked(true);
                            selectColor.setOnCheckedChangeListener(selectListener);
                            break;
                        case 3:
                            selectWidths=(CheckBox)child;
                            selectWidths.setChecked(true);
                            selectWidths.setOnCheckedChangeListener(selectListener);
                            break;
                        case 4:
                            colorLabel=(TextView)child;
                            break;
                        case 5:
                            colorChild=(SeekBar)child;
                            colorChild.setMax(360);
                            colorChild.setProgress((int)pd.getHue());
                            colorChild.setOnSeekBarChangeListener(onSeekBarChangeListener);
                            break;
                        case 6:
                            alphaLabel=(TextView)child;
                            break;
                        case 7:
                            alphaChild=(SeekBar)child;
                            alphaChild.setMax(255);
                            alphaChild.setProgress(pd.getLineAlpha());
                            alphaChild.setOnSeekBarChangeListener(onSeekBarChangeListener);
                            break;
                        case 8:
                            dashLabel=(TextView)child;
                            break;
                        case 9:
                            dashChild=(SeekBar)child;
                            dashChild.setMax(50);
                            dashChild.setProgress((int)pd.getDashLength());
                            dashChild.setOnSeekBarChangeListener(onSeekBarChangeListener);
                            break;
                        case 10:
                            gapLabel=(TextView)child;
                            break;
                        case 11:
                            gapChild=(SeekBar)child;
                            gapChild.setMax(50);
                            gapChild.setProgress((int)pd.getGapLength());
                            gapChild.setOnSeekBarChangeListener(onSeekBarChangeListener);
                            break;
                        case 12:
                            widthLabel=(TextView)child;
                            break;
                        case 13:
                            widthChild=(SeekBar)child;
                            widthChild.setMax(90);
                            widthChild.setProgress(pd.getLineWidth());
                            widthChild.setOnSeekBarChangeListener(onSeekBarChangeListener);
                            break;
                        case 14:
                            proceedButton=(Button)child;
                            proceedButton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onProceedListener.onProceed(v,pd);
                                }
                            });
                            break;
                        case 15:
                            cadg=(ValuesCadgw)child;
                            cadg.setPd(pd);
                            break;

                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                    //Log.e("Hierarchy"," View tag=" +child.getTag() +"removed");
                }
            };
        }
        setOnHierarchyChangeListener(hierarchyChangeListener);


    }

    @Override
    protected void onAttachedToWindow() {
       // Log.e("EVENT","onAttachedToWindow()");
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int totalWidth=0;
        int totalHeight=0;

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int count =getChildCount();
        for(int i=0;i<count;i++){
            View child=getChildAt(i);

            if(child.getVisibility()!=GONE) {

                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int leftMargin = lp.leftMargin;
                int rightMargin = lp.rightMargin;
                int topMargin = lp.topMargin;
                int bottomMargin = lp.bottomMargin;

                int totalHorizontalMargin = leftMargin + rightMargin;
                int totalVerticalMargin = topMargin + bottomMargin;

                //Log.e("PATTERNLAYOUT", " onMeasure i=" + i + " leftMargin=" + leftMargin + " rightMargin=" + rightMargin + " topMargin=" + topMargin
                 //       + " bottomMargin" + bottomMargin);

                switch (widthMode) {
                    case MeasureSpec.EXACTLY:
                        totalWidth = Math.max(totalWidth, child.getMeasuredWidth() + totalHorizontalMargin);
                        break;
                    case MeasureSpec.AT_MOST:
                        totalWidth = Math.max(totalWidth, child.getMeasuredWidth() + totalHorizontalMargin);
                        break;
                    case MeasureSpec.UNSPECIFIED:
                        totalWidth = Math.max(totalWidth, child.getMeasuredWidth() + totalHorizontalMargin);
                        break;
                }
                switch (heightMode) {
                    case MeasureSpec.EXACTLY:
                        if(i!=3)
                            totalHeight = totalHeight + child.getMeasuredHeight() + totalVerticalMargin;
                        break;
                    case MeasureSpec.AT_MOST:
                        if(i!=3)
                            totalHeight = totalHeight + child.getMeasuredHeight() + totalVerticalMargin;
                        break;
                    case MeasureSpec.UNSPECIFIED:
                        if(i!=3)
                            totalHeight = totalHeight + child.getMeasuredHeight() + totalVerticalMargin;
                        break;
                }
            }
            //child.setTag(String.valueOf(i));

        }
        //Log.e("PATTERNLAYOUT"," onMeasure setMeasuredDimension("+ totalWidth+","+ totalHeight+")");
        setMeasuredDimension(totalWidth,totalHeight);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Log.e("EVENT"," onLayout("+ changed+","+l+","+ t+","+r+","+b+")");
        int count = getChildCount();
        int prevChildRight = 0;
        int prevChildBottom = 0;

        int leftMargin;
        int rightMargin;
        int topMargin;
        int bottomMargin;

        int childLeft;
        int childTop;
        int childRight;
        int childBottom;

        int prevChildRightTemp=0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                leftMargin = lp.leftMargin;
                rightMargin = lp.rightMargin;
                topMargin = lp.topMargin;
                bottomMargin = lp.bottomMargin;
                if(i!=2){
                    //prevChildRight=prevChildRightTemp;
                    childLeft = prevChildRight + leftMargin;
                    childTop = prevChildBottom + lp.topMargin;
                    childRight = childLeft + child.getMeasuredWidth() + rightMargin;
                    childBottom = childTop + child.getMeasuredHeight() + bottomMargin;
                    child.layout(childLeft, childTop, childRight, childBottom);
                    prevChildBottom += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                    prevChildRight=prevChildRightTemp;
                }else{
                    childLeft = prevChildRight + leftMargin;
                    childTop = prevChildBottom + lp.topMargin;
                    childRight = childLeft + child.getMeasuredWidth() + rightMargin;
                    childBottom = childTop + child.getMeasuredHeight() + bottomMargin;
                    child.layout(childLeft, childTop, childRight, childBottom);
                    prevChildRight += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                    //prevChildRight += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                }

            }
        }

    }

    public void setOnProceedListener(OnProceedListener onProceedListener) {
        this.onProceedListener = onProceedListener;
    }

    public interface OnProceedListener{
        public void onProceed(View view, PatternDrawable pd);
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
    public PatternLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new PatternLayout.LayoutParams(getContext(), attrs);
    }


    public static class LayoutParams extends ViewGroup.MarginLayoutParams{
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;

        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;

        public int position = POSITION_MIDDLE;


        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
        public LayoutParams(Context context, AttributeSet attr) {
            super(context,attr);
        }
    }
}