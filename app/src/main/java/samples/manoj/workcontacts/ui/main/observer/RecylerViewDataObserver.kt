package samples.manoj.workcontacts.ui.main.observer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver


class RecyclerViewDataObserver(private val recyclerView: RecyclerView, ev1: View?, ev2: View?) : AdapterDataObserver() {
    private val emptyView1: View?
    private val emptyView2: View?
    private fun checkIfEmpty() {
        if (emptyView1 != null && emptyView2!=null && recyclerView.adapter != null) {
            val emptyViewVisible = recyclerView.adapter!!.itemCount == 0
            emptyView1.setVisibility(if (emptyViewVisible) View.VISIBLE else View.GONE)
            emptyView2.setVisibility(if (emptyViewVisible) View.VISIBLE else View.GONE)
            recyclerView.setVisibility(if (emptyViewVisible) View.GONE else View.VISIBLE)
        }
    }

    override fun onChanged() {
        checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        checkIfEmpty()
    }

    init {
        emptyView1 = ev1
        emptyView2 = ev2
        checkIfEmpty()
    }
}