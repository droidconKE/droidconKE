package droiddevelopers254.droidconke.ui

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.support.v7.content.res.AppCompatResources
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import droiddevelopers254.droidconke.R

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M

class CollapsibleCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var mExpanded = false
    private val mCardTitle: TextView
    private val mCardDescription: HtmlTextView
    private val mExpandIcon: ImageView
    private val mTitleContainer: View

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleCard, 0, 0)
        val cardTitle = arr.getString(R.styleable.CollapsibleCard_cardTitle)
        val cardDescription = arr.getString(R.styleable.CollapsibleCard_cardDescription)
        arr.recycle()
        val root = LayoutInflater.from(context)
                .inflate(R.layout.collapsible_card_content, this, true)

        mTitleContainer = root.findViewById(R.id.title_container)
        mCardTitle = root.findViewById(R.id.card_title)
        mCardTitle.text = cardTitle
        setTitleContentDescription(cardTitle)
        mCardDescription = root.findViewById(R.id.card_description)
        mCardDescription.setHtmlText(cardDescription)
        mExpandIcon = root.findViewById(R.id.expand_icon)
        if (SDK_INT < M) {
            mExpandIcon.imageTintList = AppCompatResources.getColorStateList(context, R.color.collapsing_section)
        }
        val toggle = TransitionInflater.from(getContext())
                .inflateTransition(R.transition.info_card_toggle)
        val expandClick = OnClickListener {
            mExpanded = !mExpanded
            toggle.duration = if (mExpanded) 300L else 200L
            TransitionManager.beginDelayedTransition(root.parent as ViewGroup, toggle)
            mCardDescription.visibility = if (mExpanded) View.VISIBLE else View.GONE
            mExpandIcon.rotation = if (mExpanded) 180f else 0f
            // activated used to tint controls when expanded
            mExpandIcon.isActivated = mExpanded
            mCardTitle.isActivated = mExpanded
            setTitleContentDescription(cardTitle)
        }
        mTitleContainer.setOnClickListener(expandClick)
    }

    fun setCardDescription(description: String) {
        mCardDescription.setHtmlText(description)
    }

    private fun setTitleContentDescription(cardTitle: String?) {
        val res = resources
        mCardTitle.contentDescription = "$cardTitle, " +
                if (mExpanded)
                    res.getString(R.string.expanded)
                else
                    res.getString(R.string.collapsed)
    }
}