package droiddevelopers254.droidconke.utils

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationBehaviour : CoordinatorLayout.Behavior<BottomNavigationView>() {
  private var height: Int = 0

  override fun onLayoutChild(parent: CoordinatorLayout, child: BottomNavigationView, layoutDirection: Int): Boolean {
    height = child.height
    return super.onLayoutChild(parent, child, layoutDirection)
  }

  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL
  }

  override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    //super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    if (dyConsumed > 0) {
      slideDown(child)
    } else if (dyConsumed < 0) {
      slideUp(child)
    }

  }

  private fun slideUp(child: BottomNavigationView) {
    child.clearAnimation()
    child.animate().translationY(0f).duration = 200
  }

  private fun slideDown(child: BottomNavigationView) {
    child.clearAnimation()
    child.animate().translationY(height.toFloat()).duration = 200
  }
}
