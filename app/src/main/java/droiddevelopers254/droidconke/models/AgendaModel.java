package droiddevelopers254.droidconke.models;

public class AgendaModel {
    private String agendaTitle;
    private String agendaTimeline;
    private int agendaIcon;
    private int agendaBackgroundColor;

    public String getAgendaTitle() {
        return agendaTitle;
    }

    public void setAgendaTitle(String agendaTitle) {
        this.agendaTitle = agendaTitle;
    }

    public String getAgendaTimeline() {
        return agendaTimeline;
    }

    public void setAgendaTimeline(String agendaTimeline) {
        this.agendaTimeline = agendaTimeline;
    }

    public int getAgendaIcon() {
        return agendaIcon;
    }

    public void setAgendaIcon(int agendaIcon) {
        this.agendaIcon = agendaIcon;
    }

    public int getAgendaBackgroundColor() {
        return agendaBackgroundColor;
    }

    public void setAgendaBackgroundColor(int agendaBackgroundColor) {
        this.agendaBackgroundColor = agendaBackgroundColor;
    }

    public AgendaModel(String agendaTitle, String agendaTimeline, int agendaIcon, int agendaBackgroundColor) {
        this.agendaTitle = agendaTitle;
        this.agendaTimeline = agendaTimeline;
        this.agendaIcon = agendaIcon;
        this.agendaBackgroundColor = agendaBackgroundColor;
    }
}
