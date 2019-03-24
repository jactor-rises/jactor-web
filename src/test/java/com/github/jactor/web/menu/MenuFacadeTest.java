package com.github.jactor.web.menu;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A MenuFacade")
class MenuFacadeTest {

  @Test
  @DisplayName("should fail if the menu asked for is unknown")
  void willFailWhenMenuIsUnknown() {
    MenuItem menuItem = new MenuItem("name", "target", null, Collections.emptyList());
    MenuFacade menuFacadeToTest = new MenuFacade(List.of(
        new Menu("known", List.of(menuItem))
    ));

    assertThatIllegalArgumentException().isThrownBy(() -> menuFacadeToTest.fetchMenuItemsByName("unknown.menu"))
        .withMessageContaining("unknown.menu")
        .withMessageContaining("known.menu");
  }

  @Test
  @DisplayName("should find menu items for known Menu")
  void willFindKnownMenuItems() {
    MenuItem menuItem = new MenuItem("name", "target", null, Collections.emptyList());
    MenuFacade menuFacadeToTest = new MenuFacade(List.of(
        new Menu("known", List.of(menuItem))
    ));

    List<MenuItem> menuItems = menuFacadeToTest.fetchMenuItemsByName("known");

    Assertions.assertThat(menuItems).contains(menuItem);
  }
}
