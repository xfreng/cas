package org.jasig.cas.authentication;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Credential for authenticating with a username and password and rand.
 *
 * @author sf.xiong
 * @since 4.2.8
 */
public class UsernamePasswordRandCredential extends UsernamePasswordCredential {

    private static final long serialVersionUID = -6087899752446821856L;

    /**
     * The rand.
     */
    @NotNull
    @Size(min = 1, message = "rand.required")
    private String rand;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        final UsernamePasswordRandCredential that = (UsernamePasswordRandCredential) o;

        if (rand != null ? !rand.equals(that.rand) : that.rand != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(rand)
                .toHashCode();
    }

    public String getRand() {
        return rand;
    }

    public void setRand(final String rand) {
        this.rand = rand;
    }
}
