package ru.nsu.votintsev.factory.supplier;

public abstract class BasicSupplier implements Supplier {

    protected int speed;
    protected long lastTime;

    @Override
    public void changeSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
}
