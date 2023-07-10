package fontsproject;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class Raleway_Bold_TextView extends AppCompatTextView {
    public Raleway_Bold_TextView(Context context) {
        super(context);
        init();
    }

    public Raleway_Bold_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Raleway_Bold_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLineSpacing(0, 0.9f);
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Bold.ttf");
            setTypeface(tf);
        }
    }
}