package droiddevelopers254.droidconke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FiltersModel {
    @PrimaryKey
    private int id;
    private boolean checked;
    private String name;

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

    public FiltersModel(int id, boolean checked, String name) {
        this.id = id;
        this.checked = checked;
        this.name = name;
    }
}
