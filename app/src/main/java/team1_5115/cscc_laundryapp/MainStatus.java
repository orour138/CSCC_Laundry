package team1_5115.cscc_laundryapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainStatus extends AppCompatActivity {
    ArrayList myLoadTracking = new ArrayList();

    @SuppressLint("InlinedApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(R.layout.activity_main_status);
        loadBalanceValue(this);
        // start threads to update machine status
        ScheduledThreadPoolExecutor updateThreadPool = new ScheduledThreadPoolExecutor(1);
        updateThreadPool.scheduleAtFixedRate(new updateMachineStatusCallable(), 0, 1, TimeUnit.SECONDS);

    }

    private class updateMachineStatusCallable implements Runnable {
        Handler handler = new Handler();
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    LaundryMachines laundryMachines = LaundryMachines.getInstance();
                    // washer timers
                    TextView washer_text_1 = (TextView) findViewById(R.id.washer_text_1);
                    TextView washer_text_2 = (TextView) findViewById(R.id.washer_text_2);
                    TextView washer_text_3 = (TextView) findViewById(R.id.washer_text_3);
                    TextView washer_text_4 = (TextView) findViewById(R.id.washer_text_4);
                    washer_text_1.setText(laundryMachines.getWasherStatus(1));
                    washer_text_2.setText(laundryMachines.getWasherStatus(2));
                    washer_text_3.setText(laundryMachines.getWasherStatus(3));
                    washer_text_4.setText(laundryMachines.getWasherStatus(4));
                    // dryer timers
                    TextView dryer_text_1 = (TextView) findViewById(R.id.dryer_text_1);
                    TextView dryer_text_2 = (TextView) findViewById(R.id.dryer_text_2);
                    TextView dryer_text_3 = (TextView) findViewById(R.id.dryer_text_3);
                    TextView dryer_text_4 = (TextView) findViewById(R.id.dryer_text_4);
                    dryer_text_1.setText(laundryMachines.getDryerStatus(1));
                    dryer_text_2.setText(laundryMachines.getDryerStatus(2));
                    dryer_text_3.setText(laundryMachines.getDryerStatus(3));
                    dryer_text_4.setText(laundryMachines.getDryerStatus(4));

                    // washer icons
                    Button washer_button_1 = (Button) findViewById(R.id.washer_1);
                    Button washer_button_2 = (Button) findViewById(R.id.washer_2);
                    Button washer_button_3 = (Button) findViewById(R.id.washer_3);
                    Button washer_button_4 = (Button) findViewById(R.id.washer_4);
                    switch (laundryMachines.getWasherStatusIcon(1)) {
                        case "w_free":
                            washer_button_1.setBackgroundResource(R.drawable.w_free);
                            break;
                        case "w_broken":
                            washer_button_1.setBackgroundResource(R.drawable.w_broken);
                            break;
                        default:
                            washer_button_1.setBackgroundResource(R.drawable.w_busy);
                            break;
                    }
                    switch (laundryMachines.getWasherStatusIcon(2)) {
                        case "w_free":
                            washer_button_2.setBackgroundResource(R.drawable.w_free);
                            break;
                        case "w_broken":
                            washer_button_2.setBackgroundResource(R.drawable.w_broken);
                            break;
                        default:
                            washer_button_2.setBackgroundResource(R.drawable.w_busy);
                            break;
                    }
                    switch (laundryMachines.getWasherStatusIcon(3)) {
                        case "w_free":
                            washer_button_3.setBackgroundResource(R.drawable.w_free);
                            break;
                        case "w_broken":
                            washer_button_3.setBackgroundResource(R.drawable.w_broken);
                            break;
                        default:
                            washer_button_3.setBackgroundResource(R.drawable.w_busy);
                            break;
                    }
                    switch (laundryMachines.getWasherStatusIcon(4)) {
                        case "w_free":
                            washer_button_4.setBackgroundResource(R.drawable.w_free);
                            break;
                        case "w_broken":
                            washer_button_4.setBackgroundResource(R.drawable.w_broken);
                            break;
                        default:
                            washer_button_4.setBackgroundResource(R.drawable.w_busy);
                            break;
                    }

                    // dryer icons
                    Button dryer_button_1 = (Button) findViewById(R.id.dryer_1);
                    Button dryer_button_2 = (Button) findViewById(R.id.dryer_2);
                    Button dryer_button_3 = (Button) findViewById(R.id.dryer_3);
                    Button dryer_button_4 = (Button) findViewById(R.id.dryer_4);
                    switch (laundryMachines.getDryerStatusIcon(1)) {
                        case "d_free":
                            dryer_button_1.setBackgroundResource(R.drawable.d_free);
                            break;
                        case "d_broken":
                            dryer_button_1.setBackgroundResource(R.drawable.d_broken);
                            break;
                        default:
                            dryer_button_1.setBackgroundResource(R.drawable.d_busy);
                            break;
                    }
                    switch (laundryMachines.getDryerStatusIcon(2)) {
                        case "d_free":
                            dryer_button_2.setBackgroundResource(R.drawable.d_free);
                            break;
                        case "d_broken":
                            dryer_button_2.setBackgroundResource(R.drawable.d_broken);
                            break;
                        default:
                            dryer_button_2.setBackgroundResource(R.drawable.d_busy);
                            break;
                    }
                    switch (laundryMachines.getDryerStatusIcon(3)) {
                        case "d_free":
                            dryer_button_3.setBackgroundResource(R.drawable.d_free);
                            break;
                        case "d_broken":
                            dryer_button_3.setBackgroundResource(R.drawable.d_broken);
                            break;
                        default:
                            dryer_button_3.setBackgroundResource(R.drawable.d_busy);
                            break;
                    }
                    switch (laundryMachines.getDryerStatusIcon(4)) {
                        case "d_free":
                            dryer_button_4.setBackgroundResource(R.drawable.d_free);
                            break;
                        case "d_broken":
                            dryer_button_4.setBackgroundResource(R.drawable.d_broken);
                            break;
                        default:
                            dryer_button_4.setBackgroundResource(R.drawable.d_busy);
                            break;
                    }

                    // update My Loads area
                    ListView myLoadList = (ListView) findViewById(R.id.my_loads_list);
                    myLoadTracking.clear();
                    // washers
                    for (int i=0; i < 4; i++) {
                        if (laundryMachines.userTrackedWashers[i] != null) {
                            if (laundryMachines.userTrackedWashers[i] == true && (!laundryMachines.getWasherStatus(1 + i).equals("REPAIR"))) {
                                String formattedText = String.format("%-12s%12s", "Washer " + (i+1),  laundryMachines.getWasherStatus(1 + i));
                                if (laundryMachines.getWasherStatus(i+1).equals("FREE")) {
                                    formattedText = String.format("%-16s%12s", "Washer " + (i+1),  "FINISHED");
                                    laundryMachines.userTrackedWashers[i] = false;
                                    myLoadTracking.add(formattedText);
                                    notification("washing");
                                } else {
                                    myLoadTracking.add(formattedText);
                                }
                            }
                        }
                    }

                    // TODO: dryer tracking causing problems
//                    // dryers
                    for (int i=0; i < 4; i++) {
                        if (laundryMachines.userTrackedDryers[i] != null) {
                            if (laundryMachines.userTrackedDryers[i] == true  && (!laundryMachines.getDryerStatus(1 + i).equals("REPAIR"))) {
                                String formattedText = String.format("%-12s   %12s", "Dryer " + (i+1),  laundryMachines.getDryerStatus(1 + i));
                                if (laundryMachines.getDryerStatus(i+1).equals("FREE")) {
                                    formattedText = String.format("%-16s   %12s", "Dryer " + (i+1),  "FINISHED");
                                    laundryMachines.userTrackedDryers[i] = false;
                                    myLoadTracking.add(formattedText);
                                    notification("drying");
                                } else {
                                    myLoadTracking.add(formattedText);
                                }
                            }
                        }
                    }
                    String[] myLoadTrackingArray = null;
                    if (myLoadTracking.isEmpty()) {
                        myLoadTrackingArray = new String[] {"You are not tracking any machine"};
                    } else {
                        myLoadTrackingArray = new String[myLoadTracking.size()];
                        myLoadTracking.toArray(myLoadTrackingArray);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_text,  myLoadTrackingArray);
                    myLoadList.setAdapter(adapter);

//                    // washer icons
//                    Button washer_button_1 = (Button) findViewById(R.id.washer_1);
//                    Button washer_button_2 = (Button) findViewById(R.id.washer_2);
//                    Button washer_button_3 = (Button) findViewById(R.id.washer_3);
//                    Button washer_button_4 = (Button) findViewById(R.id.washer_4);
//                    washer_button_1.setBackground(Drawable.createFromPath(laundryMachines.getWasherStatusIcon(1)));
//                    washer_button_2.setBackground(Drawable.createFromPath(laundryMachines.getWasherStatusIcon(2)));
//                    washer_button_3.setBackground(Drawable.createFromPath(laundryMachines.getWasherStatusIcon(3)));
//                    washer_button_4.setBackground(Drawable.createFromPath(laundryMachines.getWasherStatusIcon(4)));

//                    // dryer icons
//                    Button dryer_button_1 = (Button) findViewById(R.id.dryer_1);
//                    Button dryer_button_2 = (Button) findViewById(R.id.dryer_2);
//                    Button dryer_button_3 = (Button) findViewById(R.id.dryer_3);
//                    Button dryer_button_4 = (Button) findViewById(R.id.dryer_4);
//                    dryer_button_1.setBackground(Drawable.createFromPath(laundryMachines.getDryerStatusIcon(1)));
//                    dryer_button_2.setBackground(Drawable.createFromPath(laundryMachines.getDryerStatusIcon(2)));
//                    dryer_button_3.setBackground(Drawable.createFromPath(laundryMachines.getDryerStatusIcon(3)));
//                    dryer_button_4.setBackground(Drawable.createFromPath(laundryMachines.getDryerStatusIcon(4)));
                }
            });
        }
    }

    public void onMachineClicked(View view) {
        Intent intent = new Intent(MainStatus.this,SelectedMachineActivity.class);
        intent.putExtra("id", view.getId());
        startActivity(intent);
    }

    public void onDryerClicked(View view) {
        Intent intent = new Intent(MainStatus.this,SelectedDryerActivity.class);
        intent.putExtra("id", view.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                gotoSettingsScreen();
                return true;
            case R.id.payment:
                gotoPaymentScreen();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gotoSettingsScreen(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void gotoPaymentScreen(){
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View customNav = LayoutInflater.from(this).inflate(R.layout.main_screen_action_bar, null); // layout which contains your button.
        actionBar.setCustomView(customNav, lp1);
    }

    private void loadBalanceValue(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        String balanceVal = sharedPref.getString("balanceVal", "");
        if (balanceVal.equals("")){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("balanceVal", "0.0");
            editor.commit();
            balanceVal = "0.0";
        }
        TextView textView = (TextView) findViewById(R.id.main_screen_balanceVal);
        textView.setText(balanceVal);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        String balanceVal = sharedPref.getString("balanceVal", "");
        TextView textView = (TextView) findViewById(R.id.main_screen_balanceVal);
        textView.setText(balanceVal);
    }

    public void notification(String type) {
        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_smiling_face_24dp)
                        .setContentTitle("CSCC Laundry")
                        .setContentText("Your " + type + " cycle has finished!");
        Intent resultIntent = new Intent(this, MainStatus.class);
        mBuilder.setContentIntent(null);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(001, mBuilder.build());
    }
}

