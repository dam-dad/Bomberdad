package dad.javafx.bomberdad.components;

import javafx.geometry.Point2D;

public class IA extends Thread {
	PlayerComponent ia;
	PlayerComponent player;
	PlayerComponent player2;
	PlayerComponent cercano;

	public IA(PlayerComponent player1, PlayerComponent player2, PlayerComponent player3) {
		ia = player1;
		player = player2;
		this.player2 = player3;
	}

	public void run() {
		try {
			while (player.getVidas() >= 0 & player2.getVidas() >= 0) {
				cercano = masCercano(ia, player, player2);
				perseguir(cercano);
				sleep(500);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void perseguir(PlayerComponent player) {
		try {
			if (player.position.getX() < ia.position.getX() & this.left()) {
				ia.moveLeft();

			} else if (player.position.getX() > ia.position.getX() & this.right()) {
				ia.moveRight();

			} else if (player.position.getY() > ia.position.getY() & this.down()) {
				ia.moveDown();

			} else if (player.position.getY() < ia.position.getY() & this.up()) {
				ia.moveUp();

			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
					& !this.right()) {
				ia.moveUp();
				sleep(500);
				ia.moveRight();
			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() > player.position.getX()
					& !this.left()) {
				ia.moveDown();
				sleep(500);
				ia.moveLeft();
			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() > player.position.getY()
					& !this.up()) {
				ia.moveRight();
				sleep(500);
				ia.moveUp();
			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() < player.position.getY()
					& !this.down()) {
				ia.moveLeft();
				sleep(500);
				ia.moveDown();
			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
					& !this.right() & !this.up()) {
				ia.moveDown();
				sleep(500);
				ia.moveRight();
			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() > player.position.getX()
					& !this.left() & !this.down()) {
				ia.moveUp();
				sleep(500);
				ia.moveLeft();
			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() > player.position.getY()
					& !this.up() & !this.right()) {
				ia.moveLeft();
				sleep(500);
				ia.moveUp();
			} else if (player.position.getX() == ia.position.getX() & ia.position.getY() < player.position.getY()
					& !this.down() & !this.left()) {
				ia.moveRight();
				sleep(500);
				ia.moveDown();
//			} else if (player.position.getY() == ia.position.getY() & ia.position.getX() < player.position.getX()
//					& !this.right() & !this.up() & !this.down()) {
//				ia.moveLeft();
//				sleep(500);
//				ia.moveUp();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PlayerComponent masCercano(PlayerComponent enemigo, PlayerComponent player1, PlayerComponent player2) {
		PlayerComponent cercano = player2;

		double d1;
		double d2;

		d1 = Math.hypot(player1.position.getX() - enemigo.position.getX(),
				player1.position.getY() - enemigo.position.getY());
		d2 = Math.hypot(player2.position.getX() - enemigo.position.getX(),
				player2.position.getY() - enemigo.position.getY());

		if (d1 < d2) {
			cercano = player1;
		}

		return cercano;
	}

	private boolean up() {
		return ia.canMove(new Point2D(0, -40));
	}

	private boolean down() {
		return ia.canMove(new Point2D(0, 40));
	}

	private boolean left() {
		return ia.canMove(new Point2D(-40, 0));
	}

	private boolean right() {
		return ia.canMove(new Point2D(40, 0));
	}
}
