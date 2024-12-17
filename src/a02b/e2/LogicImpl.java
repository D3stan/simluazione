package a02b.e2;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private final Map<Point, Boolean> cells = new HashMap<>();
    private boolean disabled = false;

    public LogicImpl(int size) {
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                cells.put(new Point(j, i), false);
            }
        }
    }

    @Override
    public String set(Point key) {
        return cells.compute(key, (point, value) -> value = !value) == true ? "*" : "";
    }

    @Override
    public Map<Point, Boolean> check() {
        final Map<Point, Boolean> toreturn = cells.entrySet().stream()
            .filter(e -> {
                return cells.entrySet().stream()
                .filter(entry -> entry.getValue())
                .filter(entry -> entry.getKey().getX() - e.getKey().getX() == entry.getKey().getY() - e.getKey().getY())
                .count() == 3;
            })
            .filter(e -> {
                Point randomPoint = cells.entrySet().stream().filter(entry -> entry.getValue() == true).findAny().get().getKey();
                
                return randomPoint.getX() - e.getKey().getX() == randomPoint.getY() - e.getKey().getY();
            })
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> disabled == true ? true : false,
                (existing, replacement) -> replacement // handle duplicates
            ));
        disabled = !disabled;
        return toreturn;
    }
}
