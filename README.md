# Домашнее задание к занятию «Интерфейсы для организации малой связности. Обобщённое программирование (Generics)»
Цель задания
Научиться сортировать объекты.
Научиться сравнивать объекты с помощью Comparable и Comparator.
Инструкция к заданию
Скачайте и установите профессиональный редактор кода Intellij Idea Community Version.
Откройте IDEA, создайте и настройте новый Maven-проект. Под каждую задачу следует создавать отдельный проект, если обратное не сказано в условии.
Создайте пустой репозиторий на GitHub и свяжите его с папкой вашего проекта (не с какой-либо другой папкой).
Правильно настройте репозиторий в плане .gitignore. Проигнорируйте папки .idea и target (раньше была out) и .iml-файл — их в репозитории быть не должно.
Закоммитьте и запушьте созданный проект на GitHub, настройте Github Actions, сделайте git pull.
Выполните в IDEA задачу согласно условию.
Проверьте соблюдение правил форматирования кода.
Убедитесь, что при запуске mvn clean verify (раньше было mvn clean test) все тесты запускаются, проходят, а сборка завершается успешно.
Закоммитьте и отправьте в репозиторий содержимое папки проекта.
Убедитесь, что CI запустился на последнем коммите и завершился успешно (зелёная галочка).
Материалы, которые пригодятся для выполнения задания
Как создать Maven-проект в IDEA.
Как отформатировать код в Java.
Как настроить CI на основе Github Actions.
Задание 1 (обязательное)
Перед вами класс, описывающий билет на самолёт. Точнее, предложение авиакомпании по перелёту:

import java.util.Objects;

public class Ticket {
    private String from; // аэропорт откуда
    private String to; // аэропорт куда
    private int price; // цена
    private int timeFrom; // время вылета (по москве)
    private int timeTo; // время прилёта (по москве)

    public Ticket(String from, String to, int price, int timeFrom, int timeTo) {
        this.from = from;
        this.to = to;
        this.price = price;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getPrice() {
        return price;
    }

    public int getTimeFrom() {
        return timeFrom;
    }

    public int getTimeTo() {
        return timeTo;
    }


    // Вспомогательные методы для корректной работы equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return price == ticket.price && timeFrom == ticket.timeFrom && timeTo == ticket.timeTo && from.equals(ticket.from) && to.equals(ticket.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, price, timeFrom, timeTo);
    }
}
Ниже менеджер, который умеет хранить и искать эти билеты:

public class AviaSouls {
    private Ticket[] tickets = new Ticket[0];

    /**
     * Вспомогательный метод для имитации добавления элемента в массив
     * @param current Массив, в который мы хотим добавить элемент
     * @param ticket Элемент, который мы хотим добавить
     * @return Возвращает новый массив, который выглядит как тот что мы передали,
     * но с добавлением нового элемента в конец
     */
    private Ticket[] addToArray(Ticket[] current, Ticket ticket) {
        Ticket[] tmp = new Ticket[current.length + 1];
        for (int i = 0; i < current.length; i++) {
            tmp[i] = current[i];
        }
        tmp[tmp.length - 1] = ticket;
        return tmp;
    }

    /**
     * Метод добавления билета в менеджер
     * @param ticket Добавляемый билет
     */
    public void add(Ticket ticket) {
        tickets = addToArray(tickets, ticket);
    }

    public Ticket[] findAll() {
        return tickets;
    }

    /**
     * Метод поиска билетов по маршруту
     * @param from Откуда вылетаем
     * @param to Куда прилетаем
     * @return Массив из подходящих билетов
     */
    public Ticket[] search(String from, String to) {
        Ticket[] result = new Ticket[0]; // массив для ответа
        for (Ticket ticket : tickets) { // перебираем все билеты
            if (ticket.getFrom().equals(from)) { // совпадает аэропорт вылета
                if (ticket.getTo().equals(to)) { // совпадает аэропорт прилёта
                    result = addToArray(result, ticket); // добавляем его в массив ответа
                }
            }
        }
        return result;
    }
}
Но оказалось, что пользоваться таким менеджером не очень удобно: он не сортирует ни по цене, ни по времени перелёта. Новую логику предстоит добавить вам.

Сначала сделайте так, что билеты по умолчанию сравниваются по цене. Для этого добавьте implements Comparable<Ticket> { в класс Ticket и реализуйте метод compareTo из интерфейса так, чтобы один билет считался меньше другого, если цена первого меньше цены второго. Протестируйте работу метода compareTo.

Добавьте сортировку массива ответа в методе search, чтобы билеты упорядочивались в порядке возрастания цены. Протестируйте метод поиска.

Создайте класс TicketTimeComparator, который имплементировал бы компаратор для билетов:

import java.util.Comparator;

public class TicketTimeComparator implements Comparator<Ticket> {
    
    @Override
    public int compare(Ticket t1, Ticket t2) {
        // ваш код
    }
}
Компаратор должен сравнивать два билета так, что первый считался бы меньше, чем второй, если его время полёта меньше чем время полёта второго. Протестируйте этот компаратор.

Добавьте в менеджер метод searchAndSortBy(String from, String to, Comparator<Ticket> comparator), который также бы искал билеты, но сортировал их уже по логике переданного параметром компаратора. Протестируйте этот метод, используя созданный выше компаратор.

Все тесты должны быть в тест-классе AviaSoulsTest.
