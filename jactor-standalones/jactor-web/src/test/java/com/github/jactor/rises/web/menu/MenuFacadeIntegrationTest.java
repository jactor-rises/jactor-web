package com.github.jactor.rises.web.menu;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.web.JactorWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JactorWeb.class)
@DisplayName("The MenuFacade")
class MenuFacadeIntegrationTest {
    @Resource(name = "jactor.web.menuFacade") private MenuFacade testMenuFacade;

    @DisplayName("should fail when fetching items for an unknown menu")
    @Test void shouldFailWhenFetchingItemsForAnUnknownMenu() {
        MenuTarget menuTarget = new MenuTarget(new MenuItemTarget("some target"), new Name("unknown"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> testMenuFacade.fetchMenuItem(new MenuTargetRequest(menuTarget)));
    }

    @DisplayName("should fetch menu item with chosen child")
    @Test void whenFindingMenuItemsAndTheNameIsKnownTheListOfMenuItemsWillBeReturned() {
        MenuTarget menuTarget = new MenuTarget(new MenuItemTarget("user?choose=jactor"), new Name("main"));
        MenuTargetRequest menuTargetRequest = new MenuTargetRequest(menuTarget);

        List<MenuItem> menuItems = testMenuFacade.fetchMenuItem(menuTargetRequest);

        for (MenuItem menuItem : menuItems) {
            Name itemName = menuItem.getItemName();
            Name chosenName = new Name("jactor");

            if (new Name("menu.main.home").equals(itemName)) {
                assertThat(menuItem.isChildChosen()).as("home.children").isEqualTo(true);
            } else if (chosenName.equals(itemName)) {
                assertThat(menuItem.isChosen()).as("jactor").isEqualTo(true);
            } else {
                assertThat(menuItem.isChildChosen()).as("other item names").isEqualTo(false);
            }
        }
    }
}
