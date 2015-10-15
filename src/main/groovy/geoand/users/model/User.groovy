package geoand.users.model

/**
 * Created by gandrianakis on 15/10/2015.
 */
class User {
    final String name
    final Set<User> followers = [] as Set
    final Set<User> following = [] as Set

    User(String name) {
        this.name = name
    }

    void addFollower(User u) {
        followers << u
        u.following << this
    }

    boolean equals(obj) {
        getClass() == obj?.getClass() && name == obj?.name
    }
}
