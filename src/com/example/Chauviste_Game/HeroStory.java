package com.example.Chauviste_Game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class HeroStory extends Activity {

    public LinearLayout writerLayout;
    public ImageView heroImage;
    public String heroName;
    public String story;
    public int imageResource;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.story);

        heroImage = (ImageView) findViewById(R.id.hero_image_story);
        writerLayout = (LinearLayout) findViewById(R.id.writer_layout);
        heroImage.setY(200);

        getIntentResult();
        story = heroName + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eget velit nec erat iaculis volutpat eu non purus. Nullam vitae dignissim ante, at ullamcorper nisl. Quisque ac nulla magna. Vivamus eu dapibus dolor. Aenean et enim ac urna facilisis viverra. Praesent sagittis erat id tincidunt elementum. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        final TypeWriter writer = createWriter(story);
        writerLayout.addView(writer);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("heroResource",imageResource);
                getApplicationContext().startActivity(intent);
                finish();
            }
        },getWaitingTime(story));
    }

    public TypeWriter createWriter(String text){
        Typeface type = Typeface.createFromAsset(getAssets(), "font/feather.ttf");
        TypeWriter writer = new TypeWriter(getApplicationContext());
        writer.setX(5);
        writer.setY(200);
        writer.setCharacterDelay(35);
        writer.animateText(text);
        writer.setTextColor(Color.BLACK);
        writer.setTextSize(20);
        writer.setTypeface(type);
        return writer;
    }

    public int getWaitingTime(String text){
        return text.length()*35 + 5000;
    }

    public void getIntentResult(){
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        imageResource = intent.getIntExtra("heroResource",R.drawable.hero_cowboy);
        heroImage.setBackgroundResource(imageResource);
        heroName = extra.getString("heroName");
        Toast.makeText(getApplicationContext(),heroName,Toast.LENGTH_SHORT).show();
    }
}
