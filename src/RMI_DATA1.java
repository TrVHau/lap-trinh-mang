
// hiện đang lỗi

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMI_DATA1 {
    public static void main(String[] args) throws Exception {

        Registry registry =
                LocateRegistry.getRegistry("36.50.135.242");

        DataService service =
                (DataService) registry.lookup("RMIDataService");

        String studentCode = "B23DCCN287";
        String qCode = "4hSKlmjV";

        Integer[] res = service.requestData(studentCode, qCode);

        List<Integer> result = new ArrayList<>();

        if (res[0] > res[1])
            result.add(1);

        for (int i = 1; i < res.length - 1; i++) {
            if (res[i] > res[i - 1] && res[i] > res[i + 1])
                result.add(i + 1);
        }

        if (res[res.length - 1] > res[res.length - 2])
            result.add(res.length);

        service.submitData(studentCode, qCode, result);
    }
}