package com.minimax.settingslauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSettings();
            }
        });
    }
    
    private void launchSettings() {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "Открываем настройки", Toast.LENGTH_SHORT).show();
            } else {
                // Если нет прямого запуска, используем am start
                launchWithAmStart("com.android.settings");
            }
        } catch (Exception e) {
            launchWithAmStart("com.android.settings");
        }
    }
    
    private void launchWithAmStart(String packageName) {
        try {
            Runtime.getRuntime().exec(new String[]{"su", "-c", "am start --user 0 " + packageName});
            Toast.makeText(this, "Команда выполнена (требует root)", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            try {
                // Попытка без root
                Runtime.getRuntime().exec(new String[]{"am", "start", "--user", "0", packageName});
                Toast.makeText(this, "Команда выполнена", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Ошибка запуска: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}