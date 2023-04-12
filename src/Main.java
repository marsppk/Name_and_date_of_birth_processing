import javax.swing.*;
import java.time.LocalDateTime;
import java.awt.*;

public class Main {
    public static String make_initials(String first_name, String second_name){
        char first = first_name.charAt(0);
        char second = second_name.charAt(0);
        return first + "." + second + ".";
    }

    public static String define_gender(String surname){
        if (surname.length() < 3){
            return "Incorrect";
        }
        int start = surname.length() - 2;
        int end = surname.length();
        char[] dst = new char[end - start];
        surname.getChars(start, end, dst, 0);
        String end_of_surname = new String(dst);
        if (end_of_surname.equalsIgnoreCase("ич")){
            return "М";
        }
        else if (end_of_surname.equalsIgnoreCase("на")){
            return "Ж";
        }
        else {
            return "Incorrect";
        }
    }
    public static int calculate_age(String date_now, String date_of_birthday){
        String[] date = date_now.split("-");
        String[] birthday = date_of_birthday.split("\\.");
        if (birthday.length != 3){
            return -5;
        }
        switch (Integer.parseInt(birthday[1])) {
            case 2 -> {
                if (Integer.parseInt(birthday[0]) > 29) {
                    return -1;
                }
            }
            case 4, 11, 9, 6 -> {
                if (Integer.parseInt(birthday[0]) > 30) {
                    return -1;
                }
            }
            default -> {
            }
        }
        if (Integer.parseInt(birthday[2]) > Integer.parseInt(date[0])){
            return -2;
        }
        else if (Integer.parseInt(birthday[2]) == Integer.parseInt(date[0]) && (Integer.parseInt(birthday[1]) > Integer.parseInt(date[1]))){
            return -2;
        }
        else if (Integer.parseInt(birthday[2]) == Integer.parseInt(date[0]) && (Integer.parseInt(birthday[1]) == Integer.parseInt(date[1]))  && (Integer.parseInt(birthday[0]) > Integer.parseInt(date[2]))){
            return -2;
        }
        if (!leap_year(Integer.parseInt(birthday[2])) && Integer.parseInt(birthday[0]) == 29 && Integer.parseInt(birthday[1]) == 2){
            return -3;
        }
        if ((Integer.parseInt(birthday[0]) > 31 || 0 > Integer.parseInt(birthday[0])) || (Integer.parseInt(birthday[1]) > 12 || 0 > Integer.parseInt(birthday[1]))){
            return -4;
        }
        if ((Integer.parseInt(birthday[1]) > Integer.parseInt(date[1])) || (Integer.parseInt(birthday[1]) == Integer.parseInt(date[1])) && Integer.parseInt(birthday[0]) > Integer.parseInt(date[2])){
            return Integer.parseInt(date[0]) - Integer.parseInt(birthday[2]) - 1;
        }
        else if ((Integer.parseInt(birthday[1]) < Integer.parseInt(date[1])) || (Integer.parseInt(birthday[1]) == Integer.parseInt(date[1]) && Integer.parseInt(birthday[0]) <= Integer.parseInt(date[2]))){
            return Integer.parseInt(date[0]) - Integer.parseInt(birthday[2]);
        }
        return -5;
    }
    public static boolean leap_year(int year) {
        if (year % 400 == 0) {
            return true;
        }
        else return year % 100 > 1 && year % 4 == 0;
    }
    public static String correct_end(int year){
        if (year % 10 == 1 && year % 100 != 11){
            return "год";
        }
        else if (year % 10 >= 2 && year % 10 <= 4 && (year % 100 < 10 || year % 100 >= 20)){
            return "года";
        }
        else{
            return "лет";
        }
    }
    public static boolean isValidName(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'А' && s.charAt(i) <= 'Я') && !(s.charAt(i) >= 'а' && s.charAt(i) <= 'я')) {
                return false;
            }
        }
        return true;
    }
    public static boolean isValidDate(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(Character.isDigit(s.charAt(i)) ||
                    s.charAt(i) == '.')) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.pack();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(new Dimension(400, 150));
        mainWindow.setVisible(true);
        mainWindow.setResizable(false);
        mainWindow.getResultButton.addActionListener(getResultButton -> {
            String[] input_data = mainWindow.textField1.getText().split(" ");
            String[] date_now = String.valueOf(LocalDateTime.now()).split("T");
            if (input_data.length != 4){
                JOptionPane.showMessageDialog(mainWindow, "Недостаточно данных для вывода ответа!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (!isValidName(input_data[0]) || !isValidName(input_data[1]) || !isValidName(input_data[2])) {
                    JOptionPane.showMessageDialog(mainWindow, "Имя введено некорректно!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else if (!isValidDate(input_data[3])){
                    JOptionPane.showMessageDialog(mainWindow, "Дата введена некорректно!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    int age = calculate_age(date_now[0], input_data[3]);
                    if (age == -1) {
                        JOptionPane.showMessageDialog(mainWindow, "Некорректно введен день месяца!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } else if (age == -2) {
                        JOptionPane.showMessageDialog(mainWindow, "Некорректно введен год рождения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } else if (age == -3) {
                        JOptionPane.showMessageDialog(mainWindow, "Данный год не високосный (в нем нет 29 февраля)!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } else if (age == -4) {
                        JOptionPane.showMessageDialog(mainWindow, "Некорректно введен день или месяц рождения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } else if (age == -5) {
                        JOptionPane.showMessageDialog(mainWindow, "Невозможно посчитать возраст!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String gender = define_gender(input_data[2]);
                        if (gender.equals("Incorrect")) {
                            JOptionPane.showMessageDialog(mainWindow, "Невозможно корректно определить пол!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(mainWindow, "Результат: " + input_data[0] + " " + make_initials(input_data[1], input_data[2]) + " " + gender + " " + age + " " + correct_end(calculate_age(date_now[0], input_data[3])));
                        }
                    }
                }
            }
        });
    }
}
