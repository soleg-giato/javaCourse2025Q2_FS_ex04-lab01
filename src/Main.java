import java.util.Scanner;

public class Main {

    /**
     * author: Shchukin FN
     * date: 2025.04.22
     * task: lesson 04-02 (Lab01-02)
     * Задача выложена в виде картинки
     * Требуется написать финансового помощника
     * для учета движения сумм на счете.
     */

    // массив категорий для ускорения поиска
    static String[] arrCategories = new String[1024];
    // для динамики нужен расширяемый лист
    // это костыль - ограничение массива сверху 1024 элементов
    static int[][] arrBills = new int[1024][2];
    static int countCats = 0;
    static int countBills = 0;
    static int totalAmount = 0;

    public static void main(String[] args) {

        while (Integer.parseInt(validateMenu()[1]) > 0) {
            //
        };
    }

    static void printAllCats() {

        System.out.println();
        System.out.println("Индексный массив категорий:");
        for (int i = 0; i < countCats; i++) {
            System.out.printf("%d. %s", i + 1, arrCategories[i]);
            System.out.println();
        }
        System.out.println();
    }

    static void printAllBills() {

        System.out.println();
        System.out.println("Ваши траты/пополнения:");
        for (int i = 0; i < countBills; i++) {
            System.out.printf("%d. %s %d", i + 1, getCategoryByIdx(arrBills[i][0]), arrBills[i][1]);
            System.out.println();
        }
    }

    static void printBillsByName(String categoryName) {
        int catIdx = findCategory(categoryName);

        if (catIdx > -1) {
            String name = getCategoryByIdx(catIdx);
            for (int i = 0; i < countBills; i++) {
                if (arrBills[i][0] == catIdx) {
                    System.out.printf("%d. %s %d", i, name, arrBills[i][1]);
                    System.out.println();
                }
            }
        } else {
            System.out.printf("Такая категория \"%s\" не существует.", categoryName);
        }
    }

    static void printTotal() {
        System.out.printf("Всего операций %d, итоговая сумма счета: %d.", countBills, totalAmount);
        System.out.println();
    }

    static void printMenu() {
        System.out.println("*******Ваш финансовый помощник*******");
        System.out.println("[1] Добавить трату/пополнение");
        System.out.println("[2] Удалить трату/пополнение");
        System.out.println("[3] Узнать баланс счета");
        System.out.println("[4] Вывести все траты/пополнения");
        System.out.println("[5] Вывести траты по категории");
        System.out.println("[0] Выход");
    }

    static int findCategory(String categoryName) {

        int recFound = -1;

        // пустой индексный массив категорий
        if (countCats == 0) {
            return recFound;
        } else {
            // массив не пустой
            int i = 0;

            // ищем уже имеющуюся категорию
            while (i < countCats) {
                if (categoryName.trim().equalsIgnoreCase(arrCategories[i])) {
                    recFound = i;
                    return recFound;
                }
                i++;
            }
        }

        return recFound;
    }

    static int addCategory(String categoryName) {

        int recFound = findCategory(categoryName);

        // категория новая
        if (recFound == -1) {
            arrCategories[countCats] = categoryName;
            recFound = countCats;
            countCats++;
        }

        return recFound;
    }

    static String getCategoryByIdx(int idx) {
        return arrCategories[idx];
    }

    static int addBill(String bill) {
        String categoryName = bill.trim().split(" ")[0].trim();
        int amount = Integer.parseInt(bill.trim().split(" ")[1]);

        if (amount < 0 && totalAmount < Math.abs(amount)) {
            System.out.printf("Сумма на счете \"%d\" меньше суммы списания \"%d\". Списание не прошло.", totalAmount, amount);
            System.out.println();
            return -1;
        }

        int catId = addCategory(categoryName);
        arrBills[countBills] = new int[]{catId, amount};
        countBills++;
        totalAmount += amount;

        return countBills;
    }

    static int removeBill(int billIdx) {
        if (billIdx < 0 || billIdx > countBills) {
            System.out.println("Указан неверный номер операции для удаления.");
            return 1;
        }

        totalAmount -= arrBills[billIdx - 1][1];
        // дефрагментация заполненного массива
        for (int i = billIdx; i < countBills; i++) {
            arrBills[i - 1] = arrBills[i];
        }
        countBills--;

        return 0;
    }

    static String[] validate(String input) {
        String[] res = {"0", "OK - Входные данные прошли все проверки.", ""};

        if (input.trim().isEmpty())
            res = new String[]{"1", "Укажите не пустую строку", ""};
        else {
            if (input.trim().split(" ")[0].trim().isEmpty())
                res = new String[]{"2", "Укажите не пустое название категории", ""};

            if (input.trim().split(" ").length == 1)
                res = new String[]{"3", "Укажите не пустую сумму по категории", ""};

        }

        return res;
    }

    static int executeMenu(int menuIdx) {
        int res = 1;

        switch (menuIdx) {
            case 0:
                res = 0;
                break;
            case 1:
                String input = getMenuData("Введите операцию: ");
                String[] validRes = validate(input);

                if (validRes[0].equals("0")) {
                    int idxBill = addBill(input);
                    System.out.printf("newId ?= %d", idxBill);
                    System.out.println();
                } else {
                    System.out.printf("Ошибка: %s", validRes[1]);
                    System.out.println();
                }
                break;
            case 2:
                removeBill(Integer.parseInt(getMenuData("Укажите номер операции: ")));
                break;
            case 3:
                printTotal();
                break;
            case 4:
                printAllBills();
                break;
            case 5:
                printBillsByName(getMenuData("Укажите имя категории: "));
                break;
            default:
                validateMenu();
        }

        return res;
    }

    static String getMenuData(String msg) {
        System.out.print(msg);
        Scanner scr = new Scanner(System.in);

        return scr.nextLine();
    }

    static String[] validateMenu() {

        String input = "";
        int validNum = 1;

        // в цикле добиваемся ввода валидных входных данных
        while (validNum > 0) {

            // читаем входные данные
            printMenu();
            input = getMenuData("Ваш выбор: ");
            System.out.printf("Пришли такие данные: \"%s\"", input);
            System.out.println();

            // валидируем входные данные
            if (input.trim().isEmpty()) {
                System.out.println("Пункт меню не выбран. Введите корректный номер...");
                System.out.println();
            } else {

                int res = Integer.parseInt(input.trim());
                if (res >= 0 && res <= 5) {
                    validNum = 0;
                    executeMenu(res);
                    return new String[]{"0", String.valueOf(res)};
                } else System.out.println("Выберите номер меню в диапазоне [0..5].");
            }
        }

        return new String[]{"0", "0"};
    }

}
