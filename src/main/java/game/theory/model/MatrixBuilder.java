package game.theory.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MatrixBuilder {

    private List<String> strategyNames = new ArrayList<>();
    private List<String>conditionNames = new ArrayList<>();
    private List<List<Double>>matrix = new ArrayList<>();

    public MatrixBuilder() {
    }

    public GameMatrix fromConsole(){
        //  Получить список названий стратегий
        //  объявляем сканнер
        Scanner scan = new Scanner(System.in);
        //  считываем строку
        System.out.println("Введите стратегии (без пробелов и через запятую)");
        String strategyNameString = scan.nextLine();
        //  разделить на строки, где разделителем будет запятая
        String[] strategyNameArray = strategyNameString.split(",");
        // привести массив в список
        List<String>strategyNames = List.of(strategyNameArray);

        //  Получить список названий условий
        //  считываем строку
        System.out.println("Введите условия (без пробелов и через запятую)");

        String conditionString = scan.nextLine();
        System.out.println("Введите весовые коэффициенты");
        //  разделить на строки, где разделителем будет запятая
        String[] conditionArray = conditionString.split(",");
        // привести массив в список
        List<String>conditionNames = List.of(conditionArray);

        //Oбъявить матрицу
        List<List<Double>>matrix = new ArrayList<>();

        //  Получить значения векторов
        // объявить цикл
        for (int i=0;i<strategyNames.size();i++){
            //  считываем строку
            String strategyValuesString = scan.nextLine();
            //  разделить на строки, где разделителем будет запятая
            String[]  strategyValuesArray  = strategyValuesString.split(",");
            //  проверить введенное значение
            if (strategyValuesArray.length<conditionNames.size()) {
                throw new IllegalArgumentException("Неверное количество значений стратегий");

            }
            // привести массив в список
            List<Double>  strategyValues = new ArrayList<>();
            for (int j=0;j<strategyValuesArray.length;j++){
                strategyValues.add(Double.parseDouble(strategyValuesArray[j]));
            }
            //привести к  Double
            // добавление стратегий в матрицу
            matrix.add(strategyValues);
        }
        return new GameMatrix(matrix,strategyNames,conditionNames);
    }

    public GameMatrix fromFile(String file){
        System.out.println("Создание матрицы из файла: "+file);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int lineNum = 0;
            String line = reader.readLine();
            while (line!=null){
                lineNum++;
                processLine(lineNum,line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createMatrix();
    }

    private void processLine(int lineNum, String line){
        if (isFirstLine(lineNum)){
            setStrategyNames(line);
        }
        else if (isSecondLine(lineNum)){
            setConditionNames(line);
        }
        else {
            addLineToMatrix(line);
        }
    }

    private void setStrategyNames(String line){
        String[]strategyNames = line.split(",");
        this.strategyNames = List.of(strategyNames);
    }

    private void setConditionNames(String line){
        String[]conditionNames = line.split(",");
        this.conditionNames = List.of(conditionNames);
    }

    private void addLineToMatrix(String line){
        String[]matrixLineArray = line.split(",");
        List<Double>matrixLineValues = Arrays.stream(matrixLineArray)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        if (matrixLineValues.size()==conditionNames.size()) {
            matrix.add(matrixLineValues);
        }
        else {
            throw new IllegalArgumentException("Количество значений в строке метрицы не совпадает с количеством указанных условий: "+line);
        }
    }

    private GameMatrix createMatrix(){
        if (matrix.size()==strategyNames.size()){
            System.out.println("Успешно!");
            return new GameMatrix(matrix, strategyNames, conditionNames);
        }
        else {
            throw new IllegalArgumentException("Количество заданных стратегий не совпадает с количеством введенных строк матрицы");
        }
    }

    private static boolean isFirstLine(int lineNum){
        return lineNum==1;
    }

    private static boolean isSecondLine(int lineNum){
        return lineNum==2;
    }

    public GameMatrix getExample(){
        List<String>strategyNames = List.of("Культура 1", "Культура 2", "Культура 3");
        List<String>conditionNames = List.of("Прохладное и дождливое", "Нормальное", "Жаркое и сухое");
        List<List<Double>>matrix = List.of(
                List.of(0.0, 2.0, 5.0),
                List.of(2.0, 3.0, 1.0),
                List.of(4.0, 3.0, -1.0)
        );
        return new GameMatrix(matrix, strategyNames, conditionNames);
    }
}
