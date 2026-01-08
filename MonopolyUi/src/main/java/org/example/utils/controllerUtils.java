package org.example.utils;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.example.ru.vsu.oop.engine.api.cell.*;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
public class controllerUtils {
    public static final double CELL_WIDTH = 60;
    public static final double CELL_HEIGHT = 60;
    public static final double BOARD_SIZE = CELL_HEIGHT * 11;

    public static Point2D getCellCoordinates(int position) {
        double x = 0, y = 0;

        if (position >= 0 && position <= 10) { // Верх
            x = position * CELL_WIDTH;
            y = 0;
        } else if (position >= 11 && position <= 20) { // Правая
            x = BOARD_SIZE - CELL_WIDTH;
            y = (position - 10) * CELL_HEIGHT;
        } else if (position >= 21 && position <= 30) { // Низ
            x = BOARD_SIZE - (position - 20 + 1) * CELL_WIDTH;
            y = BOARD_SIZE - CELL_HEIGHT;
        } else if (position >= 31 && position < 40) { // Левая
            x = 0;
            y = BOARD_SIZE - (position - 30 + 1) * CELL_HEIGHT;
        }

        return new Point2D(x, y);
    }

    public static Color getColorForCell(Cell cell) {
        if (cell instanceof Street street) {
            return switch (street.getColorGroup()) {
                case BROWN -> Color.web("#8B4513");
                case LIGHT_BLUE -> Color.web("#87CEEB");
                case PINK -> Color.web("#FF69B4");
                case ORANGE -> Color.web("#FFA500");
                case RED -> Color.web("#DC143C");
                case YELLOW -> Color.web("#FFD700");
                case GREEN -> Color.web("#32CD32");
                case DARK_BLUE -> Color.web("#4169E1");
                default -> Color.web("#808080");
            };
        } else if (cell instanceof Railway) {
            return Color.web("#C0C0C0");
        } else if (cell instanceof Utilities) {
            return Color.web("#F0E68C");
        } else if (cell instanceof SpecialCell specialCell) {
            if (specialCell.getCellType() == CellType.CHANCE) {
                return Color.web("#FFFACD");
            } else if (specialCell.getCellType() == CellType.COMMUNITY) {
                return Color.web("#E0FFFF");
            }
        }
        return Color.web("#F5F5F5");
    }
}
