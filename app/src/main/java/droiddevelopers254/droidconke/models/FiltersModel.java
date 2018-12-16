package droiddevelopers254.droidconke.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FiltersModel {
    @PrimaryKey
    private int id;
    private boolean checked;
    private String name;


    public FiltersModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
