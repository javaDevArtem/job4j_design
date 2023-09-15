package ru.job4j.generics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleStoreTest {

    @Test
    void whenAddAndFindThenRoleIsDeveloper() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("Developer");
    }

    @Test
    void whenAddAndFindThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        Role result = store.findById("10");
        assertThat(result).isNull();
    }

    @Test
    void whenAddDuplicateAndFindRoleIsDeveloper() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        store.add(new Role("1", "Engineer"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("Developer");
    }

    @Test
    void whenReplaceThenRoleIsEngineer() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        store.replace("1", new Role("1", "Engineer"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("Engineer");
    }

    @Test
    void whenNoReplaceRoleThenNoChangeRole() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        store.replace("10", new Role("10", "Engineer"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("Developer");
    }

    @Test
    void whenDeleteRoleThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        store.delete("1");
        Role result = store.findById("1");
        assertThat(result).isNull();
    }

    @Test
    void whenNoDeleteRoleThenRoleIsDeveloper() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        store.delete("10");
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("Developer");
    }

    @Test
    void whenReplaceOkThenTrue() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        boolean result = store.replace("1", new Role("1", "Engineer"));
        assertThat(result).isTrue();
    }

    @Test
    void whenReplaceNotOkThenFalse() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Developer"));
        boolean result = store.replace("10", new Role("10", "Engineer"));
        assertThat(result).isFalse();
    }
}