public record Hailstone(Position3D position, Velocity3D velocity) {

    public IntersectionPoint positionAfterTime(double time) {
        return new IntersectionPoint(
                position.x() + velocity.x() * time,
                position.y() + velocity.y() * time,
                position.z() + velocity.z() * time);
    }
}

record Position3D(long x, long y, long z) {
}

record Velocity3D(long x, long y, long z) {
}

record IntersectionPoint(double x, double y, double z) {
}
