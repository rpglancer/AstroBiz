package astroBiz.view;

/**
 * Empty interface exists solely for passing multiple different viewmode [VM] enums through to Confirmation by [ab]using polymorphism.
 * @author Matt Bangert
 *
 */
public interface VM {
	public static VM activeVM = null;
	public int getOpt();
}
