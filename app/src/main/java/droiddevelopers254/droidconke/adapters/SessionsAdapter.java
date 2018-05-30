package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder> {
    private List<SessionsModel> sessionsModelList;
    private Context context;
    private SessionsModel sessionsModel;
    String starStatus,dayNumber;
    private DatabaseReference databaseReference;


    public SessionsAdapter(Context context,List<SessionsModel> sessionsModelList,String dayNumber){
        this.sessionsModelList = sessionsModelList;
        this.context=context;
        this.dayNumber=dayNumber;
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sessionCategoryText,sessionTimeText,sessionRoomText,sessionTitleText;
        ImageView starImg;
        LinearLayout sessionDetailsLinear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionCategoryText=itemView.findViewById(R.id.sessionCategoryText);
            sessionTimeText=itemView.findViewById(R.id.sessionTimeText);
            sessionTitleText=itemView.findViewById(R.id.sessionTitleText);
            sessionRoomText=itemView.findViewById(R.id.sessionRoomText);
            starImg=itemView.findViewById(R.id.starImg);
            sessionDetailsLinear=itemView.findViewById(R.id.sessionDetailsLinear);

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
        holder.sessionTimeText.setText(sessionsModel.getDuration());
        holder.sessionRoomText.setText(sessionsModel.getRoom());
        holder.sessionCategoryText.setText(sessionsModel.getTopic());

        //check a session was previously starred
        starStatus=sessionsModel.getStarred();
        if (starStatus.equals("0")){
            holder.starImg.setImageResource(R.drawable.ic_star_border_black_24dp);
        }else if (starStatus.equals("1")){
            holder.starImg.setImageResource(R.drawable.ic_star_blue_24dp);
        }

        //change the star section in db
        holder.starImg.setOnClickListener(view -> {
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

        });
        //start session detail view
        holder.sessionDetailsLinear.setOnClickListener(view -> {
            Intent  intent = new Intent(context,SessionViewActivity.class);
            intent.putExtra("sessionId", sessionsModelList.get(position).getId());
            intent.putExtra("dayNumber",dayNumber);
            intent.putExtra("starred",sessionsModelList.get(position).getStarred());
            intent.putExtra("speakerId",String.valueOf(sessionsModelList.get(position).getSpeaker_id()));
            intent.putExtra("roomId",String.valueOf(sessionsModelList.get(position).getRoom_id()));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        return sessionsModelList.size();
    }


}
