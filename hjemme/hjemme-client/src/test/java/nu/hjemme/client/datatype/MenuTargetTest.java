package nu.hjemme.client.datatype;

import nu.hjemme.test.MatchBuilder;
import nu.hjemme.test.TypeSafeBuildMatcher;
import org.junit.Test;

import static nu.hjemme.test.DescriptionMatcher.is;
import static nu.hjemme.test.EqualsMatcher.hasImplenetedEqualsMethodUsing;
import static nu.hjemme.test.HashCodeMatcher.hasImplementedHashCodeAccordingTo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/** @author Tor Egil Jacobsen */
public class MenuTargetTest {

    @Test
    public void whenInvokingHashCodeTheResultShouldBeEqualOnDifferentInstancesThatAreEqual() {
        MenuTarget base = new MenuTarget(new MenuItemTarget("a target"), new Name("a menu"));
        MenuTarget equal = new MenuTarget(new MenuItemTarget("a target"), new Name("a menu"));
        MenuTarget notEqual = new MenuTarget(new MenuItemTarget("on target"), new Name("a menu"));

        assertThat(base, hasImplementedHashCodeAccordingTo(equal, notEqual));
    }

    @Test
    public void whenChecksForEqualityIsDoneTheValuesOfThePropertiesMustBeCorrect() {
        MenuTarget base = new MenuTarget(new MenuItemTarget("a target"), new Name("a menu"));
        MenuTarget equal = new MenuTarget(new MenuItemTarget("a target"), new Name("a menu"));
        MenuTarget notEqual = new MenuTarget(new MenuItemTarget("on target"), new Name("a menu"));

        assertThat(base, hasImplenetedEqualsMethodUsing(equal, notEqual));
    }

    @Test
    public void whenInvokingToStringOnTheDataTypeItShouldBeImplementedOnTheDataTypeClass() {
        MenuTarget menuTarget = new MenuTarget(new MenuItemTarget("a target"), new Name("a menu"));
        assertThat("toString", menuTarget.toString(), is(equalTo("MenuTarget[Name[a menu],a target]")));
    }

    @Test
    public void willEncapsulateTheNameOfTheMenuAndTheTargetOnTheMenu() {
        MenuTarget menuTarget = new MenuTarget(new MenuItemTarget("a target"), new Name("a menu"));

        assertThat(menuTarget, new TypeSafeBuildMatcher<MenuTarget>("Et 'target' som inneholder navnet til menyen") {
            @Override
            public MatchBuilder matches(MenuTarget menuTarget, MatchBuilder matchBuilder) {
                return matchBuilder
                        .matches(menuTarget.getMenuName().getName(), is(equalTo("a menu"), "menuName"))
                        .matches(menuTarget.getMenuItemTarget().getTarget(), is(equalTo("a target"), "menuItemTarget"));
            }
        });
    }
}
