package com.example.administrator.androidncck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidncck.Iview.ISingin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1;
    TextView txtGoogleUser;
    Button btn_Post, btn_ViewList, btn_GoAdd, btn_Logout;
    String GGID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onIt();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
        btn_ViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListMonHoc.class);
                startActivity(intent);
            }
        });
        btn_GoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddProduct.class);
                startActivity(intent);
            }
        });

        btn_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GGID = txtGoogleUser.getText().toString();
                Map<String,String> mMap = new HashMap<>();
                mMap.put("google_id",GGID);
                mMap.put("firebase_url","https://androidck-5705c.firebaseio.com/");
                new LoginAsyncTask(MainActivity.this, new ISingin() {
                    @Override
                    public void onLoginSuccess(String m) {
                        Toast.makeText(MainActivity.this,m,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoginFail(String m) {
                        Toast.makeText(MainActivity.this,m,Toast.LENGTH_SHORT).show();
                    }
                },mMap).execute("http://vidoandroid.vidophp.tk/api/FireBase/PushData");
            }
        });
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


    }

    private void onIt() {
        txtGoogleUser = (TextView)findViewById(R.id.txtGoogleUser);
        btn_GoAdd = (Button)findViewById(R.id.btn_Add_Product);
        btn_Post = (Button)findViewById(R.id.btn_Main_Post);
        btn_ViewList = (Button)findViewById(R.id.btn_ListProduct);
        btn_Logout = (Button)findViewById(R.id.btn_Logout);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signIn();
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("google+ singin log: ", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    private void updateUI(GoogleSignInAccount account){
        if (account != null){
            txtGoogleUser.setText(" "+ account.getId());
        }
    }
}
