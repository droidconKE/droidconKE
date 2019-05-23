package droiddevelopers254.droidconke.utils

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import org.apache.commons.lang3.StringEscapeUtils

class HtmlTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    fun setHtmlText(text: String?) {
        if (text != null) {
            setText(Html.fromHtml(StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeHtml4(text))))
        } else {
            setText("")
        }
    }

    /**
     * LinkMovementMethod conflicts with text ellipsizing so instead we we listen for touch events
     * and implement link detection (logic borrowed from LinkMovementMethod).
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when {
            (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) && text is Spanned -> {
                var x = event.x.toInt()
                var y = event.y.toInt()

                x -= totalPaddingLeft
                y -= totalPaddingTop

                x += scrollX
                y += scrollY

                val layout = layout
                val line = layout.getLineForVertical(y)
                val off = layout.getOffsetForHorizontal(line, x.toFloat())

                val link = (text as Spanned).getSpans(off, off, ClickableSpan::class.java)
                when {
                    link.isNotEmpty() -> {
                        when (action) {
                            MotionEvent.ACTION_UP -> link[0].onClick(this)
                        }
                        return true
                    }
                }
            }
        }
        return false
    }
}
