package team1_5115.cscc_laundryapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class PopupMachineSelect extends AppCompatActivity implements CycleSelectFragment.OnFragmentInteractionListener, CycleConfirmFragment.OnFragmentInteractionListener {
    public static int totalTime = 0;
    public static int deductMoney = 0;
    private int selectedMachineId = 0;
    private int button_id_num;
    private boolean isSuperCycle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cycle_select_washers);
        // start threads to update machine status
        ScheduledThreadPoolExecutor updateThreadPool = new ScheduledThreadPoolExecutor(1);
        updateThreadPool.scheduleAtFixedRate(new updateMachineStatusCallable(), 0, 1, TimeUnit.SECONDS);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("id")) {
            selectedMachineId = extras.getInt("id");
            Button icon = (Button) findViewById(selectedMachineId);
            icon.setAlpha(1);

            button_id_num = Integer.parseInt(icon.getTag().toString());
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(extras.containsKey("isQuickStart")){
            CycleConfirmFragment confirmFragment = new CycleConfirmFragment();
            Bundle args = new Bundle();
            args.putBoolean("isQuickStart", true);
            String washerID = ((Button)findViewById(selectedMachineId)).getText().toString();
            args.putString("washerID", washerID);
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String washerPreClothes = sharedPref.getString("washerPreClothes", "Whites");
            boolean preSuperCycle = sharedPref.getBoolean("washerPreSuperCycle", false);
            args.putString("preClothes", washerPreClothes);
            args.putBoolean("preSuperCycle", preSuperCycle);
            this.isSuperCycle = preSuperCycle;
            confirmFragment.setArguments(args);
            transaction.add(R.id.cycle_select_washer_container, confirmFragment, "confirm_fragment");
        }else{
            CycleSelectFragment issueFragment = new CycleSelectFragment();
            transaction.add(R.id.cycle_select_washer_container, issueFragment, "issue_fragment");
        }
        transaction.commit();
    }

    class updateMachineStatusCallable implements Runnable {
        Handler handler = new Handler();
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    LaundryMachines laundryMachines = LaundryMachines.getInstance();
                    TextView washer_text_1 = (TextView) findViewById(R.id.washer_text_1);
                    TextView washer_text_2 = (TextView) findViewById(R.id.washer_text_2);
                    TextView washer_text_3 = (TextView) findViewById(R.id.washer_text_3);
                    TextView washer_text_4 = (TextView) findViewById(R.id.washer_text_4);
                    washer_text_1.setText(laundryMachines.getWasherStatus(1));
                    washer_text_2.setText(laundryMachines.getWasherStatus(2));
                    washer_text_3.setText(laundryMachines.getWasherStatus(3));
                    washer_text_4.setText(laundryMachines.getWasherStatus(4));

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
                }
            });
        }
    }


    public void onRadioButtonClicked(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.cycle_radio_group);
        RadioButton radio_1 = (RadioButton) findViewById(R.id.cycle_1_radio);
        RadioButton radio_2 = (RadioButton) findViewById(R.id.cycle_2_radio);
        RadioButton radio_3 = (RadioButton) findViewById(R.id.cycle_3_radio);
        RadioButton radio_4 = (RadioButton) findViewById(R.id.cycle_4_radio);
        radio_1.setTypeface(null, Typeface.NORMAL);
        radio_2.setTypeface(null, Typeface.NORMAL);
        radio_3.setTypeface(null, Typeface.NORMAL);
        radio_4.setTypeface(null, Typeface.NORMAL);
        RadioButton newRadio = (RadioButton) radioGroup.findViewById(view.getId());
        newRadio.setTypeface(null, Typeface.BOLD);
    }

    public void onIssueSubmitButtonClicked(View view) {
        CycleSelectFragment oldFragment = (CycleSelectFragment) getSupportFragmentManager().findFragmentById(R.id.cycle_options_fragment);
        CycleConfirmFragment newFragment = new CycleConfirmFragment();
        Bundle args = new Bundle();
        String washerID = ((Button)findViewById(selectedMachineId)).getText().toString();
        args.putString("washerID", washerID);
        int checkedRadioButtonId = ((RadioGroup)findViewById(R.id.cycle_radio_group)).getCheckedRadioButtonId();
        String washerPreClothes = ((RadioButton)findViewById(checkedRadioButtonId)).getText().toString();
        args.putString("preClothes", washerPreClothes);
        boolean preSuperCycle = ((ToggleButton)findViewById(R.id.sc_toggle)).isChecked();
        args.putBoolean("preSuperCycle", preSuperCycle);
        this.isSuperCycle = preSuperCycle;
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cycle_select_washer_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onCycleConfirmButtonClicked(View view) {
        // update the status time and icon
        LaundryMachines laundryMachines = LaundryMachines.getInstance();
        if (laundryMachines.setWasherTimer(button_id_num, Long.valueOf(2400000)) == false) {
            Toast.makeText(this.getBaseContext(), "Tracking Request Failed", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this.getBaseContext(), "Tracking Washer " + button_id_num, Toast.LENGTH_LONG).show();
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
            Float balanceVal = Float.parseFloat(sharedPref.getString("balanceVal", ""));
            if (isSuperCycle)
                balanceVal -= 2.5f;
            else
                balanceVal -= 1.75f;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("balanceVal", Float.toString(balanceVal));
            editor.commit();
            if (balanceVal < 5) {
                String message = "Low balance ! Please reload money card soon.";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Intent intent = new Intent(PopupMachineSelect.this, MainStatus.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Intent intent = new Intent(PopupMachineSelect.this, MainStatus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    public void onConfirmSubmitButtonClicked(View view) {
        Toast.makeText(this.getBaseContext(), "A maintenance request has sent", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onCancelButtonClicked(View view) {
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.child_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(this, MainStatus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
