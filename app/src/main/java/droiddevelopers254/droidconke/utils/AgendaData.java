package droiddevelopers254.droidconke.utils;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.AgendaModel;

public class AgendaData {
    public static List<AgendaModel> getAgendas(){
        List<AgendaModel> agendaModelList = new ArrayList<>();

        AgendaModel agendaModel = new AgendaModel("Registration","8:00 AM - 9:00 AM", R.drawable.ic_verified_user_black_24dp,R.color.colorGrey);
        agendaModelList.add(agendaModel);

        AgendaModel agendaModel1 = new AgendaModel("Welcome Words","9:00 AM - 9:15 AM", R.drawable.ic_verified_user_black_24dp,R.color.colorGrey);
        agendaModelList.add(agendaModel1);

        AgendaModel agendaModel2 = new AgendaModel("Keynote","9:15 AM - 10:00 AM", R.drawable.ic_star_black_24dp,R.color.colorSunflowerYellow);
        agendaModelList.add(agendaModel2);


        AgendaModel agendaModel3 = new AgendaModel("Codelab 1","10:15 AM - 11:30 AM",R.drawable.ic_code_black_24dp, R.color.colorFavourite);
        agendaModelList.add(agendaModel3);

        AgendaModel agendaModel4 = new AgendaModel("Tea Break","11:30 AM - 10:45 AM", R.drawable.ic_restaurant_black_24dp,R.color.colorAquaMarine);
        agendaModelList.add(agendaModel4);

        AgendaModel agendaModel5 = new AgendaModel("Sponsor slot","11:45 AM - 12:15 PM", R.drawable.ic_person_black_24dp,R.color.colorFavourite);
        agendaModelList.add(agendaModel5);

        AgendaModel agendaModel6 = new AgendaModel("Codelab 2","12:15 PM - 01:00 PM", R.drawable.ic_code_black_24dp,R.color.colorNeonBlue);
        agendaModelList.add(agendaModel6);

        AgendaModel agendaModel7 = new AgendaModel("Lunch","01:00 PM - 2:00 PM", R.drawable.ic_restaurant_black_24dp,R.color.colorAquaMarine);
        agendaModelList.add(agendaModel7);

        AgendaModel agendaModel8 = new AgendaModel("Office Hours","02:00 PM - 03:30 PM", R.drawable.ic_supervisor_account_black_24dp,R.color.colorLighterBlue);
        agendaModelList.add(agendaModel8);

        AgendaModel agendaModel9 = new AgendaModel("Hiring Sprints","02:00 PM - 03:30 PM", R.drawable.ic_verified_user_black_24dp,R.color.colorLighterBlue);
        agendaModelList.add(agendaModel9);

        AgendaModel agendaModel10 = new AgendaModel("Coding Interviews","02:00 PM - 03:30 PM", R.drawable.ic_verified_user_black_24dp,R.color.colorLighterBlue);
        agendaModelList.add(agendaModel10);

        AgendaModel agendaModel11 = new AgendaModel("Pitching","02:00 PM - 03:30 PM",R.drawable.ic_verified_user_black_24dp, R.color.colorLighterBlue);
        agendaModelList.add(agendaModel11);

        AgendaModel agendaModel12 = new AgendaModel("Codelab 3","03:30 PM - 05:30 PM", R.drawable.ic_code_black_24dp,R.color.colorNeonBlue);
        agendaModelList.add(agendaModel12);

        return agendaModelList;
    }
}
