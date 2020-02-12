package dad.javafx.bomberdad;

import dad.javafx.bomberdad.components.EnemyComponent;
import dad.javafx.bomberdad.components.PlayerComponent;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;

public class IATask {

//	Task<Void> task;
//	EnemyComponent ia;
//	PlayerComponent player;
//	PlayerComponent player2;
//	PlayerComponent cercano;
//	String action;
//	String ult;
//	String penUlt;
//	String aux;
//	String directionX = "left";
//	String directionY = "";
//
//	public IATask(EnemyComponent enemy, PlayerComponent player, PlayerComponent player2, String action) {
//		this.ia = enemy;
//		this.player = player;
//		this.player2 = player2;
//		this.action = action;
//		setTarea(new Task<Void>() {
//			@Override
//			protected Void call() throws Exception {
//
//				switch (action) {
//				case "chase":
//					while (ia.getVidas() >= 0) {
//						if (ia.bombsPlaced == 1) {
//							avoidBomb();
//							Thread.sleep(1000);
//						}
//						cercano = masCercano(ia, player, player2);
//						chase(ia, cercano);
////						System.out.println("moviendo");
//						Thread.sleep(500);
//					}
//					break;
//				case "walls":
//					while (ia.getVidas() >= 0) {
//						walls();
//						Thread.sleep(500);
//					}
//					break;
//				case "escape":
//					escape();
//					break;
//				}
//				return null;
//			}
//		});
//	}
//
//	public PlayerComponent masCercano(EnemyComponent enemigo, PlayerComponent player1, PlayerComponent player2) {
//		PlayerComponent cercano = player2;
//
//		double d1;
//		double d2;
//
//		d1 = Math.hypot(player1.position.getX() - enemigo.position.getX(),
//				player1.position.getY() - enemigo.position.getY());
//		d2 = Math.hypot(player2.position.getX() - enemigo.position.getX(),
//				player2.position.getY() - enemigo.position.getY());
//
//		if (d1 < d2) {
//			cercano = player1;
//		}
//
//		return cercano;
//	}
//
//	public void chase(EnemyComponent enemy, PlayerComponent player) throws InterruptedException {
//		if (player.position.getX() < ia.position.getX() & this.left()) {
//			ia.moveLeft();
//			saveMoves("left");
//
//		} else if (player.position.getX() > ia.position.getX() & this.right()) {
//			ia.moveRight();
//			saveMoves("right");
//
//		} else if (player.position.getY() > ia.position.getY() & this.down()) {
//			ia.moveDown();
//			saveMoves("down");
//
//		} else if (player.position.getY() < ia.position.getY() & this.up()) {
//			ia.moveUp();
//			saveMoves("up");
//
//		} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
//				& !this.right()) {
//			ia.moveUp();
//			saveMoves("up");
//			Thread.sleep(500);
//			ia.moveRight();
//			saveMoves("right");
//		} else if (player.position.getY() == ia.position.getY() & ia.position.getX() > player.position.getX()
//				& !this.left()) {
//			ia.moveDown();
//			saveMoves("down");
//			Thread.sleep(500);
//			ia.moveLeft();
//			saveMoves("left");
//		} else if (player.position.getX() == ia.position.getX() & ia.position.getY() > player.position.getY()
//				& !this.up()) {
//			ia.moveRight();
//			saveMoves("right");
//			Thread.sleep(500);
//			ia.moveUp();
//			saveMoves("up");
//		} else if (player.position.getX() == ia.position.getX() & ia.position.getY() < player.position.getY()
//				& !this.down()) {
//			ia.moveLeft();
//			saveMoves("left");
//			Thread.sleep(500);
//			ia.moveDown();
//			saveMoves("down");
//		} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
//				& !this.right() & !this.up()) {
//			ia.moveDown();
//			saveMoves("down");
//			Thread.sleep(500);
//			ia.moveRight();
//			saveMoves("right");
//		} else if (player.position.getY() == ia.position.getY() & ia.position.getX() > player.position.getX()
//				& !this.left() & !this.down()) {
//			ia.moveUp();
//			saveMoves("up");
//			Thread.sleep(500);
//			ia.moveLeft();
//			saveMoves("left");
//		} else if (player.position.getX() == ia.position.getX() & ia.position.getY() > player.position.getY()
//				& !this.up() & !this.right()) {
//			ia.moveLeft();
//			saveMoves("left");
//			Thread.sleep(500);
//			ia.moveUp();
//			saveMoves("up");
//		} else if (player.position.getX() == ia.position.getX() & ia.position.getY() < player.position.getY()
//				& !this.down() & !this.left()) {
//			ia.moveRight();
//			saveMoves("right");
//			Thread.sleep(500);
//			ia.moveDown();
//			saveMoves("down");
//		} else {
//			Thread.sleep(500);
//		}
//	}
//
//	public void walls() throws InterruptedException {
//		switch (directionX) {
//		case "left":
//			if (up() & down() & directionY == "down" & ult == "left") {
//				ia.moveDown();
//				saveMoves("down");
//				directionY = "down";
//			} else if (up() & down() & directionY == "up" & ult == "up") {
//				ia.moveLeft();
//				saveMoves("left");
//			} else if (left() & down() & directionY == "up" & ult != "up") {
//				ia.moveDown();
//				saveMoves("down");
//				directionY = "down";
//			} else if (left() & up() & directionY == "up" & ult == "up") {
//				ia.moveLeft();
//				saveMoves("left");
//			} else if (left() & down() & directionY == "up" & ult == "up") {
//				ia.moveLeft();
//				saveMoves("left");
//			} else if (left()) {
//				ia.moveLeft();
//				saveMoves("left");
//			} else if (up()) {
//				ia.moveUp();
//				saveMoves("up");
//				directionY = "up";
////				directionX = "right";
//			} else if (down() & right() & directionY == "up") {
////				ia.moveUp();
//				directionX = "right";
//			} else if (down()) {
//				ia.moveDown();
//				directionY = "down";
////				directionX = "left";
//			} else if (up() & directionY == "down") {
//				ia.moveUp();
//				directionX = "right";
//			} else if (down() & directionY == "up") {
//				ia.moveDown();
//				directionX = "right";
//			
//			} else {
//				directionX = "right";
//			}
//			break;
//		case "right":
//			if (up() & down() & directionY == "up" & ult == "right") {
//				ia.moveUp();
//				directionY = "up";
//				directionX = "left";
//			} else if (right()) {
//				ia.moveRight();
//				saveMoves("right");
//
//			} else if (down()) {
//				ia.moveDown();
//				directionY = "down";
////				directionX = "left";
//			} else if (up() & left() & directionY == "down") {
////				ia.moveUp();
//				directionX = "left";
//			} else if (up()) {
//				ia.moveUp();
//				saveMoves("up");
//				directionY = "up";
//				directionX = "left";
//			} else if (down() & directionY == "up") {
//				ia.moveDown();
//				directionX = "left";
//			} else {
//				directionX = "left";
//			}
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	public void escape() {
//
//	}
//
//	public void avoidBomb() throws InterruptedException {
//
//		switch (ult) {
//		case "up":
//			ia.moveDown();
//			Thread.sleep(500);
//			switch (penUlt) {
//			case "down":
//				ia.moveUp();
//				break;
//			case "up":
//				ia.moveDown();
//				break;
//			case "left":
//				ia.moveRight();
//				break;
//			case "right":
//				ia.moveLeft();
//				break;
//			default:
//				break;
//			}
//			break;
//		default:
//			break;
//		case "down":
//			ia.moveUp();
//			Thread.sleep(500);
//			switch (penUlt) {
//			case "down":
//				ia.moveUp();
//				break;
//			case "up":
//				ia.moveDown();
//				break;
//			case "left":
//				ia.moveRight();
//				break;
//			case "right":
//				ia.moveLeft();
//				break;
//			default:
//				break;
//			}
//		case "left":
//			ia.moveRight();
//			Thread.sleep(500);
//			switch (penUlt) {
//			case "down":
//				ia.moveUp();
//				break;
//			case "up":
//				ia.moveDown();
//				break;
//			case "left":
//				ia.moveRight();
//				break;
//			case "right":
//				ia.moveLeft();
//				break;
//			default:
//				break;
//			}
//		case "right":
//			ia.moveLeft();
//			Thread.sleep(500);
//			switch (penUlt) {
//			case "down":
//				ia.moveUp();
//				break;
//			case "up":
//				ia.moveDown();
//				break;
//			case "left":
//				ia.moveRight();
//				break;
//			case "right":
//				ia.moveLeft();
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//	public void saveMoves(String move) {
//		if (ult == "") {
//			ult = move;
//		} else {
//			aux = ult;
//			ult = move;
//			penUlt = aux;
//		}
//	}
//
//	public boolean up() {
//		return ia.canMove(new Point2D(0, -BombermanApp.TILE_SIZE));
//	}
//
//	private boolean down() {
//		return ia.canMove(new Point2D(0, BombermanApp.TILE_SIZE));
//	}
//
//	private boolean left() {
//		return ia.canMove(new Point2D(-BombermanApp.TILE_SIZE, 0));
//	}
//
//	private boolean right() {
//		return ia.canMove(new Point2D(BombermanApp.TILE_SIZE, 0));
//	}
//
//	public Task<Void> getTarea() {
//		return task;
//	}
//
//	public void setTarea(Task<Void> tarea) {
//		this.task = tarea;
//	}
}
