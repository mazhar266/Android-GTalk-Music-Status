package com.android.gtalkstatus;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android.music.IMediaPlaybackService;
import com.android.gtalkstatus.XMPPTransfer;

public class MediaPlaybackServiceConnection implements ServiceConnection {

    public static final String LOG_NAME = "MediaPlaybackServiceConnection";

    public IMediaPlaybackService mService;

    public void onServiceConnected(ComponentName aName, IBinder aService) {

        Log.i(LOG_NAME, "Connected! Name: " + aName.getClassName());

        mService = IMediaPlaybackService.Stub.asInterface(aService);

        try {
            Log.i(LOG_NAME, "Playing track: " + mService.getTrackName());
            Log.i(LOG_NAME, "Playing artist: " + mService.getArtistName());

            String statusMessage = "Listening to: " + mService.getArtistName() + " - " + mService.getTrackName();

            XMPPTransfer gtalkConnector = new XMPPTransfer("username", "password");
            gtalkConnector.setStatus(statusMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void onServiceDisconnected(ComponentName aName) {
        Log.i(LOG_NAME, "Service disconnected");
    }
}
