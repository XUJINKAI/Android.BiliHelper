package com.jinkai.nav_drawer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jinkai.nav_drawer.Helper.ClipboardHelper;

public class NewShortcutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_shortcut);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupCreateShortcutButton();
        setupIntentLoader();
    }

    private void setupIntentLoader() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        boolean hasExtraText = intent.hasExtra(Intent.EXTRA_TEXT);
        boolean useIntent = Intent.ACTION_SEND.equals(action) && "text/plain".equals(type) && hasExtraText;

        if (useIntent) {
            SetEditText(intent.getStringExtra(Intent.EXTRA_TEXT));
            findViewById(R.id.createShortcutButton).performClick();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        String clipboardText = ClipboardHelper.getClipboardText(this);
        EditText subjectEditText = findViewById(R.id.subjectEditText);
        EditText urlEditText = findViewById(R.id.urlEditText);

        if (clipboardText != null
                && subjectEditText.getText().toString().isEmpty()
                && urlEditText.getText().toString().isEmpty()) {
            SetEditText(clipboardText);
        }
    }

    private void setupCreateShortcutButton() {
        EditText subjectEditText = findViewById(R.id.subjectEditText);
        EditText urlEditText = findViewById(R.id.urlEditText);
        EditText packageNameEditText = findViewById(R.id.packageNameEditText);

        findViewById(R.id.createShortcutButton).setOnClickListener(v -> {
            String subject = subjectEditText.getText().toString();
            String url = urlEditText.getText().toString();
            String packageName = packageNameEditText.getText().toString().isEmpty()
                    ? packageNameEditText.getHint().toString()
                    : packageNameEditText.getText().toString();
            if (ShortcutCreator.createShortcut(this, packageName, url, subject)) {
                // TODO: 改为异步方式，完成后关闭
                // finish();
            }
        });
    }

    private void SetEditText(@Nullable String text) {
        if (text == null) {
            return;
        }

        EditText subjectEditText = findViewById(R.id.subjectEditText);
        EditText urlEditText = findViewById(R.id.urlEditText);

        int index = text.lastIndexOf("https://b23.tv/");
        if (index > 0) {
            subjectEditText.setText(text.substring(0, index).trim());
            urlEditText.setText(text.substring(index).trim());
        } else if (text.startsWith("https://") || text.startsWith("bilibili://")) {
            urlEditText.setText(text);
        }
    }
}