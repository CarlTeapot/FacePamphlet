/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;

import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {

	private String name;
	private String profStatus;
	private GImage image;
	private ArrayList<String> friends;
	private String friendsList;

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 */
	public FacePamphletProfile(String name) {
		friends = new ArrayList<>();
		this.name = name;
		profStatus = "";
		image = null;
		friendsList = "";
	}

	/**
	 * This method returns the name associated with the profile.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the image associated with the profile.
	 * If there is no image associated with the profile, the method
	 * returns null.
	 */
	public GImage getImage() {
		return image;
	}

	/**
	 * This method sets the image associated with the profile.
	 */
	public void setImage(GImage image) {
		this.image = image;
	}

	/**
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */
	public String getStatus() {
		return profStatus;
	}

	/**
	 * This method sets the status associated with the profile.
	 */
	public void setStatus(String status) {
		this.profStatus = status;
	}

	/**
	 * This method adds the named friend to this profile's list of
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which
	 * case, the given friend name is not added to the list of friends
	 * a second time.)
	 */
	public boolean addFriend(String friend) {
		if (!friends.contains(friend)) {
			friends.add(friend);
			return true;
		} else return false;
	}

	/**
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if (friends.contains(friend)) {
			friends.remove(friend);
			return true;
		} else return false;
	}

	/**
	 * This method returns an iterator over the list of friends
	 * associated with the profile.
	 */
	public Iterator<String> getFriends() {
		return friends.iterator();
	}

	public String toString() {
		friendsList = "";
		for (String friend : friends) {
			friendsList += friend + ", ";
		}
		String display = name + "(" + profStatus + "): " + friendsList;
		display = display.substring(0, display.length() - 2);
		return display;
	}

}
