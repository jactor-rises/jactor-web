package com.github.jactor.rises.web.menu;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.model.facade.JactorFacade;
import com.github.jactor.rises.model.facade.MenuFacade;
import com.github.jactor.rises.model.facade.menu.MenuItem;
import com.github.jactor.rises.web.JactorWeb;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JactorWeb.class)
@DisplayName("The MenuFacade")
class MenuFacadeIntegrationTest {
    private @Autowired MenuFacade testMenuFacade;

    @DisplayName("should fail when fetching items for an unknown menu")
    @Test void shouldFailWhenFetchingItemsForAnUnknownMenu() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> testMenuFacade.fetchMenuItems(new Name("unknown")));
    }

    @DisplayName("should fetch user menu items and reveal choosen child")
    @Test void shouldFetchMenuItemsForMenuAndRevealChoosenChild() {
        String target = "user?choose=jactor";
        Name targetName = new Name("jactor");

        List<MenuItem> menuItems = testMenuFacade.fetchMenuItems(JactorFacade.MENU_USERS);

        assertSoftly(
                softly -> {
                    for (MenuItem menuItem : menuItems) {
                        if (!menuItem.getChildren().isEmpty()) {
                            if (menuItem.getItemName().asString().equals("menu.users.default")) {
                                softly.assertThat(menuItem.isChildChosen(target)).as("'menu.users.default' with chosen child").isEqualTo(true);
                            } else {
                                softly.assertThat(menuItem.isChildChosen(target)).as("%s with chosen child", menuItem.getItemName().asString()).isEqualTo(false);
                            }
                        } else if (menuItem.isChosen(target)) {
                            softly.assertThat(menuItem.getItemName()).as("%s targetName", menuItem.getItemName().asString()).isEqualTo(targetName);
                        } else {
                            softly.assertThat(menuItem.isChildChosen(target)).as("other item names").isEqualTo(false);
                        }
                    }
                }
        );
    }
}
