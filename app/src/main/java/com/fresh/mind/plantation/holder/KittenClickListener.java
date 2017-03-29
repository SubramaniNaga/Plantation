package com.fresh.mind.plantation.holder;

import android.support.v7.widget.CardView;

/**
 * Listener for kitten click events in the grid of kittens
 *
 * @author bherbst
 */
public interface KittenClickListener {
    /**
     * Called when a kitten is clicked
     * @param holder The ViewHolder for the clicked kitten
     * @param position The position in the grid of the kitten that was clicked
     */
    void onKittenClicked(ViewHolder holder, int position, int viewType);
}
