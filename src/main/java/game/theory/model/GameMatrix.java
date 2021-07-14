package game.theory.model;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class GameMatrix {


    private final List<List<Double>> matrix;
    private final List<String> strategyNames;
    private final List<String> conditionNames;

    private final List<GameVector> strategies = new ArrayList<>();  //стратегии
    private final List<GameVector> conditions = new ArrayList<>();  //условия

    public GameMatrix(List<List<Double>> matrix, List<String> strategyNames, List<String> conditionNames) {
        this.matrix = matrix;
        this.strategyNames = strategyNames;
        this.conditionNames = conditionNames;

        for(int i=0; i<matrix.size(); i++){                 //формируем  вектора стратегий
            String currStrategy = strategyNames.get(i);
            GameVector vector = new GameVector(currStrategy, matrix.get(i), i);
            strategies.add(vector);
        }

        for (int j=0; j<matrix.get(0).size(); j++){         //формируем  вектора условий
            String currCondition = conditionNames.get(j);   //текущее значение
            List<Double> states = new ArrayList<>();
            for (int i=0; i<matrix.size(); i++){
                states.add(matrix.get(i).get(j));
            }
            GameVector vector = new GameVector(currCondition, states, j);
            conditions.add(vector);
        }
    }

    public Pair<Integer, Integer>size(){
        return new Pair<>(strategies.size(), conditions.size());
    }


    public List<String> getStrategyNames() {
        return strategyNames;
    }

    public List<String> getConditionNames() {
        return conditionNames;
    }

    public List<GameVector> getStrategies() {
        return strategies;
    }

    public List<GameVector> getConditions() {
        return conditions;
    }

    @Override
    public String toString() {
        return "Возможные условия:\n"+
                conditionNames.stream().reduce((String n1, String n2)->String.format("%s;\n%s",n1, n2)).get()+
                "\nМатрица игры: \n"+
                strategies.stream().map(GameVector::toString)
                        .reduce((String n1,String n2)->String.format("%s;\n%s",n1, n2)).get();
    }
}
