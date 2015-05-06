package astroBiz.view;

/**
 * An interface for all view mode managers located in astroBiz.view. This allows the manager to be passed through to
 * Confirmation so that the VM can be updated depending upon the outcome of the confirmation. A single abstract method
 * exists for the purposes of applying the confirmed viewmode to the active manager.
 * @author Matt Bangert
 *
 */
public interface Manager {

	public void setVM(VM vm);
	
}
