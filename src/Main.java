import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int i = 1;
        while (true) {
            //Запрашиваем путь к файлу в консоли
            String path = new Scanner(System.in).nextLine();

            //Определяем существует ли файл и является ли указанный путь путём к файлу, а не к папке
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExists || isDirectory) {
                System.out.println("Указанный файл не существует или указанный путь является путём к папке, а не к файлу");
                continue;
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + i++);

            //Построчно читаем указанный файл
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                int linesAmount = 0;
                int maxLength = 0;
                int minLength = 1024;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) {
                        //Выбрасываем исключение, если в файле встретилась строка длинее 1024 символов
                        throw new RuntimeException("The file contains a line longer than 1024 characters");
                    } else if (length > maxLength) {
                        maxLength = length;
                    } else if (length < minLength) {
                        minLength = length;
                    }
                    linesAmount++;
                }

                //Выводим данные о строках
                System.out.println("Общее количество строк в файле: " + linesAmount);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
