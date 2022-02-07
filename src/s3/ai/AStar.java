package s3.ai;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import s3.base.S3;
import s3.base.S3App;
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
		// A* with Heuristic Version
		ArrayList<Position> OpenArray = new ArrayList<>();
		HashSet<Position> ClosedSet = new HashSet<>();
		Position start = new Position(new Pair(startX, startY));
		Position goal = new Position(new Pair(goalX, goalY));
		start.heuristic = this.getHeuristic(start);
		OpenArray.add(start);

		while (!OpenArray.isEmpty()) {
			Position temp = OpenArray.remove(0);
			if (temp.equals(goal)) {
				return temp.getPath();
			}
			ClosedSet.add(temp);
			for (Position child : temp.getChildren()) {
				if (!(ClosedSet.contains(child) || OpenArray.contains(child))) {
					this.entity.setX(child.pos.m_a.intValue());
					this.entity.setY(child.pos.m_b.intValue());

					if (!child.isOutsideOfGameBorders(this.theGame) && this.theGame.anyLevelCollision(this.entity) == null) {
						child.parent = temp;
						child.heuristic = this.getHeuristic(child);
						child.depth = temp.depth + 1;
						OpenArray = insertByHeuristic(OpenArray,child);
					}
					this.entity.setX((int) startX);
					this.entity.setY((int) startY);
				}
			}
		}
		return null;

		// Breadth First Search Version
//		ArrayList<Position> OpenArray = new ArrayList<>();
//		HashSet<Position> ClosedSet = new HashSet<>();
//		Position start = new Position(new Pair(startX, startY));
//		Position goal = new Position(new Pair(goalX, goalY));
//		OpenArray.add(start);
//
//		while (!OpenArray.isEmpty()) {
//			Position temp = OpenArray.remove(0);
//			if (temp.equals(goal)) {
//				return temp.getPath();
//			}
//			ClosedSet.add(temp);
//			for (Position child : temp.getChildren()) {
//				if (!(ClosedSet.contains(child) || OpenArray.contains(child))) {
//					this.entity.setX(child.pos.m_a.intValue());
//					this.entity.setY(child.pos.m_b.intValue());
//
//					if (!child.isOutsideOfGameBorders(this.theGame) && this.theGame.anyLevelCollision(this.entity) == null) {
//						child.parent = temp;
//						OpenArray.add(child);
//					}
//					this.entity.setX((int) startX);
//					this.entity.setY((int) startY);
//				}
//			}
//		}
//		return null;
	}

	private Double getHeuristic(Position position) {
		return Math.abs(position.pos.m_a - this.goalX) + Math.abs(position.pos.m_b - this.goalY);
	}

	private ArrayList<Position> insertByHeuristic(ArrayList<Position> positions, Position position) {
		int i = positions.size() - 1;
		if (i == -1) {
			positions.add(position);
			return positions;
		}
		Position currentP = positions.get(i);
		while (i > -1 && currentP.heuristic + currentP.depth > position.heuristic + position.depth) {
			i -= 1;
			if (i > -1)
				currentP = positions.get(i);
		}
		positions.add(i+1,position);
		return positions;
	}

}
