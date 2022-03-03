import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyCyclicBarrier {
    static int x;
    static int y;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Please input x: ");
            x = sc.nextInt();
            System.out.print("Please input y: ");
            y = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Please insert an integer.");
        }
        while (x > y) {
            System.out.println("Y must bigger than X. Please insert again.");
            try {
                System.out.print("Please input x: ");
                x = sc.nextInt();
                System.out.print("Please input y: ");
                y = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Please insert an integer.");
            }
        }

        OddRunnable oddRunnable = new OddRunnable(x, y);
        EvenRunnable evenRunnable = new EvenRunnable(x, y);
        PrimeRunnable primeRunnable = new PrimeRunnable(x, y);


        Runnable r1 = () -> {
            System.out.print("Odd numbers: ");
            for (int i = 0; i < oddRunnable.oddlist.size(); i++) {
                if (i == 0)
                    System.out.print(oddRunnable.oddlist.get(i));
                else
                    System.out.print(", " + oddRunnable.oddlist.get(i));
            }
            System.out.println();
        };
        Runnable r2 = () -> {
            System.out.print("Even numbers: ");
            for (int i = 0; i < evenRunnable.evenlist.size(); i++) {

                if (i == 0)
                    System.out.print(evenRunnable.evenlist.get(i));
                else
                    System.out.print(", " + evenRunnable.evenlist.get(i));
            }
            System.out.println();
        };
        Runnable r3 = () -> {
            System.out.print("Prime  numbers: ");
            for (int i = 0; i < primeRunnable.primelist.size(); i++) {
                if (i == 0)
                    System.out.print(primeRunnable.primelist.get(i));
                else
                    System.out.print(", " + primeRunnable.primelist.get(i));
            }
            System.out.println();
        };
        CyclicBarrier barrier1 = new CyclicBarrier(1, r1);
        CyclicBarrier barrier2 = new CyclicBarrier(1, r2);
        CyclicBarrier barrier3 = new CyclicBarrier(1, r3);

        Main main = new Main(barrier1, barrier2, barrier3, oddRunnable, evenRunnable, primeRunnable);
        new Thread(main).start();
    }
}

class Main implements Runnable {

    OddRunnable oddRunnable;
    EvenRunnable evenRunnable;
    PrimeRunnable primeRunnable;
    CyclicBarrier barrier1, barrier2, barrier3;

    public Main(CyclicBarrier barrier1, CyclicBarrier barrier2, CyclicBarrier barrier3, OddRunnable oddRunnable, EvenRunnable evenRunnable, PrimeRunnable primeRunnable) {
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
        this.barrier3 = barrier3;
        this.oddRunnable = oddRunnable;
        this.evenRunnable = evenRunnable;
        this.primeRunnable = primeRunnable;
    }

    public void run() {
        try {

            oddRunnable.run();
            this.barrier1.await();

            evenRunnable.run();
            this.barrier2.await();

            primeRunnable.run();
            this.barrier3.await();

            System.out.println();

            if ((oddRunnable.oddaverage % 1) == 0) {
                double oddaverage = oddRunnable.oddaverage;
                int value = (int)oddaverage;
                System.out.println("Average value of odd numbers:" + Integer.toBinaryString(value));

            } else {
                System.out.println("Average value of odd numbers:" + oddRunnable.oddaverage);
            }
            if ((evenRunnable.evenaverage % 1) == 0) {
                double evenaverage = evenRunnable.evenaverage;
                int value = (int)evenaverage;
                System.out.println("Average value of odd numbers:" + Integer.toBinaryString(value));
            } else {
                System.out.println("Average value of even numbers:"+evenRunnable.evenaverage);
            }
            if ((primeRunnable.primeaverage % 1) == 0) {
                double primeaverage = primeRunnable.primeaverage;
                int value = (int)primeaverage;
                System.out.println("Average value of odd numbers:" + Integer.toBinaryString(value));
            } else {
                System.out.println("Average value of prime numbers:" + primeRunnable.primeaverage);

            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}


class OddRunnable implements Runnable {
    int x, y, total;
    double oddaverage;

    List<Integer> oddlist = new ArrayList<>();

    public OddRunnable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void run() {
        for (int i = (x + 1); i < y; i++) {
            if (i % 2 == 1) {
                oddlist.add(i);
                total += i;
            }
        }
        int length = oddlist.size();
        oddaverage = (double) total / length;
    }
}

class EvenRunnable implements Runnable {
    int x, y, total;
    List<Integer> evenlist = new ArrayList<>();
    double evenaverage;

    public EvenRunnable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void run() {
        for (int i = (x + 1); i < y; i++) {
            if (i % 2 == 0) {
                evenlist.add(i);
                total += i;
            }
        }
        int length = evenlist.size();
        evenaverage = (double) total / length;
    }

}

class PrimeRunnable implements Runnable {
    int x, y, total;
    List<Integer> primelist = new ArrayList<>();
    double primeaverage;

    public PrimeRunnable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void run() {
        for (int i = (x + 1); i < y; i++) {
            if (checkPrime(i)) {
                primelist.add(i);
                total += i;
            }
        }
        int length = primelist.size();
        primeaverage = (double) total / length;
    }

    private static boolean checkPrime(int x) {
        if (x <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

}