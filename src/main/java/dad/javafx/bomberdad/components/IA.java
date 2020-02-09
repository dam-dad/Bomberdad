//package dad.javafx.bomberdad.components;
//
//import dad.javafx.bomberdad.BombermanApp;
//import javafx.geometry.Point2D;
//
//public class IA extends Thread {
//	EnemyComponent ia;
//	PlayerComponent player;
//	PlayerComponent player2;
//	PlayerComponent cercano;
//	String action;
//
//	public IA(EnemyComponent enemy, PlayerComponent player, PlayerComponent player2, String action) {
//		ia = enemy;
//		this.player = player;
//		this.player2 = player2;
//		this.action = action;
//	}
//
//	public void run() {
//		try {
//			while (ia.getVidas() > 0) {
//				cercano = masCercano(ia, player, player2);
//				switch (action) {
//				case "chase":
//					chase(cercano);
//					sleep(500);
//					break;
//
//				case "escape":
//					escape();
//					break;
//				}
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void escape() {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void chase(PlayerComponent player) {
//		try {
//			if (player.position.getX() < ia.position.getX() & this.left()) {
//				ia.moveLeft();
//
//			} else if (player.position.getX() > ia.position.getX() & this.right()) {
//				ia.moveRight();
//
//			} else if (player.position.getY() > ia.position.getY() & this.down()) {
//				ia.moveDown();
//
//			} else if (player.position.getY() < ia.position.getY() & this.up()) {
//				ia.moveUp();
//
//			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
//					& !this.right()) {
//				ia.moveUp();
//				sleep(500);
//				ia.moveRight();
//			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() > player.position.getX()
//					& !this.left()) {
//				ia.moveDown();
//				sleep(500);
//				ia.moveLeft();
//			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() > player.position.getY()
//					& !this.up()) {
//				ia.moveRight();
//				sleep(500);
//				ia.moveUp();
//			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() < player.position.getY()
//					& !this.down()) {
//				ia.moveLeft();
//				sleep(500);
//				ia.moveDown();
//			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
//					& !this.right() & !this.up()) {
//				ia.moveDown();
//				sleep(500);
//				ia.moveRight();
//			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() > player.position.getX()
//					& !this.left() & !this.down()) {
//				ia.moveUp();
//				sleep(500);
//				ia.moveLeft();
//			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() > player.position.getY()
//					& !this.up() & !this.right()) {
//				ia.moveLeft();
//				sleep(500);
//				ia.moveUp();
//			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() < player.position.getY()
//					& !this.down() & !this.left()) {
//				ia.moveRight();
//				sleep(500);
//				ia.moveDown();
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
//	private boolean up() {
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
//}
