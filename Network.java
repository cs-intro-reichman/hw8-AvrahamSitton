/** Represents a social network. The network has users, who follow other users.
 *  Each user is an instance of the User class. */
public class Network {
    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /**
     * Creates a network with a given maximum number of users.
     * @param maxUserCount Maximum number of users the network can hold
     */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /**
     * Creates a network with some users. For testing purposes only.
     * @param maxUserCount Maximum number of users the network can hold
     * @param gettingStarted Flag to indicate test initialization
     */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /**
     * Gets the current number of users in the network.
     * @return Number of users currently in the network
     */
    public int getUserCount() {
        return this.userCount;
    }

    /**
     * Finds and returns the user with the given name.
     * @param name Name of user to find (case-insensitive)
     * @return User object if found, null if not found
     */
    public User getUser(String name) {
        if (name == null) {
            return null;
        }
        name = name.toLowerCase();
        for(int i = 0; i < userCount; i++) {
            if (name.equals(users[i].getName().toLowerCase())) {
                return users[i];
            }
        }
        return null;
    }

    /**
     * Adds a new user to the network.
     * @param name Name of new user
     * @return true if user added successfully, false if network is full or user exists
     */
    public boolean addUser(String name) {
        if (getUser(name) != null) {
            return false;
        }
        for(int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = new User(name);
                userCount++;
                return true;
            }
        }
        return false;
    }

    /**
     * Makes one user follow another.
     * @param name1 Name of user who will follow
     * @param name2 Name of user to be followed
     * @return true if follow relationship created successfully, false otherwise
     */
    public boolean addFollowee(String name1, String name2) {
        if (getUser(name1) == null || getUser(name2) == null || getUser(name1).follows(name2) || name1.equals(name2)
        || name1 == null || name2 == null) {
            return false;
        }
        User user = getUser(name1);
        user.addFollowee(name2);
        return true;
    }

    /**
     * Recommends a user to follow based on mutual followees.
     * @param name Name of user to get recommendation for
     * @return Name of recommended user to follow, null if no recommendation available
     */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) {
            return null;
        }
        int index = -1;
        int countMutuals = -1;
        for(int i = 0; i < userCount; i++) {
            User currUser = users[i];
            if (user == currUser) {
                continue;
            }
            if (user.follows(currUser.getName())) {
                continue;
            }
            if (user.countMutual(currUser) > countMutuals) {
                countMutuals = user.countMutual(currUser);
                index = i;
            }
        }
        return users[index].getName();
    }

    /**
     * Finds the most followed user in the network.
     * @return Name of user with most followers, null if network is empty
     */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }
        int max = followeeCount(users[0].getName());
        int index = 0;
        for (int i = 1; i < userCount; i++) {
            int curr = followeeCount(users[i].getName());
            if (curr > max) {
                max = curr;
                index = i;
            }
        }
        return users[index].getName();
    }

    /**
     * Counts how many users follow the given user.
     * @param name Name of user to count followers for
     * @return Number of followers for the given user
     */
    private int followeeCount(String name) {
        int counter = 0;
        for(int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Creates a string representation of the network.
     * @return String describing all users and their followees
     */
    public String toString() {
        String ans = "Network:";
        for (int i = 0; i < userCount; i++) {
             ans += "\n"+ users[i].toString();
        }
        return ans;
    }
}