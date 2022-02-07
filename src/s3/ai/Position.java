package s3.ai;

import s3.base.S3;
import s3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
        public Pair<Double, Double> pos;
        public Position parent;
        public Double depth;
        public Double heuristic;

        public Position(Pair<Double, Double> pos) {
            this.pos = pos;
            this.parent = null;
            this.heuristic = 0.0;
            this.depth = 0.0;
        }

        public ArrayList<Pair<Double, Double>> getPath() {
            if (this.parent == null)
                return new ArrayList<>();
            ArrayList<Pair<Double, Double>> path = this.parent.getPath();
            path.add(this.pos);
            return path;
        }

        public boolean isOutsideOfGameBorders(S3 game) {
            if (this.pos.m_a < 0 || this.pos.m_b < 0 || this.pos.m_a > game.getMap().getWidth() || this.pos.m_b > game.getMap().getHeight())
                return true;
            return false;
        }

        public List<Position> getChildren() {
            ArrayList<Position> children = new ArrayList<>();
            Pair<Double, Double> currPos = this.pos;
            children.add(new Position(new Pair<>(currPos.m_a + 1, currPos.m_b)));
            children.add(new Position(new Pair<>(currPos.m_a - 1, currPos.m_b)));
            children.add(new Position(new Pair<>(currPos.m_a, currPos.m_b + 1)));
            children.add(new Position(new Pair<>(currPos.m_a, currPos.m_b - 1)));
            return children;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(pos, position.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos);
        }
}
