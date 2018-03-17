package com.example.bruno.notesync.javaClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Bruno on 3/15/2018.
 */

public class RvItemClickListener implements RecyclerView.OnItemTouchListener {

GestureDetector gestureDetector;
OnClickListener onClickListener;



    public RvItemClickListener(Context context,final RecyclerView recyclerView,final OnClickListener onClickListener) {

        this.onClickListener= onClickListener;


        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent)
            {


            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent)
            {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                if (child != null && onClickListener != null) {
                    onClickListener.onItemClick( recyclerView.getChildLayoutPosition(child),child);
                }

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                if (child != null && onClickListener != null) {
                    onClickListener.onItemLongClick( recyclerView.getChildLayoutPosition(child),child);
                }

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

                return false;
            }
        });


    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)

    {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && onClickListener != null && gestureDetector.onTouchEvent(e)) {
            onClickListener.onItemClick(rv.getChildLayoutPosition(child),child);
        }
        return false;


    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && onClickListener != null && gestureDetector.onTouchEvent(e)) {
            onClickListener.onItemClick(rv.getChildLayoutPosition(child),child);
        }

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
