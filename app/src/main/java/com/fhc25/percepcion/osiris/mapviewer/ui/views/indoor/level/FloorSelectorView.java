package com.fhc25.percepcion.osiris.mapviewer.ui.views.indoor.level;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fhc25.percepcion.osiris.mapviewer.R;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * View that shows a selector of indoor floors in a map
 *
 * @author Alvaro.Arranz
 */
public class FloorSelectorView extends RadioGroup implements IFloorSelectorView {

    private static final String TAG = FloorSelectorView.class.getName();
    private static final String STRFLOOR_ID = "mStrFloor";
    private static final String RBFLOOR_ID = "mFloorRadioButtons";
    private static final String RBCOLORSFLOOR_ID = "mFloorRadioButtonsColors";

    private static final int RADIO_BUTTON_PADDING = 10;

    private List<RadioButton> mFloorRadioButtons = new ArrayList<RadioButton>();
    private List<Integer> mFloorRadioButtonsColors = new ArrayList<Integer>();
    private String mStrFloor = "";

    private IFloorSelectorObserver.Collection mObservers = new IFloorSelectorObserver.Collection();

    /**
     * Main constructor
     *
     * @param context
     * @param attrs
     */
    public FloorSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (rb != null) {
                    Lgr.i(TAG, "floor selected:" + rb.getText());
                    mStrFloor = (String) rb.getText();

                    mObservers.onFloorSelectedChanged(mStrFloor);
                }
            }
        });
    }

    /**
     * Adds an observer for the view
     *
     * @param observer
     */
    @Override
    public void addObserver(IFloorSelectorObserver observer) {
        mObservers.add(observer);
    }

    /**
     * Removes an observer
     *
     * @param observer
     */
    @Override
    public void removeObserver(IFloorSelectorObserver observer) {
        mObservers.remove(observer);
    }

    /**
     * Gets the current selected floor
     *
     * @return
     */
    @Override
    public String getFloor() {
        return mStrFloor;
    }

    /**
     * Sets the current selected floor
     *
     * @param floor
     */
    @Override
    public void setFloor(String floor) {
        mStrFloor = floor;

        for (final RadioButton rb : mFloorRadioButtons) {

            if (rb.getText().equals(mStrFloor)) {

                this.post(new Runnable() {

                    @Override
                    public void run() {
                        rb.performClick();
                    }
                });
            }
        }
    }

    /**
     * Loads the View with the corresponding names passed as parameters
     *
     * @param l_names
     */
    @Override
    public void load(Collection<String> l_names) {
        Lgr.i(TAG, "load: " + l_names);

        removeAllViews();

        if (mFloorRadioButtonsColors.isEmpty() || mFloorRadioButtonsColors.size() != l_names.size()) {
            mFloorRadioButtonsColors.clear();

            for (int i = 0; i < l_names.size(); i++) {
                mFloorRadioButtonsColors.add(getResources().getColor(
                        R.color.floor_button_background));
            }
        }

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

        if (mStrFloor == null || "".equals(mStrFloor)) {
            List<String> listNames = new LinkedList<String>(l_names);
            Collections.sort(listNames);
            mStrFloor = listNames.iterator().next();
        }

        mFloorRadioButtons.clear();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int selected_index = 0;
        int count = 0;
        for (String s : l_names) {
            if (!s.matches("")) {
                RadioButton rb = (RadioButton) inflater.inflate(
                        R.layout.template_floor_radio_button, null);

                rb.setPadding((int) (metrics.scaledDensity * RADIO_BUTTON_PADDING), 0,
                        (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING), 0);
                rb.setText(s);

                mFloorRadioButtons.add(rb);

                rb.setBackgroundColor(mFloorRadioButtonsColors.get(count));

                XmlResourceParser parser = getResources().getXml(R.color.color_floor_radio_button);
                Lgr.i("createFloorButtons", "" + rb.getId());
                try {
                    ColorStateList csl = ColorStateList.createFromXml(
                            getResources(), parser);
                    rb.setTextColor(csl);
                } catch (XmlPullParserException e) {
                    Lgr.e(TAG, e);
                } catch (IOException e) {
                    Lgr.e(TAG, e);
                }

                addView(rb);

                if (mStrFloor != null && s.matches(mStrFloor)) {
                    selected_index = count;
                }
                count++;
            }
        }

        if (!mFloorRadioButtons.isEmpty()) {
            mFloorRadioButtons.get(0).setPadding(
                    (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING),
                    (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING),
                    (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING), 0);
            mFloorRadioButtons.get(mFloorRadioButtons.size() - 1).setPadding(
                    (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING), 0,
                    (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING),
                    (int) (metrics.scaledDensity * RADIO_BUTTON_PADDING));
        }

        if (getChildCount() > 0) {

            RadioButton rb = (RadioButton) getChildAt(selected_index);
            check(rb.getId());
        }
    }

    /**
     * Sets the color theme for each button considering a group of route floors
     *
     * @param listRouteFloors
     */
    public void setRouteColorsInFloors(final Collection<Integer> listRouteFloors) {

        post(new Runnable() {
            @Override
            public void run() {

                mFloorRadioButtonsColors = new ArrayList<Integer>();

                for (RadioButton rb : mFloorRadioButtons) {

                    boolean isInRoute = false;
                    Integer iRbText = Integer.parseInt((String) rb.getText());

                    for (Integer i : listRouteFloors) {
                        if (i.equals(iRbText)) {
                            isInRoute = true;
                        }
                    }

                    if (isInRoute) {
                        rb.setBackgroundColor(getResources().getColor(
                                R.color.floor_route_background));
                        mFloorRadioButtonsColors.add(getResources().getColor(
                                R.color.floor_route_background));
                    } else {
                        rb.setBackgroundColor(getResources().getColor(
                                R.color.floor_normal_background));
                        mFloorRadioButtonsColors.add(getResources().getColor(
                                R.color.floor_normal_background));
                    }
                }
            }
        });
    }

    /**
     * Loads its state from an Android's bundle object
     *
     * @param bundle
     */
    public void loadStateFromBundle(Bundle bundle) {

        if (bundle.containsKey(RBCOLORSFLOOR_ID + "Size")) {

            if (mFloorRadioButtonsColors == null) {
                mFloorRadioButtonsColors = new ArrayList<Integer>();
            }

            mFloorRadioButtonsColors.clear();
            int size = bundle.getInt(RBCOLORSFLOOR_ID + "Size");

            for (int i = 0; i < size; i++) {
                mFloorRadioButtonsColors.add(bundle.getInt(RBCOLORSFLOOR_ID + i));
            }
        }

        if (bundle.containsKey(RBFLOOR_ID + "Visibility")) {
            boolean isVisible = (bundle.getBoolean(RBFLOOR_ID + "Visibility"));
            if (isVisible) {
                setVisibility(View.VISIBLE);
            } else {
                setVisibility(View.INVISIBLE);
            }
        }

        if (bundle.containsKey(STRFLOOR_ID)) {
            mStrFloor = bundle.getString(STRFLOOR_ID);

            setFloor(mStrFloor);
        }

    }


    /**
     * Saves its state to an Android's bundle object
     *
     * @param bundle
     */
    public void saveStateToBundle(Bundle bundle) {

        bundle.putString(STRFLOOR_ID, this.mStrFloor);

        int counter = 0;
        if (mFloorRadioButtonsColors != null) {
            bundle.putInt(RBCOLORSFLOOR_ID + "Size", mFloorRadioButtonsColors.size());
            for (Integer i : mFloorRadioButtonsColors) {
                bundle.putInt(RBCOLORSFLOOR_ID + counter, i.intValue());
                counter++;
            }
        }

        bundle.putBoolean(RBFLOOR_ID + "Visibility", getVisibility() == View.VISIBLE);
    }

    public boolean isVisible() {
        int visibility = getVisibility();
        return visibility == View.VISIBLE;
    }

    public void setVisible(final boolean visible) {

        post(new Runnable() {
            @Override
            public void run() {

                if (visible) {
                    setVisibility(View.VISIBLE);
                } else {
                    setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
