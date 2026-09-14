package RMI;

import java.io.Serializable;

public class TicketSla implements Serializable {
    private static final long serialVersionUID = 20260517L;

    private String ticketId;
    private String priority;      // LOW, MEDIUM, HIGH, CRITICAL
    private int openedHoursAgo;
    private boolean breached;
    private String action;

    public TicketSla() { }
    public TicketSla(String ticketId, String priority, int openedHoursAgo) {
        this.ticketId=ticketId;
        this.priority=priority;
        this.openedHoursAgo= openedHoursAgo;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getOpenedHoursAgo() {
        return openedHoursAgo;
    }

    public void setOpenedHoursAgo(int openedHoursAgo) {
        this.openedHoursAgo = openedHoursAgo;
    }

    public boolean isBreached() {
        return breached;
    }

    public void setBreached(boolean breached) {
        this.breached = breached;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}