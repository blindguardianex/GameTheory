package game.theory.model.criteria;

import game.theory.model.GameMatrix;
import game.theory.model.GameVector;
import game.theory.model.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PessimismCriteria implements ICriteria {

    private final GameMatrix gameMatrix;

    public PessimismCriteria(GameMatrix gameMatrix) {
        this.gameMatrix = gameMatrix;
    }

    @Override
    public List<GameVector> optimum() {
        List<Pair<GameVector, Double>>mins = gameMatrix.getStrategies().stream()                     //список минимальных значений в векторах
                .map(a->new Pair<>(a, a.min()))
                .collect(Collectors.toList());
        Pair<GameVector, Double>min= mins.stream().min(Comparator.comparing(Pair::getValue)).get();   //поиск максимального вектора среди минимальных
        return mins.stream()
                .filter(m->m.getValue()==min.getValue())
                .map(m->m.getKey())
                .collect(Collectors.toList());
    }
}
