package com.zoportfolio.testalarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TestBroadcast extends BroadcastReceiver {

    private static final String TAG = "TestBroadcast.TAG";

    private static final String RAND_STRING = "GOJIRA WALKS";
    private static final String RAND_STRING_2 = "GOJIRA WALKS FURTHER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            if(intent.hasExtra(RAND_STRING)) {
                createToast(context, intent.getStringExtra(RAND_STRING));
            }else if(intent.hasExtra(RAND_STRING_2)) {
                createToast(context, intent.getStringExtra(RAND_STRING_2));
            }
        }
    }

    private void createToast(Context _context, String _message) {
        //Create the toast.
        Toast.makeText(_context, _message, Toast.LENGTH_SHORT).show();
    }

}
