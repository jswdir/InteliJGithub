import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LottoGenerator {
    public Set<Integer> generateNumbers() {
        Set<Integer> numbers = new HashSet<>();
        Random random = new Random();

        while (numbers.size() < 6) {
            int number = random.nextInt(45) + 1;
            numbers.add(number);
        }

        return numbers;
    }
}