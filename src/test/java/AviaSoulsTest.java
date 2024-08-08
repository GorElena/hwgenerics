import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class AviaSoulsTest {

    @Test
    public void sortByPricePositive() {
        Ticket ticket1 = new Ticket("Москва", "Сочи", 13_000, 21, 23);
        Ticket ticket2 = new Ticket("Москва", "Ростов", 6_000, 15, 17);

        int expect = 1;
        int actual = ticket1.compareTo(ticket2);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void sortByPriceNegative() {
        Ticket ticket1 = new Ticket("Москва", "Сочи", 13_000, 21, 23);
        Ticket ticket2 = new Ticket("Москва", "Ростов", 6_000, 15, 17);

        int expect = -1;
        int actual = ticket2.compareTo(ticket1);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void sortByPriceEqual() {
        Ticket ticket3 = new Ticket("Москва", "Нижний Новгород", 3_000, 16, 17);

        int expect = 0;
        int actual = ticket3.compareTo(ticket3);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void sortByPriceFromManager() {
        Ticket ticket1 = new Ticket("Москва", "Сочи", 13_000, 21, 23);
        Ticket ticket6 = new Ticket("Москва", "Сочи", 6_000, 21, 23);

        AviaSouls manager = new AviaSouls();
        manager.add(ticket1);
        manager.add(ticket6);
        Ticket[] expect = {ticket6, ticket1};
        Ticket[] actual = manager.search("Москва", "Сочи");
        Assertions.assertArrayEquals(expect, actual);
    }

    @Test
    public void sortByFlyingTimeUsingComparator() {
        Ticket ticket1 = new Ticket("Москва", "Сочи", 13_000, 15, 20);
        Ticket ticket2 = new Ticket("Москва", "Рщстов", 6_000, 15, 17);
        Ticket ticket3 = new Ticket("Москва", "Нижний Новгород", 3_000, 15, 18);

        TicketTimeComparator timeComparator = new TicketTimeComparator();
        Ticket[] tickets = {ticket1, ticket2, ticket3};
        Arrays.sort(tickets, timeComparator);

        Ticket[] expect = {ticket2, ticket3, ticket1};

        Assertions.assertArrayEquals(expect, tickets);
    }

    @Test
    public void sortByFlyingTimeUsingManager() {
        Ticket ticket1 = new Ticket("Москва", "Сочи", 13_000, 15, 20);
        Ticket ticket2 = new Ticket("Москва", "Сочи", 6_000, 15, 17);
        Ticket ticket3 = new Ticket("Москва", "Сочи", 3_000, 15, 18);
        Ticket ticket4 = new Ticket("Москва", "Сочи", 4_000, 15, 18);
        Ticket ticket5 = new Ticket("Москва", "Нижний Новгород", 13_000, 21, 23);
        Ticket ticket6 = new Ticket("Нижний Новгород", "Москва", 6_000, 15, 17);

        AviaSouls manager = new AviaSouls();
        TicketTimeComparator timeComparator = new TicketTimeComparator();
        manager.add(ticket1);
        manager.add(ticket2);
        manager.add(ticket3);
        manager.add(ticket4);
        manager.add(ticket5);
        manager.add(ticket6);
        Ticket[] expect = {ticket2, ticket3, ticket4, ticket1};
        Ticket[] actual = manager.searchAndSortBy("Москва", "Сочи", timeComparator);
        Assertions.assertArrayEquals(expect, actual);
    }
}