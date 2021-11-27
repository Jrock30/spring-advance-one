package com.jrock.springadvanceone.trace.callback;

public interface TraceCallback<T> {
    T call();
}
