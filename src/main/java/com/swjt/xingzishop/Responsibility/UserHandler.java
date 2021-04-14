package com.swjt.xingzishop.Responsibility;

import java.lang.reflect.Member;

public abstract class UserHandler<T> {

    protected UserHandler chain;

    public void next(UserHandler userHandler) {
        this.chain = userHandler;
    }

    public abstract void doHandler(Member member);

    public static class Builder<T> {
        private UserHandler<T> head;
        private UserHandler<T> tail;

        public Builder<T> addHandler(UserHandler<T> userHandler) {
            if (this.head == null) {
                this.head = this.tail = userHandler;
                return this;
            }
            this.tail.next(userHandler);
            this.tail = userHandler;

            return this;
        }

        public UserHandler<T> build() {
            return this.head;
        }

    }
}
