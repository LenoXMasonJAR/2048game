package game1024;

import java.awt.Color;

public enum ColorL {
    TWO(Color.RED), FOUR(Color.PINK), EIGHT(Color.BLUE), SIXT(new Color(234,56,89)), THRTW(Color.GREEN), SIXF(Color.CYAN), ONETWO(
            Color.ORANGE), TWOFIF(Color.RED), FIVETWEL(Color.BLUE), ONETHOU(Color.YELLOW), TWENTYF(Color.PINK), FOURNIN(Color.GREEN);

    public Color col;

    private ColorL(Color c) {
        col = c;
    }

}
