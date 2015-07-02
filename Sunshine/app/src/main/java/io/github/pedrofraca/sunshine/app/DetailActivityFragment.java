package io.github.pedrofraca.sunshine.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        String forecast = getActivity().getIntent().getStringExtra(DetailActivity.FORECAST_DATA);
        TextView forecastTextView = (TextView) rootView.findViewById(R.id.forecast);
        forecastTextView.setText(forecast);
        return rootView;
    }
}
