import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random a = new Random(new Date().getTime());
        for (int i = 0; i < 10; i++) {
            Integer cc = i + a.nextInt(100);
            System.out.println(cc);
            list.add(cc);
        }
        System.out.println(new Date(12L));
        list.forEach(integer -> System.out.println(integer));
    }
}
