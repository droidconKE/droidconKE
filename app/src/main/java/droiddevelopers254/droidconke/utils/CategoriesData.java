package droiddevelopers254.droidconke.utils;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.models.FiltersModel;

public class CategoriesData {
    public static List<FiltersModel> getCategories(){
        List<FiltersModel> filtersModelList= new ArrayList<>();

        FiltersModel filtersModel= new FiltersModel(0,true,"Codelabs");
        filtersModelList.add(filtersModel);

        FiltersModel filtersModel1= new FiltersModel(0,true,"Sessions");
        filtersModelList.add(filtersModel1);

        FiltersModel filtersModel2= new FiltersModel(0,true,"After Hours");
        filtersModelList.add(filtersModel2);

        FiltersModel filtersModel3= new FiltersModel(0,true,"Meetups");
        filtersModelList.add(filtersModel3);


        return filtersModelList;
    }
}
