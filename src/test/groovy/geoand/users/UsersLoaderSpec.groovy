package geoand.users

import geoand.users.model.User
import spock.lang.Specification

import static org.assertj.core.api.Assertions.*;

/**
 * Created by gandrianakis on 15/10/2015.
 */
class UsersLoaderSpec extends Specification {

    def "invalid content"(String content) {
        when:
            UsersLoader.loadUsers(content)

        then:
            thrown RuntimeException

        where:
            content | _
            '''
                wrong {
                    names 'geo', 'master', 'kicker', 'general'
                    user 'geo' follows 'master', 'general'
                    user 'master' follows 'geo'
                    user 'general' follows 'master'
                    user 'kicker' follows 'master'
                }
            ''' | _
            '''
                users {
                    wrong 'geo', 'master', 'kicker', 'general'
                    user 'geo' follows 'master', 'general'
                    user 'master' follows 'geo'
                    user 'general' follows 'master'
                    user 'kicker' follows 'master'
                }
            ''' | _
            '''
                users {
                        names 'geo', 'master', 'kicker', 'general'
                        wrong 'geo' follows 'master', 'general'
                        user 'master' follows 'geo'
                        user 'general' follows 'master'
                        user 'kicker' follows 'master'
                }
            ''' | _
            '''
                users {
                        names 'geo', 'master', 'kicker', 1
                        user 'geo' follows 'master', 'general'
                        user 'master' follows 'geo'
                        user 'general' follows 'master'
                        user 'kicker' follows 'master'
                }
            ''' | _

    }

    def "missing user"() {
        given:
            final String content =
            '''
                users {
                    names 'geo', 'master', 'kicker', 'general'
                    user 'lala' follows 'master', 'general'
                    user 'master' follows 'geo'
                    user 'general' follows 'master'
                    user 'kicker' follows 'master'
                }
            '''

        when:
            UsersLoader.loadUsers(content)

        then:
            thrown RuntimeException
    }


    def "valid content"() {
        given:
            final String VALID_CONTENT =
            '''
                users {
                    names 'geo', 'master', 'kicker', 'general'
                    user 'geo' follows 'master', 'general'
                    user 'master' follows 'geo'
                    user 'general' follows 'master'
                    user 'kicker' follows 'master'
                }
            '''

        when:
            final Set<User> users = UsersLoader.loadUsers(VALID_CONTENT)

        then:
            assertThat(users*.name).containsOnly('geo', 'master', 'kicker', 'general')
            assertFollowers(users, 'geo', 'master')
            assertFollowers(users, 'master', 'geo' ,'general', 'kicker')
            assertFollowers(users, 'kicker')
            assertFollowers(users, 'general', 'geo')
    }

    def "load from classpath"() {
        when:
            final Set<User> users = UsersLoader.loadUsersFromClasspath("/users.conf")

        then:
            assertThat(users*.name).containsOnly('geo', 'geo2')
            assertFollowers(users, 'geo2', 'geo')
    }

    def "load from absolutePath"() {
        when:
            final Set<User> users = UsersLoader.loadUsersFromAbsolutePath(UsersLoaderSpec.getResource('/users.conf').toURI().path)

        then:
            assertThat(users*.name).containsOnly('geo', 'geo2')
            assertFollowers(users, 'geo2', 'geo')
    }

    void assertFollowers(Collection<User> allUsers, String userName, String... expectedFollowerUserNames) {
        assertThat(findUser(allUsers, userName)?.followers*.name).containsOnly(expectedFollowerUserNames)
    }

    User findUser(Collection<User> users, String userName) {
        return users.find{it.name == userName}
    }

}
