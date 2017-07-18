package org.mapfish.print.config.access;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Test;
import org.mapfish.print.AbstractMapfishSpringTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.fail;

public class AndAccessAssertionTest extends AbstractMapfishSpringTest {

    @Autowired
    ApplicationContext applicationContext;

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test(expected = AssertionError.class)
    public void testSetPredicates() throws Exception {

        final AndAccessAssertion andAssertion = applicationContext.getBean(AndAccessAssertion.class);
        andAssertion.setPredicates(AlwaysAllowAssertion.INSTANCE, AlwaysAllowAssertion.INSTANCE);
        andAssertion.setPredicates(AlwaysAllowAssertion.INSTANCE);
    }
    @Test(expected = AccessDeniedException.class)
    public void testAssertAccessNotAllowed() throws Exception {
        final AndAccessAssertion andAssertion = applicationContext.getBean(AndAccessAssertion.class);
        AccessAssertion pred1 = new RoleAccessAssertion().setRequiredRoles(Sets.newHashSet("ROLE_USER"));
        AccessAssertion pred2 = new RoleAccessAssertion().setRequiredRoles(Sets.newHashSet("ROLE_OTHER"));
        andAssertion.setPredicates(pred1, pred2);

        AccessAssertionTestUtil.setCreds("ROLE_USER");
        andAssertion.assertAccess("", this);
    }
    @Test
    public void testAssertAccessAllowed() throws Exception {
        final AndAccessAssertion andAssertion = applicationContext.getBean(AndAccessAssertion.class);
        AccessAssertion pred1 = new RoleAccessAssertion().setRequiredRoles(Sets.newHashSet("ROLE_USER"));
        AccessAssertion pred2 = new RoleAccessAssertion().setRequiredRoles(Sets.newHashSet("ROLE_OTHER"));
        andAssertion.setPredicates(pred1, pred2);

        AccessAssertionTestUtil.setCreds("ROLE_USER", "ROLE_OTHER");
        andAssertion.assertAccess("", this);
    }

    @Test
    public void testMarshalUnmarshal() throws Exception {
        final AndAccessAssertion andAssertion = applicationContext.getBean(AndAccessAssertion.class);
        AccessAssertion pred1 = new RoleAccessAssertion().setRequiredRoles(Sets.newHashSet("ROLE_USER"));
        AccessAssertion pred2 = new RoleAccessAssertion().setRequiredRoles(Sets.newHashSet("ROLE_OTHER"));
        andAssertion.setPredicates(pred1, pred2);

        AccessAssertionTestUtil.setCreds("ROLE_USER", "ROLE_OTHER");
        andAssertion.assertAccess("", this);

        try {
            AccessAssertionTestUtil.setCreds("ROLE_USER");
            andAssertion.assertAccess("", this);

            fail("Expected an AccessDeniedException exception");
        } catch (AccessDeniedException e) {
            // good
        }

    }
}