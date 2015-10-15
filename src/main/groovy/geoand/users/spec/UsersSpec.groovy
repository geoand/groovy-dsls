package geoand.users.spec

import geoand.users.model.User

/**
 * Created by gandrianakis on 15/10/2015.
 */
class UsersSpec {

    final Set<User> allUsers = [] as Set

    void names(String... userNames) {
        allUsers.addAll(userNames.collect {
            new User(it)
        })
    }

    def user(String name) {
        [follows: { String... followedUserNames ->
            final def initUser = findUser(name)
            followedUserNames.collect { String followedUserName -> findUser(followedUserName) }.each { it.addFollower(initUser) }
        }]
    }

    private User findUser(String name) {
        final User user = allUsers.find { it.name == name }
        assert null != user
        user
    }
}
