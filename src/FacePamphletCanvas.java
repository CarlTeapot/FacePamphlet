/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;

import java.awt.*;
import java.util.Iterator;

public class FacePamphletCanvas extends GCanvas
		implements FacePamphletConstants {

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}


	/**
	 * This method displays a message string near the bottom of the
	 * canvas.  Every time this method is called, the previously
	 * displayed message (if any) is replaced by the new message text
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel label = new GLabel(msg);
		label.setLocation(getWidth() / 2 - label.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		label.setFont(MESSAGE_FONT);
		add(label);
	}


	/**
	 * This method displays the given profile on the canvas.  The
	 * canvas is first cleared of all existing items (including
	 * messages displayed near the bottom of the screen) and then the
	 * given profile is displayed.  The profile display includes the
	 * name of the user from the profile, the corresponding image
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		displayName(profile.getName());
		displayImage(profile.getImage());
		displayStatus(profile);
		displayFriends(profile);
	}

	// displays the profile name on the canvas
	private void displayName(String name) {
		GLabel label = new GLabel(name);
		label.setFont(PROFILE_NAME_FONT);
		label.setColor(Color.BLUE);
		label.setLocation(LEFT_MARGIN, TOP_MARGIN + label.getAscent());
		nameY = TOP_MARGIN + label.getAscent();
		add(label);
	}

	// displays the image on the canvas
	private void displayImage(GImage image) {
		if (image != null) {
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			image.setLocation(LEFT_MARGIN, nameY + IMAGE_MARGIN);
			add(image);
		} else {
			GRect rect = new GRect(LEFT_MARGIN, nameY + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);
			label.setLocation(LEFT_MARGIN + IMAGE_WIDTH / 2 - label.getWidth() / 2,
					nameY + IMAGE_MARGIN + IMAGE_HEIGHT / 2 + label.getHeight() / 2);
			add(label);
			add(rect);

		}
	}

	// displays the profile status
	private void displayStatus(FacePamphletProfile name) {
		String status = name.getStatus();
		if (status.isEmpty())
			status = "No current status";
		else status = status;

		GLabel label = new GLabel(status);
		label.setFont(PROFILE_STATUS_FONT);
		label.setLocation(LEFT_MARGIN, +nameY + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + label.getAscent());
		add(label);
	}

	// displays friends of the user
	private void displayFriends(FacePamphletProfile name) {
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		label.setLocation(getWidth() / 2 - label.getWidth() / 2,
				+nameY + IMAGE_MARGIN);
		add(label);
		Iterator<String> iterator = name.getFriends();
		double i = label.getHeight();
		while (iterator.hasNext()) {
			GLabel friend = new GLabel(iterator.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			friend.setLocation(label.getX(), label.getY() + i);
			add(friend);
			i += label.getHeight();
		}
	}
	// variable for passing information from one method to another
	// this is better than combining two separate methods together and messing up decomposition
	private double nameY = 0;
}
