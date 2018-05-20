package droiddevelopers254.droidconke.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsModel;

public class SessionViewActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    int sessionId;
    TextView sessionViewTitleText;
    FloatingActionButton fab;

    @BindView(R.id.txtSessionTime)
    TextView txtSessionTime;
    @BindView(R.id.txtSessionRoom)
    TextView txtSessionRoom;
    @BindView(R.id.txtSessionDesc)
    TextView txtSessionDesc;
    @BindView(R.id.txtSessionCategory)
    TextView txtSessionCategory;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //get extras
        Intent extraIntent=getIntent();
        sessionId = extraIntent.getIntExtra("sessionId",0);

      // Toast.makeText(this, String.valueOf(sessionId),Toast.LENGTH_LONG).show();

        getSessionData(sessionId);

        bottomAppBar=findViewById(R.id.bottomAppBar);
        sessionViewTitleText=findViewById(R.id.sessionViewTitleText);
        fab=findViewById(R.id.fab);
        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar);

       // sessionViewTitleText.setText(sessionId);

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int id= item.getItemId();
            if (id == R.id.action_share){
                Intent shareSession=new Intent();
                shareSession.setAction(Intent.ACTION_SEND);
                shareSession.putExtra(Intent.EXTRA_TEXT,"Check out "+"'"+ sessionId +"' at "+getString(R.string.droidcoke_hashtag)+"\n"+getString(R.string.droidconke_site));
                shareSession.setType("text/plain");
                startActivity(shareSession);
            }
            if (id == R.id.action_map){
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        //star a session
        fab.setOnClickListener(view -> {
            fab.setImageResource(R.drawable.ic_star_blue_24dp);
        });

    }

    private void getSessionData(int sessionId){

        databaseReference.child("day_one").child(String.valueOf(sessionId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null){

                    SessionsModel sessionsModel = dataSnapshot.getValue(SessionsModel.class);

                    //set the data on the view
                    txtSessionTime.setText(sessionsModel.getStartTimeStamp() + " - " + sessionsModel.getEndTimeStamp());
                    txtSessionRoom.setText(sessionsModel.getRoom());
                    txtSessionDesc.setText(sessionsModel.getDescription());
                    txtSessionCategory.setText(sessionsModel.getTopic());

                }else{

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(SessionViewActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
