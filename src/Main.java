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
                int yandexBotCounter = 0;
                int googlebotCounter = 0;
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

                    //Делим каждую строку на составляющие
                    String[] parts = line.split("\"");

                    //Выделяем часть, которая находится в первых скобках
                    String part = parts[parts.length - 1];
                    String[] firstBrackets = part.split("\\(");

                    //Разделяем эту часть по точке с запятой и очищаем от пробелов
                    String firstBracket = firstBrackets[firstBrackets.length - 1];
                    String[] fragments = firstBracket.split("; ");

                    //Берём второй фрагмент и отделям в этом фрагменте часть до слэша
                    String fragment = fragments[0];
                    if (fragments.length >= 2) {
                        fragment = fragments[1];
                    }
                    String[] resFragments = fragment.split("/");

                    //Подсчитываем количество запросов от ботов
                    String resFragment = resFragments[0];
                    if (resFragment.equals("YandexBot")) {
                        yandexBotCounter++;
                    } else if (resFragment.equals("Googlebot")) {
                        googlebotCounter++;
                    }
                }

                //Выводим данные о запросах
                System.out.println("Общее число запросов к веб-сайту: " + linesAmount);
                System.out.println("Количество запросов от YandexBot: " + yandexBotCounter);
                System.out.println("Количество запросов от Googlebot: " + googlebotCounter);

                //Выводим долю запросов YandexBot и Googlebot относительно общего числа сделанных запросов
                int resYandexBot = yandexBotCounter * 100 / linesAmount;
                System.out.println("Доля запросов от YandexBot: " + resYandexBot + "%");
                int resGooglebot = googlebotCounter * 100 / linesAmount;
                System.out.println("Доля запросов от Googlebot: " + resGooglebot + "%");

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
