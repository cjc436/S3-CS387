package s3.ai;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;


public class AStar {
	private double startX;
	private double startY;
	private double goalX;
	private double goalY;
	private S3PhysicalEntity entity;
	private S3 theGame;
	
	public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) {
		AStar a = new AStar(start_x,start_y,goal_x,goal_y,i_entity,the_game);
		List<Pair<Double, Double>> path = a.computePath();
		if (path!=null) return path.size();
		return -1;
	}

	public AStar(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) {
            startX = start_x;
			startY = start_y;
			goalX = goal_x;
			goalY = goal_y;
			entity = i_entity;
			theGame = the_game;
	}

	public List<Pair<Double, Double>> computePath() {
		class Position {
			public Pair<Double, Double> pos;
			public Position parent;
			//public Double heuristic;

			public Position(Pair<Double, Double> pos) {
				this.pos = pos;
				this.parent = null;
				//this.heuristic = 0.0;
			}

			public ArrayList<Pair<Double, Double>> getPath() {
				if (this.parent == null)
					return new ArrayList<>();
				ArrayList<Pair<Double, Double>> path = this.parent.getPath();
				path.add(this.pos);
				return path;
			}

			public List<Position> getChildren() {
				ArrayList<Position> children = new ArrayList<>();
				Pair<Double, Double> currPos = this.pos;
//				int entityWidth = entity.getWidth();
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

		System.out.println("path request: StartX=" + this.startX + ", StartY=" + this.startY + ", GoalX=" + this.goalX + ", GoalY=" + this.goalY);

		ArrayList<Position> OpenArray = new ArrayList<>();
		//HashSet<Position> OpenSet = new HashSet<>();
		HashSet<Position> ClosedSet = new HashSet<>();
		Position start = new Position(new Pair(startX, startY));
		Position goal = new Position(new Pair(goalX, goalY));
		//OpenSet.add(start);
		OpenArray.add(start);

		while (!OpenArray.isEmpty()) {
			Position temp = OpenArray.remove(0);
			if (temp.equals(goal)) {
				System.out.println("Goal Found!");
				return temp.getPath();
			}
			ClosedSet.add(temp);
			for (Position child : temp.getChildren()) {
				if (!(ClosedSet.contains(child) || OpenArray.contains(child))) {
//					Pair<Double, Double> p = child.pos;
					this.entity.setX(child.pos.m_a.intValue());
					this.entity.setY(child.pos.m_b.intValue());

					if (this.theGame.anyLevelCollision(this.entity) == null) {
//					if (this.theGame.isSpaceFree(p.m_a.intValue(), p.m_b.intValue(), this.entity.getWidth())) {
						child.parent = temp;
						OpenArray.add(child);
						this.entity.setX((int) startX);
						this.entity.setY((int) startY);
						//OpenSet.add(child);
					}
				}
			}
		}
		System.out.println("Not Found!");
		return null;
	}

//	public static void main(String[] args) {
//
//	}

}
