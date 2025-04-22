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

        // тестовые данные
//        int idx0 = addCategory("Аванс");
//        System.out.printf("0 ?= %d", idx0);
//        System.out.println();
//        int idx1 = addCategory("Продукты");
//        System.out.printf("1 ?= %d", idx1);
//        System.out.println();
//        int idx2 = addCategory("Одежда");
//        System.out.printf("2 ?= %d", idx2);
//        System.out.println();
//        int idx3 = addCategory("Аванс");
//        System.out.printf("0 ?= %d", idx3);
//        System.out.println();
//        int idx4 = addCategory("Аванс");
//        System.out.printf("0 ?= %d", idx4);
//        System.out.println();


        int idxBill0 = addBill("Аванс 56000");
        System.out.printf("0 ?= %d", idxBill0);
        System.out.println();
        int idxBill1 = addBill("Продукты -37000");
        System.out.printf("1 ?= %d", idxBill1);
        System.out.println();
        int idxBill2 = addBill("Аванс 128000");
        System.out.printf("2 ?= %d", idxBill2);
        System.out.println();

        printAllBills();

        printAllCats();

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
        System.out.println("Ваши траты/пополнение:");
        for (int i = 0; i < countBills; i++) {
            System.out.printf("%d. %s %d", i + 1, getCategoryByIdx(arrBills[i][0]), arrBills[i][1]);
            System.out.println();
        }
        System.out.printf("Всего операций %d, итоговая сумма счета: %d.", countBills, totalAmount);
        System.out.println();

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
                if (categoryName.equalsIgnoreCase(arrCategories[i])) {
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
        // validate();
        String categoryName = bill.trim().split(" ")[0].trim();
        int amount = Integer.parseInt(bill.trim().split(" ")[1]);

        if (amount < 0 && totalAmount < Math.abs(amount)) {
            System.out.printf("Сумма на счете \"%d\" меньше суммы списания \"%d\". Списание не прошло.", totalAmount, amount);
            System.out.println();
            return -1;
        }

        int catId = addCategory(categoryName);
        arrBills[countBills] = new int[] {catId, amount};
        countBills++;
        totalAmount += amount;

        return countBills;
    }

}
