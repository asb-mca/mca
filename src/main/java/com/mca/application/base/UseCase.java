package com.mca.application.base;

public interface UseCase<I extends UseCaseInput> {
    void execute(I input);
}
