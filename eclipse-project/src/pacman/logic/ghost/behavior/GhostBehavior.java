package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

/**
 * A szellemek viselked�s�t megval�s�t� interface.
 * 
 * @author V�radi Dominik
 *
 */
public interface GhostBehavior {
	/**
	 * A param�terben �rkezett szellemet mozgatja egy adott logika (viselked�s) szerint.
	 * 
	 * @param g - A szellem
	 */
	public void moveGhost( Ghost g );
}
