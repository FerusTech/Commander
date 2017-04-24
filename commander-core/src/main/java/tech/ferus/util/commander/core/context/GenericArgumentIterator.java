package tech.ferus.util.commander.core.context;

import tech.ferus.util.commander.api.context.Argument;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

public class GenericArgumentIterator implements Iterator<Argument> {

    private final List<Argument> arguments;
    private int current = -1;

    public GenericArgumentIterator(@Nonnull final List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean hasNext() {
        return this.arguments.size() > this.current + 1;
    }

    @Override
    public Argument next() {
        if (!this.hasNext()) {
            return null;
        }

        this.current += 1;
        return this.arguments.get(this.current);
    }

    @Override
    public void remove() {
        this.arguments.remove(this.current);
        this.current -= 1;
    }

    @Override
    public void forEachRemaining(@Nonnull final Consumer<? super Argument> action) {
        while (this.hasNext()) {
            action.accept(this.next());
        }
    }
}
