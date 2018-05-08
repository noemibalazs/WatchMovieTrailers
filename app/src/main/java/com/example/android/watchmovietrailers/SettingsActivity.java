package com.example.android.watchmovietrailers;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public static class MovieSharedPreference extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            Preference orderBy = findPreference(getString(R.string.order_by_label_key));
            bindPreferenceSummaryToValue(orderBy);

            Preference year = findPreference(getString(R.string.search_movies_label_key));
            bindPreferenceSummaryToValue(year);

        }



        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                if (index>0){
                    CharSequence [] labels = listPreference.getEntries();
                    preference.setSummary(labels [index]);
                } else {
                    preference.setSummary(stringValue);
                }
            }

            return true;
        }


        private void bindPreferenceSummaryToValue(Preference preference){
           preference.setOnPreferenceChangeListener(this);
           SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
           String string = sharedPreferences.getString(preference.getKey(), "");
           onPreferenceChange(preference, string);
        }
    }


}
