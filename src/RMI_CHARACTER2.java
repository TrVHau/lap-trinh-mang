import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_CHARACTER2 {
    public static void main(String[] args)throws Exception {
        Registry registry = LocateRegistry.getRegistry("36.50.135.242");
        CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

        String response = service.requestCharacter("B23DCCN287","Keh1cJz0");
        int n= Integer.parseInt(response);
        System.out.println(n);
        int[] value={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] symbols={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< value.length;i++){
            while(n>=value[i]){
                n-=value[i];
                sb.append(symbols[i]);
            }
        }
        System.out.println(sb.toString());
        service.submitCharacter("B23DCCN287","Keh1cJz0",sb.toString());
    }
}
