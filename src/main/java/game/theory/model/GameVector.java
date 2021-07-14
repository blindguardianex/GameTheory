package game.theory.model;

import java.text.DecimalFormat;
import java.util.List;

public class GameVector implements Comparable<GameVector> {  //класс, позволяющий описать строку или столбец игровой матрицы.Поле key будем использовать для идентификации, а также при сравнении, а сравнение понадобится при реализации доминирования.

    private final String name;
    private final List<Double> values;
    private final int key;
    private final DecimalFormat formatter = new DecimalFormat("#0.00");

    public GameVector(String name, List<Double> values, int key) {
        this.name = name;       //ключевое слово this требуется для того, чтобы метод мог сослаться на вызвавший его объект.
        this.values = values;
        this.key = key;
    }

    public double max(){
        return values.stream()
                .mapToDouble(v->v)          //преобразовываем полученные значения в  примитивы double
                .max()
                .getAsDouble();
    }

    public double min(){
        return values.stream()
                .mapToDouble(v->v)
                .min()
                .getAsDouble();
    }


    @Override
    public int compareTo(GameVector other) {        //сравнение
        int compare = 0;
        if (this.key == other.key){
            return compare;
        }
        boolean great = true;
        for (int i=0; i<values.size(); i++){
            great = great && (this.values.get(i)>=other.values.get(i));
        }
        if (great){
            compare = 1;
        } else {
            boolean less = true;
            for (int i=0; i<values.size(); i++){
                less = less && (this.values.get(i)<=other.values.get(i));
            }
            if (less){
                compare = -1;
            }
        }
        return compare;
    }

    public String getName() {
        return name;
    }

    public List<Double> getValues() {
        return values;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return name+": "+values.stream()
                .map(formatter::format)
                .reduce((String f1, String f2)->String.format("%s %s",f1,f2)).get();
    }
}