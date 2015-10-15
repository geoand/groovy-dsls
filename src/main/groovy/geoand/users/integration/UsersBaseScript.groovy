package geoand.users.integration

import geoand.users.spec.UsersSpec

/**
 * Created by gandrianakis on 15/10/2015.
 */
abstract class UsersBaseScript extends Script {

    def users(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=UsersSpec) Closure cl) {
        def users = new UsersSpec()
        def code = cl.rehydrate(users, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY

        code()

        return users.allUsers
    }
}
