package com.subtlyrude.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    /**
     * passwordLength declaration
     */
    int passwordLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method generates the secure password
     * @param view
     */
    public void generate(View view) {

        EditText userPasswordLength = findViewById(R.id.passwordLength);
        String passwordLengthString = userPasswordLength.getText().toString();

        /**
         * this if-else block helps to tackle the empty input issue
         */
        if (passwordLengthString.isEmpty()){
            Toast.makeText(this, "Length must be 8-24", Toast.LENGTH_SHORT).show();
        }
        else
            passwordLength = Integer.parseInt(passwordLengthString);

        EditText userKey = findViewById(R.id.key);
        String key = userKey.getText().toString();

        EditText userPassword = findViewById(R.id.password);
        String password = userPassword.getText().toString();

        /**
         * AES encryption
         */
        final String secretKey = key;
        String originalString = password;
        String encryptedString = AES.encrypt(originalString, secretKey);

        /**
         * this if-else block checks the upper and lower limits
         */
        if (passwordLength > 24 || passwordLength < 8) {

                Toast.makeText(this, "Length must be 8-24", Toast.LENGTH_SHORT).show();
        }
        else {
            /**
             * using the substring allows us to returns a secure password with user specific number of characters
             */
            display(encryptedString.substring(0, passwordLength));

            /**
             * this the code to copy the password automatically on to the clipboard
             */
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("securePassword", encryptedString.substring(0, passwordLength));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * this method displays a String
     * @param text
     */
    public void display(String text) {
        TextView t = findViewById(R.id.securePassword);
        t.setText(text);
    }
}
