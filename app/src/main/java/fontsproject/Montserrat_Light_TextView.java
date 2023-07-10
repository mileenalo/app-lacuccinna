package fontsproject;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class Montserrat_Light_TextView extends AppCompatTextView {
    public Montserrat_Light_TextView(Context context) {
        super(context);
        init();
    }

    public Montserrat_Light_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Montserrat_Light_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLineSpacing(0, 0.9f);
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Light.ttf");
            setTypeface(tf);
        }
    }
}