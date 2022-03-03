import java.util.Scanner;
import java.util.concurrent.*;

public class MyCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String word1, word2, word3;
        Scanner sc = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int count;

        System.out.print("Please enter the 1st string: ");
        word1 = sc.next() + sc.nextLine();
        System.out.print("Please enter the 2st string: ");
        word2 = sc.next() + sc.nextLine();
        System.out.print("Please enter the 3st string: ");
        word3 = sc.next() + sc.nextLine();


        Future<Integer> countValue = executor.submit(new CharactersCount(word1, word2, word3));
        count = countValue.get();
        System.out.println("Total Characters: " + count);
        if (count > 20) {
            System.out.println("More-than-twenty");
        } else {
            System.out.println("Too-short");
        }
        executor.shutdown();
    }

    static class CharactersCount implements Callable<Integer> {
        String word1, word2, word3;

        public CharactersCount(String word1, String word2, String word3) {
            this.word1 = word1;
            this.word2 = word2;
            this.word3 = word3;
        }

        @Override
        public Integer call() {
            return charCount();
        }

        private int charCount() {
            int totalCharacters = 0;
            String word = word1 + word2 + word3;
            word = word.replaceAll("\\s+", "");
            for (int i = 0; i < word.length(); i++) {
                totalCharacters++;
            }
            return totalCharacters;
        }
    }
}
