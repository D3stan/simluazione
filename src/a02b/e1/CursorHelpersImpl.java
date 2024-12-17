package a02b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class CursorHelpersImpl implements CursorHelpers {

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return new Cursor<X>() {
            private final Iterator<X> iterator = list.iterator();
            private X current = iterator.next();

            @Override
            public X getElement() {
                return current;
            }

            @Override
            public boolean advance() {
                if (iterator.hasNext()) {
                    current = iterator.next();
                    return true;
                } 
                return false;
            }
            
        };
    }

    @Override
    public Cursor<Integer> naturals() {
        return new Cursor<Integer>() {

            private final Iterator<Integer> iterator = IntStream.range(0, Integer.MAX_VALUE).iterator();
            private Integer current = iterator.next();

            @Override
            public Integer getElement() {
                return current;
            }

            @Override
            public boolean advance() {
                if (iterator.hasNext()) {
                    current = iterator.next();
                    return true;
                } 
                return false;
                
            }
            
        };
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<X>() {

            private int timesAdvanced = 1;

            @Override
            public X getElement() {
                return input.getElement();
            }

            @Override
            public boolean advance() {
                if (timesAdvanced == max) {
                    return false;
                } else {
                    timesAdvanced++;
                    return input.advance();
                }
            }
            
        };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        do {
            consumer.accept(input.getElement());
        } while (input.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        final List<X> list = new ArrayList<>();
        int timesAdded = 0;
        do {
            list.add(input.getElement());
            timesAdded++;
        } while (input.advance() && timesAdded < max);
        return list;
    }

}
