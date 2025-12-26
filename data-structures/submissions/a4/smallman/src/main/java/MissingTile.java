public class MissingTile {
    static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    static void tilingHelper(int size, int topX, int topY, int paintedX, int paintedY, Grid bd) {
        if (size == 2) {
            if (paintedX == topX & paintedY == topY) {
                bd.setTile(paintedX, paintedY, 3);
            }
            if (paintedX == topX + 1 & paintedY == topY) {
                bd.setTile(paintedX, paintedY, 0);
            }
            if (paintedX == topX & paintedY == topY + 1) {
                bd.setTile(paintedX, paintedY, 2);
            }
            if (paintedX == topX + 1 & paintedY == topY + 1) {
                bd.setTile(paintedX, paintedY, 1);
            }
            return;
        }

        // Yes this is just ((size/2)) but this is for the sake of being as close to the proof as possible
        // I also proved P(k+1) based on P(k) already so based on this
        // can assume that P(k) is based on P(k-1) per induction.
        int newSize = (int) Math.pow(2, log2(size) - 1);
        boolean inTopLeft = paintedX < topX + newSize && paintedY < topY + newSize;
        boolean inTopRight = paintedX >= topX + newSize && paintedY < topY + newSize;
        boolean inBottomLeft = paintedX < topX + newSize && paintedY >= topY + newSize;
        boolean inBottomRight = paintedX >= topX + newSize && paintedY >= topY + newSize;

        if (inTopLeft) {
            bd.setTile(topX + newSize - 1, topY + newSize - 1, 3); // center tile
            tilingHelper(newSize, topX, topY, paintedX, paintedY, bd); // top left
            tilingHelper(newSize, topX + newSize, topY, topX + newSize, topY + newSize - 1, bd); // top right
            tilingHelper(newSize, topX, topY + newSize, topX + newSize - 1, topY + newSize, bd); // bottom left
            tilingHelper(newSize, topX + newSize, topY + newSize, topX + newSize, topY + newSize, bd); // bottom right
        } else if (inTopRight) {
            bd.setTile(topX + newSize, topY + newSize - 1, 0); // center tile
            tilingHelper(newSize, topX, topY, topX + newSize - 1, topY + newSize - 1, bd); // top left
            tilingHelper(newSize, topX + newSize, topY, paintedX, paintedY, bd); // top right
            tilingHelper(newSize, topX, topY + newSize, topX + newSize - 1, topY + newSize, bd); // bottom left
            tilingHelper(newSize, topX + newSize, topY + newSize, topX + newSize, topY + newSize, bd); // bottom right
        } else if (inBottomLeft) {
            bd.setTile(topX + newSize - 1, topY + newSize, 2); // center tile
            tilingHelper(newSize, topX, topY, topX + newSize - 1, topY + newSize - 1, bd); // top left
            tilingHelper(newSize, topX + newSize, topY, topX + newSize, topY + newSize - 1, bd); // top right
            tilingHelper(newSize, topX, topY + newSize, paintedX, paintedY, bd); // bottom left
            tilingHelper(newSize, topX + newSize, topY + newSize, topX + newSize, topY + newSize, bd); // bottom right
        } else if (inBottomRight) {
            bd.setTile(topX + newSize, topY + newSize, 1); // center tile
            tilingHelper(newSize, topX, topY, topX + newSize - 1, topY + newSize - 1, bd); // top left
            tilingHelper(newSize, topX + newSize, topY, topX + newSize, topY + newSize - 1, bd); // top right
            tilingHelper(newSize, topX, topY + newSize, topX + newSize - 1, topY + newSize, bd); // bottom left
            tilingHelper(newSize, topX + newSize, topY + newSize, paintedX, paintedY, bd); // bottom right
        }
    }

    public static void tileGrid(Grid board) {
        tilingHelper(board.size(), 0, 0, board.getPaintedCellX(), board.getPaintedCellY(), board);
    }
}
