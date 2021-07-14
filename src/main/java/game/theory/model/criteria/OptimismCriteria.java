package game.theory.model.criteria;

import game.theory.model.GameMatrix;
import game.theory.model.GameVector;
import game.theory.model.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OptimismCriteria implements  ICriteria{

    private final GameMatrix gameMatrix;

    public OptimismCriteria(GameMatrix gameMatrix) {
        this.gameMatrix = gameMatrix;
    }

    @Override
    public List<GameVector> optimum() {
        List<Pair<GameVector, Double>>max = gameMatrix.getStrategies().stream()
                .map(a->new Pair<>(a, a.max()))
                .collect(Collectors.toList());
        Pair<GameVector, Double>supermax = max.stream().max(Comparator.comparing(Pair::getValue)).get();
        return max.stream()
                .filter(m->m.getValue()==supermax.getValue())
                .map(m->m.getKey())
                .collect(Collectors.toList());
    }
}
