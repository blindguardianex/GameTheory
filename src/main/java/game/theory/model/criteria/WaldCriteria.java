package game.theory.model.criteria;

import game.theory.model.GameMatrix;
import game.theory.model.GameVector;
import game.theory.model.Pair;
import game.theory.model.criteria.ICriteria;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WaldCriteria implements ICriteria {

    private final GameMatrix gameMatrix;

    public WaldCriteria(GameMatrix gameMatrix) {
        this.gameMatrix = gameMatrix;
    }

    @Override
    public List<GameVector> optimum() {
        List<Pair<GameVector, Double>>mins = gameMatrix.getStrategies().stream()
                .map(a->new Pair<>(a, a.min()))
                .collect(Collectors.toList());
        Pair<GameVector, Double>max = mins.stream().max(Comparator.comparing(Pair::getValue)).get();
        return mins.stream()
                .filter(m->m.getValue()==max.getValue())
                .map(m->m.getKey())
                .collect(Collectors.toList());
    }
}
