/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;

import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	/*
		private instance variables. I could have created these variables inside methods,
		but then I would have to store the button texts as Constants tu use them in action performer methods
		and I preferred to just create global instance variables, as changing the button texts is easier this way
		(compared to changing the initializing code and the values of constants separately)
	 */
	private JTextField nameFIeld;
	private JTextField statusFIeld;
	private JTextField pictureFIeld;
	private JTextField friendFIeld;

	private JButton addButton;
	private JButton deleteButton;
	private JButton lookupButton;
	private JButton statusButton;
	private JButton pictureButton;
	private JButton friendButton;

	private FacePamphletDatabase database;
	private FacePamphletProfile currentProfile;
	private FacePamphletCanvas canvas;
	private String msg = "";

	public void init() {
		database = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
		drawLeft();
		drawTop();
		addActionListeners(this);
	}

	// initializes the top buttons and fields
	private void initTop() {
		nameFIeld = new JTextField(TEXT_FIELD_SIZE);
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
	}

	// initializes the left buttons and fields
	private void initLeft() {
		statusFIeld = new JTextField(TEXT_FIELD_SIZE);
		friendFIeld = new JTextField(TEXT_FIELD_SIZE);
		pictureFIeld = new JTextField(TEXT_FIELD_SIZE);

		statusButton = new JButton("Change Status");
		pictureButton = new JButton("Change Picture");
		friendButton = new JButton("Add Friend");
	}

	// draws the top buttons and fields
	private void drawTop() {
		initTop();
		add(new JLabel("Name"), NORTH);
		add(nameFIeld, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
	}

	// draws the left buttons and fields
	private void drawLeft() {
		initLeft();
		add(statusFIeld, WEST);
		add(statusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureFIeld, WEST);
		add(pictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendFIeld, WEST);
		add(friendButton, WEST);

	}

	// handles the action of adding the user
	// the rules of adding the profile was confusing in the word file
	// so I chose to switch the current user when adding an already existing user
	private void addAction(ActionEvent e) {
		if (e.getActionCommand().equals(addButton.getActionCommand())) {
			String text = nameFIeld.getText();

			if (!onlySpacesOrEmpty(text)) {
				if (!database.containsProfile(text)) {
					FacePamphletProfile profile = new FacePamphletProfile(text);
					database.addProfile(profile);
					currentProfile = profile;
					msg = "Added new profile";
				} else {
					FacePamphletProfile profile = database.getProfile(text);
					msg = "Profile with the name " + text + " already exists";
				}
				currentProfile = database.getProfile(text);
			}
		}
	}

	// this method checks if the Field text is empty or only contains spaces
	private boolean onlySpacesOrEmpty(String text) {
		if (text.isEmpty())
			return true;
		for (char i : text.toCharArray()) {
			if (i != ' ')
				return false;
		}
		return true;
	}

	// handles the action of deleting the profile
	// it then removes the profile from the friends list of another users
	private void deleteAction(ActionEvent e) {
		if (e.getActionCommand().equals(deleteButton.getActionCommand())) {
			String text = nameFIeld.getText();
			if (!onlySpacesOrEmpty(text)) {
				if (database.containsProfile(text)) {
					database.deleteProfile(text);
					msg = "Profile of " + text + " deleted";
					for (FacePamphletProfile profile : database.profiles) {
						profile.removeFriend(text);
					}
				} else {
					msg = "profile does not exist";
				}
				currentProfile = null;
				canvas.showMessage(msg);
			}
		}
	}

	// handles the action of looking up another profile
	// the looked up profile becomes the current profile
	private void lookupAction(ActionEvent e) {
		if (e.getActionCommand().equals(lookupButton.getActionCommand())) {
			String text = nameFIeld.getText();
			if (!onlySpacesOrEmpty(text)) {
				if (database.containsProfile(text)) {
					msg = "Displaying: " + text;
					currentProfile = database.getProfile(text);
				} else {
					msg = "profile does not exist";
					currentProfile = null;
				}
				canvas.showMessage(msg);
			}
		}
	}

	// handles the action of updating the status
	private void statusAction(ActionEvent e) {
		if (e.getActionCommand().equals(statusButton.getActionCommand()) ||
				e.getActionCommand().equals(statusFIeld.getText())) {
			if (!onlySpacesOrEmpty(statusFIeld.getText())) {
				if (currentProfile != null) {
					currentProfile.setStatus(statusFIeld.getText());
					msg = currentProfile.getName() + " set status to " + statusFIeld.getText();
				} else {
					msg = "choose a profile to add a status";
				}
				canvas.showMessage(msg);
			}
		}
	}

	// handles the action of choosing the picture
	private void pictureAction(ActionEvent e) {
		if (e.getActionCommand().equals(pictureButton.getActionCommand()) ||
				e.getActionCommand().equals(pictureFIeld.getText())) {
			String fileName = pictureFIeld.getText();
			if (currentProfile != null) {
				if (!onlySpacesOrEmpty(fileName)) {
					GImage image = null;
					try {
						image = new GImage(fileName);
						currentProfile.setImage(image);
						msg = "Picture updated";
					} catch (ErrorException ex) {
						msg = "Unable to open image file " + fileName;
					}
				}
			} else {
				msg = "choose a profile to add a picture";
			}
				canvas.showMessage(msg);
			}
		}

	// checks whether the current user already has the desired friend in the friends list
	private boolean checkFriend(FacePamphletProfile profile, String friend) {
		Iterator<String> iterator = profile.getFriends();
		while (iterator.hasNext()) {
			String xd = iterator.next();
			if (xd.equals(friend))
				return true;
		}
		return false;
	}
	// I did not check the field name if it was empty or only consisted of spaces,
	// because you can only add profiles as friends, and profiles can not have empty names,
	// that was made sure of in previous methods
	private void friendsAction(ActionEvent e) {
		if (e.getActionCommand().equals(friendButton.getActionCommand()) ||
				e.getActionCommand().equals(friendFIeld.getText())) {
			String friend = friendFIeld.getText();
			if (currentProfile != null) {
				if (friend.equals(currentProfile.getName()))
					msg = "Can't add yourself as a friend";
				else {
					if (database.containsProfile(friend)) {
						if (checkFriend(currentProfile, friend)) {
							msg = currentProfile.getName() + " already has " + friend + " as a friend";
						} else {
							currentProfile.addFriend(friend);
							database.getProfile(friend).addFriend(currentProfile.getName());
							msg = currentProfile.getName() + " Added as a friend";
						}
					} else {
						msg = friend + " does not exist";
					}
				}
			} else {
				msg = "choose a profile to add a friend";
			}
			canvas.showMessage(msg);
		}
	}

	// handles the actions
	public void actionPerformed(ActionEvent e) {
		addAction(e);
		deleteAction(e);
		lookupAction(e);

		statusAction(e);
		pictureAction(e);
		friendsAction(e);

		canvas.removeAll();
		if (currentProfile != null)
			canvas.displayProfile(currentProfile);
		canvas.showMessage(msg);
	}
}