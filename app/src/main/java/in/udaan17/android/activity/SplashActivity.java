package in.udaan17.android.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import in.udaan17.android.R;
import in.udaan17.android.util.APIHelper;
import in.udaan17.android.util.Helper;
import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener, ParticleView.ParticleAnimListener {

    boolean dataFetched = false;
    boolean animationComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.getString(R.string.title_activity_splash));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializeElements();
        if (Helper.hasNetworkConnection(this)) {
            APIHelper.fetchData(this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        SharedPreferences sharedPreferences = SplashActivity.this.getSharedPreferences(SplashActivity.this.getString(R.string.prefs_file_name), Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString(SplashActivity.this.getString(R.string.prefs_data_json), response.toString()).apply();
                        APIHelper.fetchDeveloperData(SplashActivity.this, SplashActivity.this, SplashActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } else if (!this.getSharedPreferences(this.getString(R.string.prefs_file_name), Context.MODE_PRIVATE).contains("data_json")) {
            Helper.showNetworkAlertPopup(this);
        } else {
            if (animationComplete) {
                MainActivity.startActivity(this);
            }
        }

    }

    private void initializeElements() {
        ParticleView particleView = (ParticleView) this.findViewById(R.id.activity_splash_particle_view);
        particleView.startAnim();
        particleView.setOnParticleAnimListener(this);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("DEVELOPER_FETCH_ERROR", error.networkResponse.toString());
    }

    @Override
    public void onResponse(JSONArray response) {
        dataFetched = true;
        SharedPreferences sharedPreferences = this.getSharedPreferences(this.getString(R.string.prefs_file_name), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(this.getString(R.string.prefs_developer_data_json), response.toString()).apply();
        if (animationComplete && dataFetched) {
            MainActivity.startActivity(this);
        }
    }

    @Override
    public void onAnimationEnd() {
        animationComplete = true;
        if (animationComplete && dataFetched) {
            MainActivity.startActivity(this);
        }
    }
}
