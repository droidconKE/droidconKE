package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder> {
    private List<SessionsEntity> sessionsModelList;
    private Context context;
    private SessionsEntity sessionsModel;
    private String starStatus,dayNumber;
    private StarredSessionModel starredSessionModel;
    //TODO use list adapter to compare two lists


    public SessionsAdapter(Context context,List<SessionsEntity> sessionsModelList,String dayNumber){
        this.sessionsModelList=sessionsModelList;
        this.context=context;
        this.dayNumber=dayNumber;
        starredSessionModel = new StarredSessionModel();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sessionCategoryText,sessionTimeText,sessionRoomText,sessionTitleText,sessionLabelText;
        ImageView starImg;
        LinearLayout sessionDetailsLinear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionCategoryText=itemView.findViewById(R.id.sessionCategoryText);
            sessionTitleText=itemView.findViewById(R.id.sessionTitleText);
            starImg=itemView.findViewById(R.id.starImg);
            sessionDetailsLinear=itemView.findViewById(R.id.sessionDetailsLinear);
            sessionLabelText=itemView.findViewById(R.id.sessionLabelText);

        }

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sessionsModel = sessionsModelList.get(position);

        holder.sessionTitleText.setText(sessionsModel.getTitle());
        holder.sessionCategoryText.setText(sessionsModel.getTopic());
        holder.sessionLabelText.setBackgroundColor(Color.parseColor(sessionsModel.getSession_color()));
        holder.starImg.setOnClickListener(view -> Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show());

        //check a session was previously starred
//        starStatus=sessionsModel.getStarred();
//        if (starStatus.equals("0")){
//            holder.starImg.setImageResource(R.drawable.ic_star_border_black_24dp);
//        }else if (starStatus.equals("1")){
//            holder.starImg.setImageResource(R.drawable.ic_star_blue_24dp);
//        }

        //change the star section in db
   /*     holder.starImg.setOnClickListener(view -> {
            if (starStatus.equals("0")){
                //start a session
                holder.starImg.setImageResource(R.drawable.ic_star_blue_24dp);

                //update in firebase
               databaseReference.child(dayNumber).child(String.valueOf(sessionsModelList.get(position).getId())).child("starred").setValue("1");

            }else if(starStatus.equals("1")){
                holder.starImg.setImageResource(R.drawable.ic_star_border_black_24dp);
                //update in firebase
                databaseReference.child(dayNumber).child(String.valueOf(sessionsModelList.get(position).getId())).child("starred").setValue("0");
            }

        });*/
/*

       //record the users starred session in their profile
        starredSessionModel.setDay(String.valueOf(0));//I use 0 for day_one and 1 for day_two
        starredSessionModel.setSession_id(String.valueOf(sessionsModel.getId()));

        databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("starred").push().setValue(starredSessionModel);

        //this will aid in tracking every starred session and then send a push notification
        databaseReference.child("starred_sessions").push().setValue(starredSessionModel);
*/


        //start session detail view
        holder.sessionDetailsLinear.setOnClickListener(view -> {
            Intent  intent = new Intent(context,SessionViewActivity.class);
            intent.putExtra("sessionId", sessionsModelList.get(position).getId());
            intent.putExtra("dayNumber",dayNumber);
            intent.putExtra("starred",sessionsModelList.get(position).getStarred());
            intent.putIntegerArrayListExtra("speakerId",sessionsModelList.get(position).getSpeaker_id());
            intent.putExtra("roomId",sessionsModelList.get(position).getRoom_id());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return sessionsModelList.size();
    }


}
