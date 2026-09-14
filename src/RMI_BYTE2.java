import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class RMI_BYTE2 {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("36.50.135.242");
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData("B23DCCN287","r5EHTDnk");
        Map<Byte,Integer> freq = new HashMap<>();
        for(byte x:data)
            freq.put(x,freq.getOrDefault(x,0)+1);
        byte element = data[0];
        int max=freq.get(element);

        for(byte x:data)
            if(freq.get(x)>max){
                max=freq.get(x);
                element=x;
            }
        byte[] result = {element,(byte)max};
        service.submitData("B23DCCN287","r5EHTDnk",result);

    }
}
