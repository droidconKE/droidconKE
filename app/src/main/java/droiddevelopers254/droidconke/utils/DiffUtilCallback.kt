package droiddevelopers254.droidconke.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback : DiffUtil.Callback() {
  override fun getOldListSize(): Int {
    return 0
  }

  override fun getNewListSize(): Int {
    return 0
  }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return false
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return false
  }
}
