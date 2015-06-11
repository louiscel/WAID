package com.ca.wayd;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ListActivity extends Activity {

    private ListView listView;
    private TextView textView;
    private Chronometer chrono;
    private String currentActivity;
    private long     startedTime = 0;
    private Map<String, Long> timers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timers = new HashMap<>();

        setContentView(R.layout.list_activity);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.text);
        chrono   = (Chronometer) findViewById(R.id.chronometer);

        // Defined Array values to show in ListView
        String[] values = new String[200];
        for (int i=0; i<200; i++) {
            values[i] = "Activité n" + i;
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // Listener sur les events "click"
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Récupération de la valeur cliquée
                String  itemValue    = (String) listView.getItemAtPosition(position);
                textView.setText(itemValue);
                startChrono(itemValue);
                // Affiche les infos de l'activité sélectionnée
                //Toast.makeText(getApplicationContext(),"Activité sélectionnée : " + position + "  Valeur : " + itemValue, Toast.LENGTH_LONG).show();

            }

        });
    }

    private void startChrono(String activityId) {
        // Récupération de l'heure courante
        long time = SystemClock.elapsedRealtime();
        // On sauvegarde le timer courant
        if (currentActivity != null) {
            long elapsed = time - chrono.getBase();
            timers.put(currentActivity, elapsed);
        }

        // Récupération de l'ancien chrono si existant
        Long lastTime = timers.get(activityId);
        if (lastTime == null) {
            lastTime = 0l;
        }
        currentActivity = activityId;

        chrono.setBase(time - lastTime);
        chrono.stop();
        chrono.start();
    }
}
