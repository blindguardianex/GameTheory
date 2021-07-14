package game.theory.model.criteria;

import game.theory.model.GameMatrix;
import game.theory.model.GameVector;
import game.theory.model.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

    public class SavageCriteria implements ICriteria{

    private final GameMatrix gameMatrix;

    public SavageCriteria(GameMatrix gameMatrix) {
        this.gameMatrix = gameMatrix;
    }

    @Override
    public List<GameVector> optimum() {
        List<Pair<GameVector, Double>>maxs = gameMatrix.getStrategies().stream()                     //список max значений в векторах
                .map(a->new Pair<>(a, a.max()))
                .collect(Collectors.toList());
        Pair<GameVector, Double>minss = maxs.stream().min(Comparator.comparing(Pair::getValue)).get();   //поиск min вектора среди max-ых
        return maxs.stream()
                .filter(m->m.getValue()==minss.getValue())
                .map(m->m.getKey())
                .collect(Collectors.toList());
    }
}

