package subham.sinha.dev.trimurl;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class typewriter extends AppCompatTextView {

    private CharSequence fullText;
    private int index = 0;
    private long delay = 150; // milliseconds
    private final Handler handler = new Handler();

    public typewriter(Context context) {
        super(context);
    }

    public typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCharacterDelay(long millis) {
        delay = millis;
    }

    private final Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(fullText.subSequence(0, index++));
            if (index <= fullText.length()) {
                handler.postDelayed(this, delay);
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fullText = getText();
        setText("");
        startTypewriter();
    }

    public void startTypewriter() {
        index = 0;
        handler.removeCallbacks(characterAdder);
        handler.postDelayed(characterAdder, delay);
    }
}