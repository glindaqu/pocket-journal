package com.linkqw.diary.shared;

public class DateConvert {
    public static String ConvertToHuman(String date) {
        String[] splited = date.split("-");

        switch(splited[1]) {
            case "01": return "Январь, " + splited[2];
            case "02": return "Февраль, " + splited[2];
            case "03": return "Март, " + splited[2];
            case "04": return "Апрель, " + splited[2];
            case "05": return "Май, " + splited[2];
            case "06": return "Июнь, " + splited[2];
            case "07": return "Июль, " + splited[2];
            case "08": return "Август, " + splited[2];
            case "09": return "Сентябрь, " + splited[2];
            case "10": return "Октябрь, " + splited[2];
            case "11": return "Ноябрь, " + splited[2];
            default: return "Декабрь, " + splited[2];
        }
    }
}
