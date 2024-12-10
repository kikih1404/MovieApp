package id.example.movieapps.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import id.example.movieapps.Adapters.SlidersAdapter;
import id.example.movieapps.Domains.SliderItems;
import id.example.movieapps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnabel = new Runnable() {
        @Override
        public void run() {
            binding.viewPager23.setCurrentItem(binding.viewPager23.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    private void initBanner() {

    }
    private void banners(ArrayList<SliderItems> items) {
        binding.viewPager23.setAdapter(new SlidersAdapter(binding.viewPager23, items));
        binding.viewPager23.setClipToPadding(false);
        binding.viewPager23.setClipChildren(false);
        binding.viewPager23.setOffscreenPageLimit(3);
        binding.viewPager23.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 -Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        binding.viewPager23.setPageTransformer(compositePageTransformer);
        binding.viewPager23.setCurrentItem(1);
        binding.viewPager23.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnabel);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnabel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnabel, 2000);
    }
}