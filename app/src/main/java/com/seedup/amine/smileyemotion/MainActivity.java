package com.seedup.amine.smileyemotion;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity {

    // This is the request code that the SDK uses for startActivityForResult. See the code below
    // that references it. Messenger currently doesn't return any data back to the calling
    // application.
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;

    private View mMessengerButton;
    private boolean mPicking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.content_main);

        mMessengerButton = findViewById(R.id.messenger_send_button);

        // If we received Intent.ACTION_PICK from Messenger, we were launched from a composer shortcut
        // or the reply flow.
        Intent intent = getIntent();
        if(Intent.ACTION_PICK.equals(intent.getAction())){
            mPicking = true;
        }

        mMessengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMessengerButtonClicked();
            }
        });
    }

    /**
     * Sending image method.
     */
    private void onMessengerButtonClicked() {
        Uri contentUri =
                Uri.parse("android.resource://com.seedup.amine.smileyemotion/" + R.drawable.tree);
        String mimeType = "image/jpeg";

        // Create the parameters for what we want to send to Messenger.
        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.newBuilder(contentUri, mimeType)
                        .setMetaData("{ \"image\" : \"tree\" }")
                        .build();

        if(mPicking){
            // If we were launched from Messenger, we call MessengerUtils.finishShareToMessenger to return
            // the content to Messenger.
            MessengerUtils.finishShareToMessenger(this, shareToMessengerParams);
        }else{
            // Otherwise, we were launched directly (for example, user clicked the launcher icon). We
            // initiate the broadcast flow in Messenger. If Messenger is not installed or Messenger needs
            // to be upgraded, this will direct the user to the play store.
            MessengerUtils.shareToMessenger(
                    this,
                    REQUEST_CODE_SHARE_TO_MESSENGER,
                    shareToMessengerParams);
        }

    }
}
