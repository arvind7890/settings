package zzub.arvind.buzz.linesettings;

import android.view.View;
import android.widget.LinearLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

public class SettingControl extends LinearLayout implements PatternLayout.OnProceedListener {
    public interface OnProceedButtonListener{
        public void onProceedButtonClick(View v, PatternDrawable pd);
    }
    PatternLayout pl;
    public OnProceedButtonListener onProceedButtonListener;
    private Context mcontext;
    private AttributeSet mattr;
    public SettingControl(Context context) {
        super(context);
        mcontext=context;
        init();
    }

    public SettingControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext=context;
        mattr=attrs;
        init();
    }

    public SettingControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);

    }

    public SettingControl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, 0,0);
    }
    public void init(){

        LayoutInflater inflater=LayoutInflater.from(mcontext);
        inflater.inflate(R.layout.pattern_control,this,true);
        pl=findViewById(R.id.pattern);
        pl.setOnProceedListener(this);



    }

    public void setOnProceedButtonListener(OnProceedButtonListener onProceedButtonListener) {
        this.onProceedButtonListener = onProceedButtonListener;
    }

    @Override
    public void onProceed(View view, PatternDrawable pd) {
        onProceedButtonListener.onProceedButtonClick(view,pd);
    }
    public void setPatternDrawable(PatternDrawable patternDrawable){
            pl.setPd(patternDrawable);
    }
}
