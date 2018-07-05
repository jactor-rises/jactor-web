package com.github.jactor.rises.web.menu;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.model.facade.JactorFacade;
import com.github.jactor.rises.model.facade.MenuFacade;
import com.github.jactor.rises.model.facade.menu.MenuItem;
import com.github.jactor.rises.web.JactorWeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.stream.Collectors.toList;
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

    @DisplayName("should fetch user menu items and reveal chosen item/child")
    @Test
    void shouldFetchMenuItemsForMenuAndRevealChoosenChild() {
        String target = "user?choose=jactor";
        Name name = new Name("jactor");

        List<MenuItem> menuItems = testMenuFacade.fetchMenuItems(JactorFacade.MENU_USERS).stream()
                .flatMap(menuItem -> menuItem.getChildren().stream())
                .collect(toList());

        assertSoftly(
                softly -> {
                    for (MenuItem menuItem : menuItems) {
                        if (!menuItem.getChildren().isEmpty() && menuItem.getItemName().asString().equals("menu.users.default")) {
                            softly.assertThat(menuItem.isChildChosen(target)).as("'menu.users.default' with chosen child")
                                    .isEqualTo(true);
                        } else if (!menuItem.getChildren().isEmpty()) {
                            softly.assertThat(menuItem.isChildChosen(target)).as("%s with chosen child", menuItem.getItemName().asString())
                                    .isEqualTo(false);
                        } else {
                            softly.assertThat(menuItem.isChosen(target)).as("expected %s to be chosen, not %s", name, menuItem.getItemName().asString())
                                    .isEqualTo(name.equals(menuItem.getItemName()));
                        }
                    }
                }
        );
    }
}
