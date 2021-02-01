package zhuj.function;

public interface FunctionIO<Input, Output> {
    Output apply(Input input);
}
