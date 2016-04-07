package de.zabuza.sparkle.freewar.frames;

/**
 * Interface for frame manager that manage the frames of
 * {@link de.zabuza.sparkle.freewar.IFreewarInstance IFreewarInstance}s.
 * 
 * @author Zabuza
 * 
 */
public interface IFrameManager {
	/**
	 * Switches to the given frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 * 
	 * @param frame
	 *            The type of the frame to switch to
	 */
	public void switchToFrame(final EFrame frame);
}
