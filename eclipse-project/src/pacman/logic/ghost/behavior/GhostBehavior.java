package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

/**
 * A szellemek viselkedését megvalósító interface.
 * 
 * @author Váradi Dominik
 *
 */
public interface GhostBehavior {
	/**
	 * A paraméterben érkezett szellemet mozgatja egy adott logika (viselkedés) szerint.
	 * 
	 * @param g - A szellem
	 */
	public void moveGhost( Ghost g );
}
