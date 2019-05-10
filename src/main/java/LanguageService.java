import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LanguageService {

    public List initData(){
        List data = new ArrayList();
        Languages languages1 = new Languages(1,"Java","Vert.x");
        Languages languages2 = new Languages(2,"Java","Springboot");
        Languages languages3 = new Languages(3,"Javascript","Angular");
        Languages languages4 = new Languages(4,"C++","Kaizal");

        data.add(languages1);
        data.add(languages2);
        data.add(languages3);
        data.add(languages4);

        return data;
    }
}
