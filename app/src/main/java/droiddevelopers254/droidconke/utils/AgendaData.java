package droiddevelopers254.droidconke.utils;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.Agenda;

public class AgendaData {
    public static List<Agenda> getAgendas(){
        List<Agenda> agendaList = new ArrayList<>();

        Agenda agenda = new Agenda("Registration","8:00 AM - 9:00 AM", R.color.colorGrey,R.drawable.ic_verified_user_black_24dp);
        agendaList.add(agenda);

        Agenda agenda1 = new Agenda("Welcome Words","9:00 AM - 9:15 AM", R.color.colorGrey,R.drawable.ic_verified_user_black_24dp);
        agendaList.add(agenda1);

        Agenda agenda2 = new Agenda("Keynote","9:15 AM - 10:00 AM", R.color.colorSunflowerYellow,R.drawable.ic_star_black_24dp);
        agendaList.add(agenda2);


        Agenda agenda3 = new Agenda("Codelab 1","10:15 AM - 11:30 AM", R.color.colorFavourite,R.drawable.ic_code_black_24dp);
        agendaList.add(agenda3);

        Agenda agenda4 = new Agenda("Tea Break","11:30 AM - 10:45 AM", R.color.colorAquaMarine,R.drawable.ic_restaurant_black_24dp);
        agendaList.add(agenda4);

        Agenda agenda5 = new Agenda("Sponsor slot","11:45 AM - 12:15 PM", R.color.colorFavourite,R.drawable.ic_person_black_24dp);
        agendaList.add(agenda5);

        Agenda agenda6 = new Agenda("Codelab 2","12:15 PM - 01:00 PM", R.color.colorNeonBlue,R.drawable.ic_code_black_24dp);
        agendaList.add(agenda6);

        Agenda agenda7 = new Agenda("Lunch","01:00 PM - 2:00 PM", R.color.colorAquaMarine,R.drawable.ic_restaurant_black_24dp);
        agendaList.add(agenda7);

        Agenda agenda8 = new Agenda("Office Hours","02:00 PM - 03:30 PM", R.color.colorLighterBlue,R.drawable.ic_supervisor_account_black_24dp);
        agendaList.add(agenda8);

        Agenda agenda9 = new Agenda("Hiring Sprints","02:00 PM - 03:30 PM", R.color.colorLighterBlue,R.drawable.ic_verified_user_black_24dp);
        agendaList.add(agenda9);

        Agenda agenda10 = new Agenda("Coding Interviews","02:00 PM - 03:30 PM", R.color.colorLighterBlue,R.drawable.ic_verified_user_black_24dp);
        agendaList.add(agenda10);

        Agenda agenda11 = new Agenda("Pitching","02:00 PM - 03:30 PM", R.color.colorLighterBlue,R.drawable.ic_verified_user_black_24dp);
        agendaList.add(agenda11);

        Agenda agenda12 = new Agenda("Codelab 3","03:30 PM - 9:30 AM", R.color.colorNeonBlue,R.drawable.ic_code_black_24dp);
        agendaList.add(agenda12);

        return agendaList;
    }
}
