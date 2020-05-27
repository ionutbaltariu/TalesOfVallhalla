package PaooGame.Tiles;

public class TileFactory {

    public Tile createTile(int id)
    {
        return switch (id) {
            case 0 -> new GrassTile(0);
            case 1 -> new RockTile(1);
            case 2 -> new WaterTile(2);
            case 3 -> new SoilTile(3);
            case 4 -> new LavaTile(4);
            default -> null;
        };
    }

}
