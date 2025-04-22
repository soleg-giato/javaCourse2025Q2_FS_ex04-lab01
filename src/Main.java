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
    static int amount = 0;

    public static void main(String[] args) {

        // тестовые данные
        int idx0 = addCategory("Аванс");
        System.out.printf("0 ?= %d", idx0);
        System.out.println();
        int idx1 = addCategory("Продукты");
        System.out.printf("1 ?= %d", idx1);
        System.out.println();
        int idx2 = addCategory("Одежда");
        System.out.printf("2 ?= %d", idx2);
        System.out.println();
        int idx3 = addCategory("Аванс");
        System.out.printf("0 ?= %d", idx3);
        System.out.println();
        int idx4 = addCategory("Аванс");
        System.out.printf("0 ?= %d", idx4);
        System.out.println();

        printAllCats();

    }

    static void printAllCats() {

        System.out.println();
        System.out.println("Индексный массив категорий:");
        for (int i = 0; i < countCats; i++) {
            System.out.printf("%d. %s", i+1, arrCategories[i]);
            System.out.println();
        }
        System.out.println();
    }


    static int addCategory(String categoryName) {

        int recFound = -1;

        // пустой индексный массив категорий
        if (countCats == 0) {
          arrCategories[0] = categoryName;
          recFound = 0;
          countCats++;
        } else { // массив не пустой
            int i = 0;

            // ищем уже имеющуюся категорию
            while (i < countCats || recFound > 0) {
                if (categoryName.equalsIgnoreCase(arrCategories[i])) {
                    recFound = i;
                    return recFound;
                }
                i++;
            }
            // если не нашли, добавляем новую
            arrCategories[countCats] = categoryName;
            recFound = countCats;
            countCats++;
        }

        return recFound;
    }

}
