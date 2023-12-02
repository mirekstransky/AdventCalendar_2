import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> listOfQuote = readAllLines("input_day2.txt");
        //List<String> listOfQuote = readAllLines("input_test.txt");

        //  Elf by nejprve rád věděl, jaké hry by byly možné, kdyby sáček obsahoval pouze 12 červených kostek,
        //  13 zelených kostek a 14 modrých kostek ?

        String[] splited;
        int ident = 0;

        int blue = 0;
        int red = 0;
        int green = 0;
        int sumID = 0;
        int soucinSila = 0;
        int tempSoucin = 0;


        boolean prvniPruchod = true;
        int muzemeHrat = 0;


        for (int i = 0; i < listOfQuote.size() ; i++) {

                int najdiDvortecku =  listOfQuote.get(i).indexOf(":")+1;

                String string = listOfQuote.get(i);
                string = string.substring(najdiDvortecku);
                string = string.replaceAll("\\s","");

                splited = string.split(";");
                string = listOfQuote.get(i).substring(0,najdiDvortecku);
                ident = zjistiCislo(string);

                int tempBlue = 0;
                int tempRed = 0;
                int tempGreen=0;

                for (int j = 0; j < splited.length ; j++) {

                    blue = najdiBarvuPocet(splited[j],"blue");
                    red = najdiBarvuPocet(splited[j],"red");
                    green = najdiBarvuPocet(splited[j],"green");

                    if (prvniPruchod){

                        prvniPruchod=false;

                        tempBlue = blue;
                        tempGreen = green;
                        tempRed = red;
                    }
                    if (blue>=tempBlue){
                        tempBlue = blue;
                    }
                    if (red>=tempRed){
                        tempRed = red;
                    }
                    if (green>=tempGreen){
                        tempGreen = green;
                    }

                    if ((red>12) || (green>13) || (blue>14)){
                        muzemeHrat += 1;
                    }
                }
                tempSoucin = (tempRed * tempGreen * tempBlue);
                soucinSila += tempSoucin;
                if (muzemeHrat==0){
                    sumID += ident;
                }
                muzemeHrat = 0;
        }
        System.out.println(soucinSila);
        System.out.println(sumID);
    }

    //##################
    //##### METODY #####
    //##################
    public static int najdiBarvuPocet(String barva,String nazevBarvy){
        int result = 0;
        String[] splited = barva.split(",");

        for (int i = 0; i < splited.length ; i++) {
            if (splited[i].contains(nazevBarvy)){
                result += zjistiCislo(splited[i]);
            }
        }


        return result;
    }

    public static int zjistiCislo(String polozka){

        String result = "";

        for (int i = 0; i < polozka.length(); i++) {
            char charakter = polozka.charAt(i);
            String charStr = String.valueOf(charakter);
            if (isNumeric(charStr)){
                result += charStr;
            }
        }
        return Integer.parseInt(result);
    }

    public static boolean isNumeric(String num) {
        if (num == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // NACTENI VSTUPU
    public static List<String> readAllLines(String resource)throws IOException {
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            return reader.lines().collect(Collectors.toList());
        }
    }
}