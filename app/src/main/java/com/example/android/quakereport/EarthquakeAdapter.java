package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by andre on 20/10/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquake) {
        super(context, 0, earthquake);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // verifica se a view está sendo reutilizada, senao inflate
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Busca o Earthquake dentro da ArrayAdapter atraves da position
        Earthquake currentEarthquake = this.getItem(position);

        // Vincula cada atributo da Earthquake a respectiva TextView da earthquake_list_item.xmlst_item.xml
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeView.setText(new DecimalFormat("0.0").format(currentEarthquake.getMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String[] location = currentEarthquake.getLocation().split(" of ");

        TextView distanceView = (TextView) listItemView.findViewById(R.id.location_offset);
        TextView locationView = (TextView) listItemView.findViewById(R.id.location);

        if (location.length == 2) {
            distanceView.setText(location[0] + " of");
            locationView.setText(location[1]);
        } else {
            distanceView.setText("");
            locationView.setText(location[0]);
        }

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(new SimpleDateFormat("MMM dd, yyyy").format(new Date(currentEarthquake.getTimeInMilisseconds())));

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(new SimpleDateFormat("h:mm a").format(new Date(currentEarthquake.getTimeInMilisseconds())));

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {

        int magnitudeColor;

        switch (new Double(magnitude).intValue()) {
            case 0:
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }

    }
}
