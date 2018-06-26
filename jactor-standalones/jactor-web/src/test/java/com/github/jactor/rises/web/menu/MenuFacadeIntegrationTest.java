package com.github.jactor.rises.web.menu;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.web.JactorWeb;
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
class MenuFacadeIntegrationTest {
    @Resource(name = "jactor.web.menuFacade") private MenuFacade testMenuFacade;

    @Test void whenFindingMenuItemsAndTheNameIsUnknownTheMethodWillFail() {
        MenuTarget menuTarget = new MenuTarget(new MenuItemTarget("some target"), new Name("unknown"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> testMenuFacade.fetchMenuItem(new MenuTargetRequest(menuTarget)));
    }

    @Test void whenFindingMenuItemsAndTheNameIsKnownTheListOfMenuItemsWillBeReturned() {
        MenuTarget menuTarget = new MenuTarget(new MenuItemTarget("user?user=jactor"), new Name("main"));
        MenuTargetRequest menuTargetRequest = new MenuTargetRequest(menuTarget);

        List<MenuItem> menuItems = testMenuFacade.fetchMenuItem(menuTargetRequest);

        for (MenuItem menuItem : menuItems) {
            Name itemName = menuItem.getItemName();
            Name chosenName = new Name("menu.main.jactor");

            if (new Name("menu.main.home").equals(itemName)) {
                assertThat(menuItem.isChildChosen()).as("home.children").isEqualTo(true);
            } else if (chosenName.equals(itemName)) {
                assertThat(menuItem.isChosen()).as("menu.main.jactor").isEqualTo(true);
            } else {
                assertThat(menuItem.isChildChosen()).as("other item names").isEqualTo(false);
            }
        }
    }
}
