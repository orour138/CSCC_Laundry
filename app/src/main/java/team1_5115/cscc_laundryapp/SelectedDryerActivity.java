package team1_5115.cscc_laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SelectedDryerActivity extends AppCompatActivity {
    int selectdMachineId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_machine_dryer);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("id")) {
            selectdMachineId = extras.getInt("id");
            Button icon = (Button)findViewById(selectdMachineId);
            icon.setAlpha(1);
        }
    }

    public void onMaintenanceSelected(View view) {
        Intent intent = new Intent(SelectedDryerActivity.this, MaintenanceDryer.class);
        startActivity(intent);
    }

    public void onCycleSelected(View view) {
        Intent intent = new Intent(SelectedDryerActivity.this, CycleSelectDryer.class);
        startActivity(intent);
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
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}