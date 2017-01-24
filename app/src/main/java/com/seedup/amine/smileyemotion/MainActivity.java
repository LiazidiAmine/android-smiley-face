package com.seedup.amine.smileyemotion;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    private View mMessengerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.content_main);

        mMessengerButton = findViewById(R.id.messenger_send_button);

        mMessengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMessengerButtonClicked();
            }
        });
    }

    private void onMessengerButtonClicked() {
        Uri contentUri =
                Uri.parse("android.resource://com.seedup.amine.smileyemotion/" + R.drawable.tree);
        String mimeType = "image/jpeg";

        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.newBuilder(contentUri, mimeType)
                        .setMetaData("{ \"image\" : \"tree\" }")
                        .build();

        MessengerUtils.shareToMessenger(
                this,
                REQUEST_CODE_SHARE_TO_MESSENGER,
                shareToMessengerParams);
    }
}
