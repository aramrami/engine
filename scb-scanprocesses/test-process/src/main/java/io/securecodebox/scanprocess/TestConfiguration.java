package io.securecodebox.scanprocess;

import io.securecodebox.sdk.ScanProcessEntryPoint;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.PostConstruct;

/**
 * @author Rüdiger Heins - iteratec GmbH
 * @since 07.02.18
 */
@ScanProcessEntryPoint
public class TestConfiguration extends AbstractCamundaConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(TestConfiguration.class);

    private User approverUser;
    private User normalUser;

    @PostConstruct
    public void init() {
        approverUser = new InitUser("approver", "a");
        normalUser = new InitUser("user", "a");

    }

    @Override
    public void postProcessEngineBuild(final ProcessEngine processEngine) {
        //requireNonNull(adminUser);

        final IdentityService identityService = processEngine.getIdentityService();

        if (!userAlreadyExists(identityService, approverUser)) {
            createUser(identityService, approverUser);
        }

        if (!userAlreadyExists(identityService, normalUser)) {
            createUser(identityService, normalUser);
        }

        // create group
        if (identityService.createGroupQuery().groupId("approver").count() == 0) {
            Group approverGroup = identityService.newGroup("approver");
            approverGroup.setName("SecureCodeBox Approver");
            approverGroup.setType(Groups.GROUP_TYPE_SYSTEM);
            identityService.saveGroup(approverGroup);
        }
        identityService.createMembership(approverUser.getId(), "approver");
        identityService.createMembership(normalUser.getId(), "approver");
        LOG.info("Created user: {}", approverUser);
        LOG.info("Created user: {}", normalUser);
    }

    static boolean userAlreadyExists(IdentityService identityService, User adminUser) {
        final User existingUser = identityService.createUserQuery().userId(adminUser.getId()).singleResult();
        return existingUser != null;
    }

    static User createUser(final IdentityService identityService, final User adminUser) {
        User newUser = identityService.newUser(adminUser.getId());
        BeanUtils.copyProperties(adminUser, newUser);
        identityService.saveUser(newUser);
        return newUser;
    }

    private class InitUser implements User {

        private final String id;
        private final String password;

        public InitUser(String name, String password) {
            id = name;
            this.password = password;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
        }

        @Override
        public String getFirstName() {
            return null;
        }

        @Override
        public void setFirstName(String firstName) {

        }

        @Override
        public void setLastName(String lastName) {

        }

        @Override
        public String getLastName() {
            return null;
        }

        @Override
        public void setEmail(String email) {

        }

        @Override
        public String getEmail() {
            return null;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public void setPassword(String password) {

        }

        @Override
        public String toString() {
            return "InitUser{" + "id='" + id + '\'' + ", password='" + password + '\'' + '}';
        }
    }
}
