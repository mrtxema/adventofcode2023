public record Position3D(int x, int y, int z) {
    public Position3D moveDown(int steps) {
        return new Position3D(x, y, z - steps);
    }
}
