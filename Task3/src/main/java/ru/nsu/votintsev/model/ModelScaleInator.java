package ru.nsu.votintsev.model;

public class ModelScaleInator {

    private double scalePercentWidth;
    private double scalePercentHeight;

    public void setScalePercentage(double scalePercentWidth, double scalePercentHeight) {
        this.scalePercentWidth = scalePercentWidth;
        this.scalePercentHeight = scalePercentHeight;
    }

    public int scaleByX(int width) {
        return Math.max((int) (width * scalePercentWidth), 1);
    }

    public int scaleByY(int height) {
        return Math.max((int) (height * scalePercentHeight), 1);
    }

}
