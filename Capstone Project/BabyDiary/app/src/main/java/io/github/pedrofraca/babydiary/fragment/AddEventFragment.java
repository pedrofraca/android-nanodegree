package io.github.pedrofraca.babydiary.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.Arrays;
import java.util.Collections;

import io.github.pedrofraca.babydiary.R;

/**
 * Created by pedrofraca on 17/05/16.
 */
public class AddEventFragment extends android.support.v4.app.Fragment {

    public static final String TAG = AddEventFragment.class.getSimpleName();
    private AddEventFragmentListener mListener;
    private View rootView;

    public AddEventFragment() {}

    public static AddEventFragment newInstance() {
        AddEventFragment fragment = new AddEventFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_event, container, false);
        // To run the animation as soon as the view is layout in the view hierarchy we add this
        // listener and remove it
        // as soon as it runs to prevent multiple animations if the view changes bounds
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);


                Animator reveal = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    // get the hypothenuse so the radius is from one corner to the other
                    int radius = (int) Math.hypot(right, bottom);
                    reveal = ViewAnimationUtils.createCircularReveal(v, v.getRight(), v.getBottom(), 0, radius);
                    reveal.setInterpolator(new DecelerateInterpolator(2f));
                    reveal.setDuration(1000);
                    reveal.start();
                }

            }
        });

        rootView.findViewById(R.id.create_photo_event).setOnClickListener(clickListener);
        rootView.findViewById(R.id.create_measure_event).setOnClickListener(clickListener);
        rootView.findViewById(R.id.create_vaccine_event).setOnClickListener(clickListener);

        // attach a touch listener
        return rootView;
    }


    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(final View view) {
            Animator unreveal = prepareUnrevealAnimator(0, 0);
            if(unreveal!=null){
                unreveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // remove the fragment only when the animation finishes
                        getFragmentManager().beginTransaction().remove(AddEventFragment.this).commit();
                        //to prevent flashing the fragment before removing it, execute pending transactions inmediately
                        getFragmentManager().executePendingTransactions();
                        if(view.getId()==R.id.create_photo_event){
                            mListener.onPhotoEventClicked();
                        } else if(view.getId()==R.id.create_measure_event) {
                            mListener.onMeasureEventClicked();
                        } else if(view.getId()==R.id.create_vaccine_event) {
                            mListener.onVaccineEventClicked();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                unreveal.start();
            } else {
                // remove the fragment only when the animation finishes
                getFragmentManager().beginTransaction().remove(AddEventFragment.this).commit();
                //to prevent flashing the fragment before removing it, execute pending transactions inmediately
                getFragmentManager().executePendingTransactions();
                if(view.getId()==R.id.create_photo_event){
                    mListener.onPhotoEventClicked();
                } else if(view.getId()==R.id.create_measure_event) {
                    mListener.onMeasureEventClicked();
                } else if(view.getId()==R.id.create_vaccine_event) {
                    mListener.onVaccineEventClicked();
                }
            }

        }
    };

    /**
     * Get the animator to unreveal the circle
     *
     * @param cx center x of the circle (or where the view was touched)
     * @param cy center y of the circle (or where the view was touched)
     * @return Animator object that will be used for the animation
     */
    public Animator prepareUnrevealAnimator(float cx, float cy) {
        int radius = getEnclosingCircleRadius(getView(), (int) cx, (int) cy);
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(getView(), rootView.getRight(), rootView.getBottom(), radius, 0);
            anim.setInterpolator(new AccelerateInterpolator(2f));
            anim.setDuration(1000);
        }
        return anim;
    }

    @Override
    public void onAttach(Context context) {
        try{
            mListener = (AddEventFragmentListener) context;
        } catch (Exception e){
            Log.e(TAG,"The Activity must implement AddEventFragmentListener interface");
        }
        super.onAttach(context);
    }

    /**
     * To be really accurate we have to start the circle on the furthest corner of the view
     *
     * @param v  the view to unreveal
     * @param cx center x of the circle
     * @param cy center y of the circle
     * @return the maximum radius
     */
    private int getEnclosingCircleRadius(View v, int cx, int cy) {
        int realCenterX = cx + v.getLeft();
        int realCenterY = cy + v.getTop();
        int distanceTopLeft = (int) Math.hypot(realCenterX - v.getLeft(), realCenterY - v.getTop());
        int distanceTopRight = (int) Math.hypot(v.getRight() - realCenterX, realCenterY - v.getTop());
        int distanceBottomLeft = (int) Math.hypot(realCenterX - v.getLeft(), v.getBottom() - realCenterY);
        int distanceBottomRight = (int) Math.hypot(v.getRight() - realCenterX, v.getBottom() - realCenterY);

        Integer[] distances = new Integer[]{distanceTopLeft, distanceTopRight, distanceBottomLeft,
                distanceBottomRight};

        return Collections.max(Arrays.asList(distances));
    }


    public interface AddEventFragmentListener{
        void onPhotoEventClicked();
        void onMeasureEventClicked();
        void onVaccineEventClicked();
    }
}
