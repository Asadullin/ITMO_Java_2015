package laba;

import java.io.*;
import java.util.*;


/**
 * Created by INDIGO-?? on 30.09.2015.
 */
public class laba_1 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();

       // BufferedReader reader = new BufferedReader(
       // new File(args[0]);
        File dir = new File("D:\\lab1\\Product");
        try {
            File inputFile2 = new File("D:\\lab1\\color.txt");
            list2 = parsing1(Fileload(inputFile2));
            for (File file : dir.listFiles()) {
                System.out.println( file.getCanonicalPath());
                File inputFile1 = new File(file.getCanonicalPath());
                //Парсим файлы product
                list = parsing1(Fileload(inputFile1));
                //объеденяем все файлы ы один массив
                //product
                for (int i =0; i< list.size();i++)
                {
                    list3.add(list.get(i));
                }
            }
        }catch (Exception e ){System.out.println("Некорректный путь до color.txt");}

        if (list2.size()==0) {System.out.println("Color.txt все строки не корректны");}
            //color
        //Файлы в папке


        if (list.size()==0) {System.out.println("Пустая папка с ассортиментом или все файлы в папке полностью битые"); } else {

            //System.out.println(list);
            //System.out.println(list2);

/// ???????? ??? ????????? ?? ?????? ????????
            ArrayList<String> sort = new ArrayList<>();
            ArrayList<String> sort3 = new ArrayList<>();
            ArrayList<Integer> sort2 = new ArrayList<>();
            ////
            ArrayList<Double> sort4 = new ArrayList<>();
            ArrayList<Integer> again = new ArrayList<>();
            //сортируем числа и названия
            for (int i = 0; i < list3.size(); i++) {
                if (i % 2 == 0) {
                    sort.add(list3.get(i));
                    sort3.add(list3.get(i));
                } else {
                    sort2.add(Integer.parseInt(list3.get(i)));
                }
            }
            //????? ????????????????????? ????? ??????????? ????????? sort

            ///уникальные названия
            //??????? ?????????? ?? ???? ?????? product
            for (int n = 0; n < sort3.size(); n++) {
                for (int m = 0; m < sort3.size(); m++) {
                    if (sort3.get(n).equals(sort3.get(m)) && (n != m)) {
                        sort3.remove(m);
                    }
                }
            }
            ///////////////////////////////////////////////////////////////

            //находим повторяющиеся элементы и записываем их id в массив
            for (int i = 0; i < sort3.size(); i++) {
                again.clear();
                // ?????????? ??????? ? ??????????? ?????????? ? ??????
                for (int j = 0; j < sort.size(); j++) {
                    if (sort3.get(i).equals(sort.get(j))) {
                        again.add(j);
                    }
                }
                ///ищем среднее
                double sum = 0;

                for (int m = 0; m < again.size(); m++) {
                    sum = sum + sort2.get(again.get(m));
                }
                sort4.add(sum / again.size());

                //System.out.println(again);
            }

            TreeMap<String, Double> result = new TreeMap<>();
            for (int i = 0; i < sort3.size(); i++) {
                if (list2.size()==0){result.put(sort3.get(i), sort4.get(i));}
                else {
                    for (int j = 0; j < list2.size(); j++) {
                        if (j % 2 == 0) {
                            result.put(sort3.get(i) + "_" + list2.get(j), (sort4.get(i) + Integer.parseInt(list2.get(j + 1))));
                        }
                    }
                }
            }


          //  System.out.println(sort3);
           // System.out.println(sort4);
// записываем в файл результат
            try (FileWriter writer = new FileWriter("D:\\lab1\\result.txt", false)) {
                for (Map.Entry e : result.entrySet()) {
                    writer.write(String.valueOf(e) + System.getProperty("line.separator"));
                }
                writer.append('\n');
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Результаты записаны в result.txt!!!!!!!");
        }
    }


    public static ArrayList<String> parsing1(ArrayList<String> data1) throws IOException
    {
        ArrayList<String> list = new ArrayList<>();
        for (int n = 0; n < data1.size(); n++) {
            StringTokenizer st = new StringTokenizer(data1.get(n), " ,.!?\\\n\r");
            for (int i = 0; st.hasMoreTokens(); i++) {
                boolean m = false;
                String a = st.nextToken();
                String b = st.nextToken();
                //проверяем на уникальность и порядок
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).equals(a)) {
                        m = true;
                    }
                }
                if (!m) {
                    try {
                        Integer.parseInt(a);
                    } catch (Exception e) {
                        m = true;
                    }
                    if (m) {
                        list.add(a);
                        list.add(b);
                    }
                }
            }
        }

        return list;
    }

    public static ArrayList<String> Fileload(File inputFile) throws IOException
    {
        // загружаем файлы

        FileReader fileReader = new FileReader(inputFile);
        BufferedReader bf = new BufferedReader(fileReader);

        char[] chars = new char[(int)inputFile.length()];
        String s = null;
        if(fileReader.ready())
        {
            fileReader.read(chars);
            s = new String(chars);
          // fileReader.close();
           // System.out.println(s);
        }

        int count=0;
        String data = String.valueOf(chars);
        //если в строке количество слов !=2 тогда пропускаем
        ArrayList<String> data1 = new ArrayList<>();
        String[] tmp = data.split("\\n");
        for (int i = 0; i < tmp.length; i++)
        {
            String a = null;
            a = tmp[i];
            StringTokenizer aa = new StringTokenizer(a," ,.!?\\\n\r");
            if (aa.countTokens()==2){
               data1.add(a);
            }
        }
        System.out.println("Файл загружен");
        return data1;
    }
}
