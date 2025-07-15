package com.rdxstudio.colorirbrincar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;

public class MainActivity extends AppCompatActivity {

    private RewardedAd rewardedAd;
    private int coins = 0;
    private Button btnShowAd, btnDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);

        btnShowAd = findViewById(R.id.btnShowAd);
        btnDraw = findViewById(R.id.btnDraw);

        loadRewardedAd();

        btnShowAd.setOnClickListener(view -> {
            if (rewardedAd != null) {
                rewardedAd.show(MainActivity.this, rewardItem -> {
                    int rewardAmount = rewardItem.getAmount();
                    coins += rewardAmount;
                    Toast.makeText(this, "+ " + rewardAmount + " moedas!", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(this, "Anúncio ainda não carregado.", Toast.LENGTH_SHORT).show();
                loadRewardedAd();
            }
        });

        btnDraw.setOnClickListener(v -> {
            if (coins >= 1) {
                coins--;
                Intent intent = new Intent(this, PaintActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Assista um anúncio para ganhar moedas!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this,
                "ca-app-pub-5417633247801485/8010268172",
                adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedAd ad) {
                        rewardedAd = ad;
                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                loadRewardedAd();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                rewardedAd = null;
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        rewardedAd = null;
                    }
                });
    }
}
