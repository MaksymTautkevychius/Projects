package zad2;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class Maybe<T> {

    public final T value;
    public Maybe(T value) {
        this.value = value;
    }
    public static <T> Maybe<T> of(T val) {
        return new Maybe<>(val);
    }
    public void ifPresent(Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }
    public <U> Maybe<U> map(Function<T, U> mapper) {
        return value != null ? Maybe.of(mapper.apply(value)) : Maybe.empty();
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }
    public boolean isPresent() {
        return value != null;
    }
    public T orElse(T defaultValue) {
        return value != null ? value : defaultValue;
    }
    public Maybe<T> filter(Predicate<T> predicate) {
        return value != null && predicate.test(value) ? this : Maybe.empty();
    }
    public static <T> Maybe<T> empty() {
        return new Maybe<>(null);
    }
    @Override
    public String toString() {
        return value != null ? "Maybe[" + value + "]" : "Maybe.empty";
    }
}