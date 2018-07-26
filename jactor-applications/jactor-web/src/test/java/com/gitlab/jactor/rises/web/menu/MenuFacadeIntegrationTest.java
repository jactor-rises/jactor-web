package com.gitlab.jactor.rises.web.menu;

import com.gitlab.jactor.rises.commons.datatype.Name;
import com.gitlab.jactor.rises.web.JactorWeb;
import com.gitlab.jactor.rises.web.JactorWebBeans;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

        var menuItems = testMenuFacade.fetchMenuItems(JactorWebBeans.MENU_USERS).stream()
                .flatMap(menuItem -> menuItem.fetchChildren().stream())
                .collect(toList());

        assertSoftly(
                softly -> {
                    for (MenuItem menuItem : menuItems) {
                        if (menuItem.hasChildren() && menuItem.isNamed("menu.users.default")) {
                            softly.assertThat(menuItem.isChildChosen(target)).as("'menu.users.default' should have chosen child").isEqualTo(true);
                        } else if (menuItem.hasChildren()) {
                            softly.assertThat(menuItem.isChildChosen(target)).as("%s should not have chosen child", menuItem.getItemName()).isEqualTo(false);
                        } else {
                            softly.assertThat(menuItem.isChosen(target)).as("expected %s to be chosen, not %s", name, menuItem.getItemName())
                                    .isEqualTo(name.equals(menuItem.getItemName()));
                        }
                    }
                }
        );
    }
}
