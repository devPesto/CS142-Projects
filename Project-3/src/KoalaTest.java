import java.awt.*;

public class KoalaTest {
    public static void main(String[] args) {
        Koala k = new Koala(10);

        /* Directions test
        for (int i = 1; i <= 45; i++) {
            System.out.print(k.getMove().name().charAt(0));
            if (i % 5 == 0)
                System.out.println();
        }
         */

        // Eat test
//        for (int i = 0; i < 12; i++) {
//            System.out.println(k + " " + (k.getColor() == Color.GRAY ? "GRAY" : "WHITE") + " "+ k.fight(""));
//            k.eat();
//        }

        Tiger t = new Tiger();
        System.out.println("Eating 3 times");
        System.out.println(t.eat());
        System.out.println(t.eat());
        System.out.println(t.eat());

        System.out.println("Fight");
        System.out.println(t.eat());
        t.fight("");
        System.out.println(t.eat());

        for (int i = 0; i < Critter.Direction.values().length - 1; i++) {
            System.out.println(Critter.Direction.values()[i]);
        }
    }
}
