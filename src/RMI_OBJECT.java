
import RMI.ObjectService;
import RMI.TicketSla;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_OBJECT {
    public static void main(String[] args) throws Exception {

        Registry registry = LocateRegistry.getRegistry("36.50.135.242");
        ObjectService service =
                (ObjectService) registry.lookup("RMIObjectService");

        String studentCode = "B23DCCN287";
        String qCode = "wZpKd7p6";

        TicketSla ticket =
                (TicketSla) service.requestObject(studentCode, qCode);

        int threshold = switch (ticket.getPriority()) {
            case "CRITICAL" -> 2;
            case "HIGH" -> 8;
            case "MEDIUM" -> 24;
            case "LOW" -> 72;
            default -> Integer.MAX_VALUE;
        };

        boolean breached =
                ticket.getOpenedHoursAgo() > threshold;

        ticket.setBreached(breached);

        if (!breached) {
            ticket.setAction("MONITOR");
        } else if (ticket.getPriority().equals("CRITICAL")
                || ticket.getOpenedHoursAgo() > 96) {

            ticket.setAction("ESCALATE_L2");

        } else {
            ticket.setAction("ESCALATE_L1");
        }

        service.submitObject(studentCode, qCode, ticket);
    }
}

