package ru.vsu.oop.utils;

import javafx.scene.paint.Color;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.*;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class controllerUtils {

    public static Color getColorForCell(Cell cell) {
        if (cell instanceof Street street) {
            return switch (street.getColorGroup()) {
                case BROWN -> Color.SADDLEBROWN;
                case LIGHT_BLUE -> Color.LIGHTBLUE;
                case PINK -> Color.HOTPINK;
                case ORANGE -> Color.ORANGE;
                case RED -> Color.RED;
                case YELLOW -> Color.YELLOW;
                case GREEN -> Color.GREEN;
                case DARK_BLUE -> Color.DARKBLUE;
                default -> Color.GRAY;
            };
        } else if (cell instanceof Railway) {
            return Color.SILVER;
        } else if (cell instanceof Utilities) {
            return Color.LIGHTGOLDENRODYELLOW;
        } else if (cell instanceof SpecialCell specialCell) {
            if (specialCell.getCellType() == CellType.CHANCE) {
                return Color.LIGHTYELLOW;
            } else if (specialCell.getCellType() == CellType.COMMUNITY) {
                return Color.LIGHTCYAN;
            }
        }
        return Color.LIGHTGRAY;
    }
}
