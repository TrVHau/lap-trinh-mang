import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class RMI_BYTE1 {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("36.50.135.242",1099);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        // nhan data
        byte[] data = service.requestData("B23DCCN287","vvt1JF78");

        Map<Byte,Integer> freq = new HashMap<>();

        for(byte x:data)
            freq.put(x,freq.getOrDefault(x,0)+1);

        byte element = data[0];
        int min = freq.get(element);

        for(byte x:data)
            if(freq.get(x)<min){
                element=x;
                min=freq.get(element);
            }

        byte[] result = {element,(byte)min};
        service.submitData("B23DCCN287","vvt1JF78",result);
    }
}
