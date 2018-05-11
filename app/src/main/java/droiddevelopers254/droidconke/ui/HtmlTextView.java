package droiddevelopers254.droidconke.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlTextView extends AppCompatTextView{
    public HtmlTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHtmlText(String text) {
        if (text != null) {
            setText(Html.fromHtml(StringEscapeUtils.
                    unescapeJava(StringEscapeUtils.
                            unescapeHtml4(text))));
        } else {
            setText("");
        }
    }

    /**
     * LinkMovementMethod conflicts with text ellipsizing so instead we we listen for touch events
     * and implement link detection (logic borrowed from LinkMovementMethod).
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN)
                && getText() instanceof Spanned) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= getTotalPaddingLeft();
            y -= getTotalPaddingTop();

            x += getScrollX();
            y += getScrollY();

            Layout layout = getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = ((Spanned) getText()).getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(this);
                }
                return true;
            }
        }
        return false;
    }
}
