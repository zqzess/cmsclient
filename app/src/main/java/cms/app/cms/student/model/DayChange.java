package cms.app.cms.student.model;

/**
 * @author ZQZESS
 * @date 2020/6/17-18:43
 * GitHub：
 * email：
 * description：
 */
public class DayChange {
    String[] dayarr = {"第1", "第2", "第3", "第4", "第5", "第6", "第7", "第8", "第9", "第10"};
    String[] weekarr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    String[] Weekarrtmp = {"一", "二", "三", "四", "五", "六", "日"};

    public String daychange(int length, String[] arr) {
        //数字日期转日期
        String time = "";
        if (length == 6) {
            String weekday = arr[0];
            String weekday2 = weekChange(weekday);
            time = weekday2 + arr[1] + "-" + arr[2];
            weekday = arr[3];
            weekday2 = weekChange(weekday);
            time = time + "," + weekday2 + arr[4] + "-" + arr[5];

        } else if (length == 3) {
            String weekday = arr[0];
            String weekday2 = weekChange(weekday);
            time = weekday2 + arr[1] + "-" + arr[2];
        } else if (length == 9) {
            String weekday = arr[0];
            String weekday2 = weekChange(weekday);
            time = weekday2 + arr[1] + "-" + arr[2];
            weekday = arr[3];
            weekday2 = weekChange(weekday);
            time = time + "," + weekday2 + arr[4] + "-" + arr[5];
            weekday = arr[6];
            weekday2 = weekChange(weekday);
            time = time + "," + weekday2 + arr[7] + "-" + arr[8];
        }


        return time;
    }

    public String weekChange(String arr) {
        String weekday = arr;
        String weekday2 = "";
        if (weekday.equals("1")) {
            weekday2 = "周一";
        } else if (weekday.equals("2")) {
            weekday2 = "周二";
        } else if (weekday.equals("3")) {
            weekday2 = "周三";
        } else if (weekday.equals("4")) {
            weekday2 = "周四";
        } else if (weekday.equals("5")) {
            weekday2 = "周五";
        } else if (weekday.equals("6")) {
            weekday2 = "周六";
        } else if (weekday.equals("7")) {
            weekday2 = "周日";
        }
        return weekday2;
    }

    public int dayBackChange(String arr) {
        int daytmp = 0;
        int length = dayarr.length;
        for (int i = 0; i < length; i++) {
            if (dayarr[i].equals(arr)) {
                daytmp = i + 1;
            }
        }
        return daytmp;
    }

    public int weekBackChange(String arr) {
        int weektmp = 0;
        for (int i = 0; i < Weekarrtmp.length; i++) {
            if (Weekarrtmp[i].equals(arr)) {
                weektmp = i + 1;
            }
        }
        return weektmp;
    }

    public String timeChange(String arr) {//日期转为数字日期
        String time = "";
        if (arr.length() == 5) {
            String lessonto = arr.substring(arr.indexOf('-') + 1);
            String tmp = arr.substring(arr.indexOf("周") + 1, arr.indexOf("-"));
            String lessfrom = tmp.substring(tmp.length() - 1);
            String weekTmp = tmp.substring(0, 1);
            int week = weekBackChange(weekTmp);
            time = week + "," + lessfrom + "," + lessonto;
        } else {
            String[] arrString = arr.split(",");
            for (int i = 0; i < arrString.length; i++) {
                String lessonto = arrString[i].substring(arrString[i].indexOf('-') + 1);
                String tmp = arrString[i].substring(arrString[i].indexOf("周") + 1, arrString[i].indexOf("-"));
                String lessfrom = tmp.substring(tmp.length() - 1);
                String weekTmp = tmp.substring(0, 1);
                int week = weekBackChange(weekTmp);
                time = time + week + "," + lessfrom + "," + lessonto + ",";
            }
            time = time.substring(0, time.length() - 1);
        }


        return time;
    }

    public String timeCheckChange(String arr) {//日期转为数字日期
        String time = "";
        if (arr.length() == 5) {
            String lessonto = arr.substring(arr.indexOf('-') + 1);
            String tmp = arr.substring(arr.indexOf("周") + 1, arr.indexOf("-"));
            String lessfrom = tmp.substring(tmp.length() - 1);
            String weekTmp = tmp.substring(0, 1);
            int week = weekBackChange(weekTmp);
            time = week + "," + lessfrom + "," + lessonto;
        } else {
            String[] arrString = arr.split(",");
            for (int i = 0; i < arrString.length; i++) {
                String lessonto = arrString[i].substring(arrString[i].indexOf('-') + 1);
                String tmp = arrString[i].substring(arrString[i].indexOf("周") + 1, arrString[i].indexOf("-"));
                String lessfrom = tmp.substring(tmp.length() - 1);
                String weekTmp = tmp.substring(0, 1);
                int week = weekBackChange(weekTmp);
                time = time + week + "," + lessfrom + "," + lessonto + "-";
            }
            time = time.substring(0, time.length() - 1);
        }


        return time;
    }

}
