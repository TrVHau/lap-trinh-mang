import RMI.CharacterService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.URI;

public class RMI_CHARACTER1 {
    public static void main(String[] args) throws Exception{
        Registry registry= LocateRegistry.getRegistry("36.50.135.242");
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String response = service.requestCharacter("B23DCCN287","19s3ARSi");
        System.out.println(response);
        String encoder = new URI(null,null,response,null).toASCIIString();
        System.out.println(encoder);
        service.submitCharacter("B23DCCN287","19s3ARSi",encoder);
    }
}
