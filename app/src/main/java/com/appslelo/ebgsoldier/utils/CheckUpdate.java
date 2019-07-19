package com.appslelo.ebgsoldier.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.appslelo.ebgsoldier.BuildConfig;
import com.appslelo.ebgsoldier.R;
import com.appslelo.ebgsoldier.api.Api;
import com.appslelo.ebgsoldier.api.ApiClients;
import com.appslelo.ebgsoldier.model.ModelApkUrl;
import com.appslelo.ebgsoldier.model.ModelNewVer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckUpdate{

    private String strNewVerName;
    private String strNewVerMessage;
   private static String strUrl;
    private static String getVersionCode(Context tContext) {
        try {
            PackageInfo pInfo = tContext.getPackageManager().getPackageInfo(tContext.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static void alertFirUpdate(final Context tContext, String strNewVersion, String strNewVerMessage, final String strApkUrl){
        String strCurrentVersion = getVersionCode(tContext);
//        String strNewVersion = "1.2";
        if(Double.parseDouble(strCurrentVersion) < Double.parseDouble(strNewVersion)){
            new AlertDialog.Builder(tContext)
                    .setTitle("Update Available. Ver : "+strNewVersion)
                    .setMessage(strNewVerMessage)

                    .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            CustomLog.d(Constant.TAG, "Apk Url : "+strApkUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strApkUrl));
                            tContext.startActivity(intent);

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Update Later", null)
                    .setIcon(R.drawable.logo_main)
                    .show();

        }

    }

    public static void checkUpdateApi(final Context tContext){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNewVer>> call = api.updateVersion();
        call.enqueue(new Callback<List<ModelNewVer>>() {
            @Override
            public void onResponse(Call<List<ModelNewVer>> call, Response<List<ModelNewVer>> response) {
                List<ModelNewVer> tModels = response.body();
                int size= tModels.size()-1;
               String strNewVerName = tModels.get(size).getVerName();
               String strNewVerMessage = tModels.get(size).getUpdateMsg();
               String strApkUrl= tModels.get(size).getApkUrl();
                alertFirUpdate(tContext, strNewVerName,  strNewVerMessage, strApkUrl);
            }

            @Override
            public void onFailure(Call<List<ModelNewVer>> call, Throwable t) {

            }
        });
    }
}

