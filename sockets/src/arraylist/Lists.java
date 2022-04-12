package arraylist;

import java.util.ArrayList;
import java.util.List;

public class Lists {
    public static void main(String[] args) {
        List<String> words = new ArrayList<String>();

        words.add("Batata");
        words.add("Salame");
        words.add("Arroz");

        System.out.println(words.get(0)+ "\n" );

        words.remove(0);

        words.forEach((word) -> System.out.println(word));
    }
}
