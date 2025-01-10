/** Represents a user in a social network. A user is characterized by a name,
 *  a list of user names that s/he follows, and the list's size. */
 public class User {
    // Maximum number of users that a user can follow
    static int maxfCount = 10;
    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /**
     * Creates a user with an empty list of followees.
     * @param name Name of the new user
     */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount];
        fCount = 0;
    }

    /**
     * Creates a user with some followees. For testing purposes only.
     * @param name Name of the new user
     * @param gettingStarted Flag to indicate test initialization
     */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /**
     * Gets user's name.
     * @return User's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets array of users this user follows.
     * @return Array of followee names
     */
    public String[] getfFollows() {
        return follows;
    }

    /**
     * Gets number of users this user follows.
     * @return Count of followees
     */
    public int getfCount() {
        return fCount;
    }

    /**
     * Checks if user follows another user.
     * @param name Name to check
     * @return true if this user follows the specified name
     */
    public boolean follows(String name) {
        name = ChangeName(name);
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new user to follow.
     * @param name Name of user to follow
     * @return true if successfully added, false if already following or list full
     */
    public boolean addFollowee(String name) {
        name = ChangeName(name);
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                return false;
            }
        }
        if (fCount < maxfCount) {
            follows[fCount++] = name;
            return true;
        }
        return false;
    }

    /**
     * Removes a user from follow list.
     * @param name Name of user to unfollow
     * @return true if successfully removed, false if user not found
     */
    public boolean removeFollowee(String name) {
        if (name == null) {
            return false;
        }

        name = ChangeName(name);
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                for (int j = i; j < fCount - 1; j++) {
                    follows[j] = follows[j + 1];
                }
                follows[--fCount] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Counts mutual followees between this user and another.
     * @param other User to compare with
     * @return Number of mutual followees
     */
    public int countMutual(User other) {
        int count = 0;
        for (int i = 0; i < fCount; i++) {
            for (int j = 0; j < other.fCount; j++) {
                if (follows[i] != null && follows[i].equals(other.follows[j])) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if two users follow each other.
     * @param other User to check friendship with
     * @return true if users mutually follow each other
     */
    public boolean isFriendOf(User other) {
        if (!other.follows(this.name)) {
            return false;
        }

        for (int i = 0; i < fCount; i++) {
            if (this.follows[i] != null && this.follows[i].equals(other.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a string representation of the user.
     * @return String describing user and their followees
     */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }

    /**
     * Capitalizes the first letter of a name.
     * @param name Name to capitalize
     * @return Name with first letter capitalized
     */
    public String ChangeName(String name) {
        char First = name.charAt(0);
        First = Character.toUpperCase(First);
        name = First + name.substring(1);
        return name;
    }
}