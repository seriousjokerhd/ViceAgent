package login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import mainui.MainActivity;
import viceagent.com.viceagent.R;


public class SignUpActivity extends ActionBarActivity {

    protected TextView mLoginTextVeiw;
    protected EditText mName;
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mSignUpButton;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mName = (EditText) findViewById(R.id.nameField);
        mUsername = (EditText) findViewById(R.id.usernameSignUpField);
        mPassword = (EditText) findViewById(R.id.passwordSignUpField);
        mSignUpButton = (Button) findViewById(R.id.signupButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();


                name = name.trim();
                username = username.trim();
                password = password.trim();
                if (name.isEmpty()) {

                    mName.setError(getString(R.string.signup_name_error));
                }
                else if (username.length() != 10 ){

                    mUsername.setError(getString(R.string.phone_number_error));
                }
                else if (password.isEmpty()){
                    mPassword.setError(getString(R.string.password_error));
                }
                else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    ParseUser newUser = new ParseUser();
                    newUser.put("name",name);
                    newUser.setUsername(username);
                    newUser.setPassword(password);

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setTitle(getString(R.string.signup_error_title)).setMessage(e.getMessage()).setPositiveButton(android.R.string.ok, null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });

        mLoginTextVeiw = (TextView) findViewById(R.id.loginLable);
        mLoginTextVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }


}
