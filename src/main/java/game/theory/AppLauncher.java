package game.theory;

import game.theory.model.*;
import game.theory.model.criteria.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppLauncher {

    private static GameMatrix matrix;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        LaunchType type = getLaunchType();
        initMatrix(type);
        List<CriteriaType>criteriaTypes=getCriteriaType();
        startTesting(matrix, criteriaTypes);
    }

    private static void initMatrix(LaunchType type){
        MatrixBuilder matrixBuilder = new MatrixBuilder();
        switch (type){
            case FILE: {
                String file = getFilePathFromConsole();
                matrix = matrixBuilder.fromFile(file);
                break;
            }
            case CONSOLE: {
                matrix = matrixBuilder.fromConsole();
                break;
            }
            case EXAMPLE: {
                matrix = matrixBuilder.getExample();
                break;
            }
            default: {
                break;
            }
        }
    }

    private static LaunchType getLaunchType(){
        System.out.println("Введите тип формирования матрицы (\"файл\", \"консоль\" или \"пример\"): ");
        String type = scanner.nextLine();
        return LaunchType.getByType(type);
    }

    private static String getFilePathFromConsole(){
        System.out.println("Введите путь к файлу: ");
        String file = scanner.nextLine();
        return file;
    }

    private static List<CriteriaType> getCriteriaType(){
        System.out.println("Введите рассматриваемые критерии (критерий Вальда, критерий пессимизма, критерий оптимизма, критерий Саваджа, все): ");
        String criteriaLine = scanner.nextLine();
        if (criteriaLine.equalsIgnoreCase("все")){
            return List.of(CriteriaType.values());
        }
        String[]criteriaString = criteriaLine.split(",");
        List<CriteriaType>criteria = Arrays.stream(criteriaString)
                                    .map(CriteriaType::getByName)
                                    .collect(Collectors.toList());
        return criteria;
    }

    private static void startTesting(GameMatrix matrix, List<CriteriaType>criteriaTypes){
        System.out.println(matrix.toString());
        for (CriteriaType criteriaType:criteriaTypes){
            ICriteria criteria = criteriaType.getCriteria(matrix);
            List<GameVector> optimum = criteria.optimum();
            System.out.println(criteriaType.getCriteriaName()+". Оптимальная стратегия: ");
            optimum.forEach(System.out::println);
        }
    }

    private enum LaunchType{
        FILE("Файл"),
        CONSOLE("Консоль"),
        EXAMPLE("Пример");

        private final String type;

        LaunchType(String type) {
            this.type = type;
        }

        public static LaunchType getByType(String type){
            return Arrays.stream(LaunchType.values())
                    .filter(t->t.type.equalsIgnoreCase(type))
                    .findAny()
                    .orElseThrow(()->{
                        throw new IllegalArgumentException("Неверно указан тип формирования матрицы");
                    });
        }
    }

    private enum CriteriaType{
        WALD("Критерий Вальда", WaldCriteria::new),
        OPTIMISM("Критерий оптимизма", OptimismCriteria::new),
        PESSIMISM("Критерий пессимизма", PessimismCriteria::new),
        SAVAGE("Критерий Саваджа", SavageCriteria::new);

        private final String criteriaName;
        private final Function<GameMatrix, ICriteria> relatedCriteria;

        CriteriaType(String criteriaName,Function<GameMatrix, ICriteria> relatedCriteria) {
            this.criteriaName = criteriaName;
            this.relatedCriteria = relatedCriteria;
        }

        public ICriteria getCriteria(GameMatrix matrix){
            return relatedCriteria.apply(matrix);
        }

        public String getCriteriaName(){
            return criteriaName;
        }

        public static CriteriaType getByName(String criteria){
            return Arrays.stream(CriteriaType.values())
                    .filter(c->c.criteriaName.equalsIgnoreCase(criteria))
                    .findAny()
                    .orElseThrow(()->{
                        throw new IllegalArgumentException("Неверно указан тип формирования матрицы");
                    });
        }
    }
}
