package droiddevelopers254.droidconke.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import droiddevelopers254.droidconke.R;

public class SessionViewActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    String sessionTitle;
    TextView sessionViewTitleText;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        Intent extraIntent=getIntent();
        sessionTitle= extraIntent.getStringExtra("sessionTitle");

        bottomAppBar=findViewById(R.id.bottomAppBar);
        sessionViewTitleText=findViewById(R.id.sessionViewTitleText);
        fab=findViewById(R.id.fab);
        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar);

        sessionViewTitleText.setText(sessionTitle);

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int id= item.getItemId();
            if (id == R.id.action_share){
                Intent shareSession=new Intent();
                shareSession.setAction(Intent.ACTION_SEND);
                shareSession.putExtra(Intent.EXTRA_TEXT,"Check out "+"'"+sessionTitle+"' at "+getString(R.string.droidcoke_hashtag)+"\n"+getString(R.string.droidconke_site));
                shareSession.setType("text/plain");
                startActivity(shareSession);
            }
            if (id == R.id.action_map){
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        //reserve an event
        fab.setOnClickListener(view -> {
            fab.setImageResource(R.drawable.ic_star_blue_24dp);
        });

    }

}
