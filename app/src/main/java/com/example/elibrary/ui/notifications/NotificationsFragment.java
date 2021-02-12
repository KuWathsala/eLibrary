package com.example.elibrary.ui.notifications;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.elibrary.R;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getContext().getSystemService(getContext().NETWORK_STATS_SERVICE);

        printAllDataUsage();

        return root;
    }

    //when using NetworkStatsManager you need the subscriber id
    private String getSubscriberId(Context context, int networkType) {
        if (ConnectivityManager.TYPE_MOBILE == networkType) {
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        }
        return null;
    }


    // to get mobile data recived
    public long getPackageRxBytesMobile(Context context,NetworkStatsManager networkStatsManager,int packageUid) {
        NetworkStats networkStats = null;
        networkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_MOBILE,
                getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                0,
                System.currentTimeMillis(),
                packageUid);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        networkStats.getNextBucket(bucket);
        return bucket.getRxBytes();
    }


    // to get mobile data transmitted
    public long getPackageTxBytesMobile(Context context, NetworkStatsManager networkStatsManager, int packageUid) {
        NetworkStats networkStats = null;
        networkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_MOBILE,
                getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                0,
                System.currentTimeMillis(),
                packageUid);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getTxBytes();
    }


    // to get wifi data received
    public long getPackageRxBytesWifi(NetworkStatsManager networkStatsManager,int packageUid) {
        NetworkStats networkStats = null;
        networkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_WIFI,
                null,
                0,
                System.currentTimeMillis(),
                packageUid);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getRxBytes();
    }


    // to get wifi data transmitted
    public long getPackageTxBytesWifi(NetworkStatsManager networkStatsManager,int packageUid) {
        NetworkStats networkStats = null;
        networkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_WIFI,
                null,
                0,
                System.currentTimeMillis(),
                packageUid);
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket.getTxBytes();
    }


    // print to log all the data usage value per application
    public void printAllDataUsage(){
        PackageManager pm = getActivity().getPackageManager();
        // get all the applications in the phone
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getActivity().getApplicationContext().getSystemService(getContext().NETWORK_STATS_SERVICE);

        for (ApplicationInfo packageInfo : packages) {
            Log.d("MYLOG", String.valueOf(packageInfo.uid));
            Log.d("MYLOG", String.valueOf(packageInfo.name));
            Log.d("MYLOG", String.valueOf(packageInfo.packageName));

            // get data usage from trafficStats
            Log.d("MYLOG", String.valueOf(TrafficStats.getUidRxBytes(packageInfo.uid)));
            Log.d("MYLOG", String.valueOf(TrafficStats.getUidTxBytes(packageInfo.uid)));

            // get data usage from networkStatsManager using mobile
            Log.d("MYLOG", String.valueOf(getPackageRxBytesMobile(getContext(),networkStatsManager,packageInfo.uid)));
            Log.d("MYLOG", String.valueOf(getPackageTxBytesMobile(getContext(),networkStatsManager,packageInfo.uid)));

            // get data usage from networkStatsManager using wifi
            Log.d("MYLOG", String.valueOf(getPackageRxBytesWifi(networkStatsManager,packageInfo.uid)));
            Log.d("MYLOG", String.valueOf(getPackageTxBytesWifi(networkStatsManager,packageInfo.uid)));

        }
    }

}