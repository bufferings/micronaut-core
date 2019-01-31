/*
 * Copyright 2017-2018 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.micronaut.security.authentication;

import io.micronaut.security.token.config.TokenConfiguration;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

/**
 * Default bean to adapt from User Details to {@link Authentication}.
 *
 * @author Sergio del Amo
 * @since 1.0.0
 */
@Singleton
public class DefaultToAuthenticationUserDetailsAdapter implements UserDetailsAuthenticationResponseToAuthenticationAdapter {

    private final TokenConfiguration tokenConfiguration;

    /**
     *
     * @param tokenConfiguration The Token configuration.
     */
    public DefaultToAuthenticationUserDetailsAdapter(TokenConfiguration tokenConfiguration) {
        this.tokenConfiguration = tokenConfiguration;
    }

    @Nonnull
    @Override
    public Authentication adapt(AuthenticationResponse authenticationResponse) {
        if (authenticationResponse instanceof UserDetails) {
            AuthenticationUserDetailsAdapter adapter = new AuthenticationUserDetailsAdapter((UserDetails) authenticationResponse);
            if (tokenConfiguration != null) {
                adapter.setRolesNames(tokenConfiguration.getRolesName());
            }
            return adapter;
        }
        throw new IllegalArgumentException("authenticationResponse is not of type UserDetails");
    }
}
